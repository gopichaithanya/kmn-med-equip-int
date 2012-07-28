/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.ws;

/**
 *
 * @author valeo
 */
public interface WebServiceConstants {
    public static final String NAME_PREFIX = "kmn";
    public static final String LOCAL_NAMESPACE_URI = "http://localhost:9090/kmn/services";
    public static final String MESSAGES_NAMESPACE = "http://localhost:9090/kmn/schemas/messages";
    public static final String GET_PATIENTS_REQUEST = "GetPatientsRequest";
    public static final String REQKEYWORD = "reqKeyword";
    public static final String REQCLINICID = "reqClinicId";
    public static final String REQPAGENUMBER = "reqPageNumber";
    public static final String REQROWPERPAGE = "reqRowPerPage";
    
    public static final String GET_PATIENT_LIST_RESPONSE = "GetPatientListResponse";
}
