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
    //public static final String LOCAL_NAMESPACE_URI = "http://192.168.13.10:8080/kmn/services";
    public static final String MESSAGES_NAMESPACE = "http://localhost:9090/kmn/schemas/messages";
    public static final String GET_PATIENTS_REQUEST = "GetPatientsRequest";
    public static final String STORE_RESULTS_REQUEST = "StoreResultsRequest";
    public static final String REQKEYWORD = "reqKeyword";
    public static final String REQCLINICID = "reqClinicId";
    public static final String REQPAGENUMBER = "reqPageNumber";
    public static final String REQROWPERPAGE = "reqRowPerPage";
    /* StoreResultsRequest Constants */
    public static final String BRANCHID = "branchId"; 
    public static final String PATIENTID = "patientId";
    public static final String PATIENTCODE = "patientCode";
    public static final String PATIENTNAME = "patientName";
    public static final String REMARK = "remark";
    public static final String EQUIPMENTID = "equipmentId";
    public static final String IMAGEID = "imageId";
    public static final String TRXDATE = "trxDate";
    public static final String TIMESTAMP = "timeStamp";
    public static final String DATALOCATION = "dataLocation";
    public static final String DATAOUTPUT = "dataOutput";
    public static final String XMLDATA = "xmlData";
    public static final String CREATORID = "creatorId";
    
    public static final String GET_PATIENT_LIST_RESPONSE = "GetPatientListResponse";
}
