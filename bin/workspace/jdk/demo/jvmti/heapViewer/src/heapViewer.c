/*
 * @(#)heapViewer.c	1.12 10/01/12
 * 
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 * 
 * -Redistribution in binary form must reproduce the above copyright notice, 
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may 
 * be used to endorse or promote products derived from this software without 
 * specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL 
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST 
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, 
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY 
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, 
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

#include <stdio.h>
#include <stddef.h>
#include <stdlib.h>
#include <string.h>

#include "jni.h"
#include "jvmti.h"

#include "agent_util.h"

/* Global static data */
typedef struct {
    jboolean      vmDeathCalled;
    jboolean      dumpInProgress;
    jrawMonitorID lock;
} GlobalData;
static GlobalData globalData, *gdata = &globalData;

/* Typedef to hold class details */
typedef struct {
    char *signature;
    int   count;
    int   space;
} ClassDetails;

/* Enter agent monitor protected section */
static void
enterAgentMonitor(jvmtiEnv *jvmti)
{
    jvmtiError err;
    
    err = (*jvmti)->RawMonitorEnter(jvmti, gdata->lock);
    check_jvmti_error(jvmti, err, "raw monitor enter");
}

/* Exit agent monitor protected section */
static void
exitAgentMonitor(jvmtiEnv *jvmti)
{
    jvmtiError err;
    
    err = (*jvmti)->RawMonitorExit(jvmti, gdata->lock);
    check_jvmti_error(jvmti, err, "raw monitor exit");
}

/* Heap object callback */
static jint JNICALL 
cbHeapObject(jlong class_tag, jlong size, jlong* tag_ptr, jint length,
           void* user_data)
{
    if ( class_tag != (jlong)0 ) {
        ClassDetails *d;
        
        d = (ClassDetails*)(void*)(ptrdiff_t)class_tag;
        (*((jint*)(user_data)))++;
        d->count++;
        d->space += (int)size;
    }
    return JVMTI_VISIT_OBJECTS;
}

/* Compare two ClassDetails */
static int
compareDetails(const void *p1, const void *p2)
{
    return ((ClassDetails*)p2)->space - ((ClassDetails*)p1)->space;
}

/* Callback for JVMTI_EVENT_DATA_DUMP_REQUEST (Ctrl-\ or at exit) */
static void JNICALL
dataDumpRequest(jvmtiEnv *jvmti)
{
    enterAgentMonitor(jvmti); {
        if ( !gdata->vmDeathCalled && !gdata->dumpInProgress ) {
            jvmtiHeapCallbacks heapCallbacks;
            ClassDetails      *details;
            jvmtiError         err;
            jclass            *classes;
            jint               totalCount;
            jint               count;
            jint               i;

            gdata->dumpInProgress = JNI_TRUE;
           
            /* Get all the loaded classes */
            err = (*jvmti)->GetLoadedClasses(jvmti, &count, &classes);
            check_jvmti_error(jvmti, err, "get loaded classes");

            /* Setup an area to hold details about these classes */
            details = (ClassDetails*)calloc(sizeof(ClassDetails), count);
            if ( details == NULL ) {
                fatal_error("ERROR: Ran out of malloc space\n");
            }
            for ( i = 0 ; i < count ; i++ ) {
                char *sig;

                /* Get and save the class signature */
                err = (*jvmti)->GetClassSignature(jvmti, classes[i], &sig, NULL);
                check_jvmti_error(jvmti, err, "get class signature");
                if ( sig == NULL ) {
                    fatal_error("ERROR: No class signature found\n");
                }
                details[i].signature = strdup(sig);
                deallocate(jvmti, sig);

                /* Tag this jclass */
                err = (*jvmti)->SetTag(jvmti, classes[i], 
                                    (jlong)(ptrdiff_t)(void*)(&details[i]));
                check_jvmti_error(jvmti, err, "set object tag");
            }
            
            /* Iterate through the heap and count up uses of jclass */
            (void)memset(&heapCallbacks, 0, sizeof(heapCallbacks));
            heapCallbacks.heap_iteration_callback = &cbHeapObject;
            totalCount = 0;
            err = (*jvmti)->IterateThroughHeap(jvmti, 
                       JVMTI_HEAP_FILTER_CLASS_UNTAGGED, NULL,
                       &heapCallbacks, (const void *)&totalCount);
            check_jvmti_error(jvmti, err, "iterate through heap");

            /* Remove tags */
            for ( i = 0 ; i < count ; i++ ) {
                /* Un-Tag this jclass */
                err = (*jvmti)->SetTag(jvmti, classes[i], (jlong)0);
                check_jvmti_error(jvmti, err, "set object tag");
            }
            
            /* Sort details by space used */
            qsort(details, count, sizeof(ClassDetails), &compareDetails);
           
            /* Print out sorted table */
            stdout_message("Heap View, Total of %d objects found.\n\n",
                         totalCount);
            
            stdout_message("Space      Count      Class Signature\n");
            stdout_message("---------- ---------- ----------------------\n");
            
            for ( i = 0 ; i < count ; i++ ) {
                if ( details[i].space == 0 || i > 20 ) {
                    break;
                }
                stdout_message("%10d %10d %s\n",
                    details[i].space, details[i].count, details[i].signature);
            }
            stdout_message("---------- ---------- ----------------------\n\n");

            /* Free up all allocated space */
            deallocate(jvmti, classes);
            for ( i = 0 ; i < count ; i++ ) {
                if ( details[i].signature != NULL ) {
                    free(details[i].signature);
                }
            }
            free(details);
            
            gdata->dumpInProgress = JNI_FALSE;
        }
    } exitAgentMonitor(jvmti);
}

/* Callback for JVMTI_EVENT_VM_INIT */
static void JNICALL 
vmInit(jvmtiEnv *jvmti, JNIEnv *env, jthread thread)
{
    enterAgentMonitor(jvmti); {
        jvmtiError          err;
        
        err = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, 
                            JVMTI_EVENT_DATA_DUMP_REQUEST, NULL);
        check_jvmti_error(jvmti, err, "set event notification");
    } exitAgentMonitor(jvmti);
}

/* Callback for JVMTI_EVENT_VM_DEATH */
static void JNICALL 
vmDeath(jvmtiEnv *jvmti, JNIEnv *env)
{
    jvmtiError          err;
   
    /* Make sure everything has been garbage collected */
    err = (*jvmti)->ForceGarbageCollection(jvmti);
    check_jvmti_error(jvmti, err, "force garbage collection");

    /* Disable events and dump the heap information */
    enterAgentMonitor(jvmti); {
        err = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_DISABLE, 
                            JVMTI_EVENT_DATA_DUMP_REQUEST, NULL);
        check_jvmti_error(jvmti, err, "set event notification");
        
        dataDumpRequest(jvmti);
        
        gdata->vmDeathCalled = JNI_TRUE;
    } exitAgentMonitor(jvmti);
}

/* Agent_OnLoad() is called first, we prepare for a VM_INIT event here. */
JNIEXPORT jint JNICALL
Agent_OnLoad(JavaVM *vm, char *options, void *reserved)
{
    jint                rc;
    jvmtiError          err;
    jvmtiCapabilities   capabilities;
    jvmtiEventCallbacks callbacks;
    jvmtiEnv           *jvmti;
    
    /* Get JVMTI environment */
    jvmti = NULL;
    rc = (*vm)->GetEnv(vm, (void **)&jvmti, JVMTI_VERSION);
    if (rc != JNI_OK) {
        fatal_error("ERROR: Unable to create jvmtiEnv, error=%d\n", rc);
        return -1;
    }
    if ( jvmti == NULL ) {
        fatal_error("ERROR: No jvmtiEnv* returned from GetEnv\n");
    }

    /* Get/Add JVMTI capabilities */ 
    (void)memset(&capabilities, 0, sizeof(capabilities));
    capabilities.can_tag_objects = 1;
    capabilities.can_generate_garbage_collection_events = 1;
    err = (*jvmti)->AddCapabilities(jvmti, &capabilities);
    check_jvmti_error(jvmti, err, "add capabilities");

    /* Create the raw monitor */
    err = (*jvmti)->CreateRawMonitor(jvmti, "agent lock", &(gdata->lock));
    check_jvmti_error(jvmti, err, "create raw monitor");
    
    /* Set callbacks and enable event notifications */
    memset(&callbacks, 0, sizeof(callbacks));
    callbacks.VMInit                  = &vmInit;
    callbacks.VMDeath                 = &vmDeath;
    callbacks.DataDumpRequest         = &dataDumpRequest;
    err = (*jvmti)->SetEventCallbacks(jvmti, &callbacks, sizeof(callbacks));
    check_jvmti_error(jvmti, err, "set event callbacks");
    err = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, 
                        JVMTI_EVENT_VM_INIT, NULL);
    check_jvmti_error(jvmti, err, "set event notifications");
    err = (*jvmti)->SetEventNotificationMode(jvmti, JVMTI_ENABLE, 
                        JVMTI_EVENT_VM_DEATH, NULL);
    check_jvmti_error(jvmti, err, "set event notifications");
    return 0;
}

/* Agent_OnUnload() is called last */
JNIEXPORT void JNICALL
Agent_OnUnload(JavaVM *vm)
{
}

