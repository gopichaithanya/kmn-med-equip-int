package id.co.kmn.services.wsdl;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/26/12
 * Time: 12:39 PM
 */
public interface WebServiceConstant {
    /* EndPoint Constants */
    public static final String STORE_RESULTS_REQUEST = "StoreResultsRequest";
    public static final String GET_PATIENT_LIST_RESPONSE = "GetPatientListResponse";
    public static final String GET_PATIENTS_REQUEST = "GetPatientsRequest";
    public static final String GET_PATIENTS_RESPONSE = "GetPatientsResponse";
    public static final String MESSAGES_NAMESPACE = "http://localhost:9090/kmn/schemas/messages";
    public static final String GET_KMN_REQUEST = "GetKmnRequest";
    public static final String GET_KMN_RESPONSE = "GetKmnResponse";

    /* Client Constants */
    public static final String NAME_PREFIX = "kmn";
    public static final String LOCAL_NAMESPACE_URI = "http://localhost:9090/kmn/services";

    /* Web Service Client Constants */
    public static final String KMNSERVICE_WSDL_URL = "http://localhost:9090/kmn/KmnService.wsdl";
    public static final String KMNSERVICE_TNS_URL = "http://localhost:9090/kmn/KmnService/";
    public static final String QNAME_LOCAL_SERVER = "KmnService";
    public static final String QNAME_LOCAL_SOAP = "KmnServiceSOAP";

    /* CIS Web Service Client Constants */
    //public static final String CIS_WSDL_URL = "http://192.168.13.10:2221/apps/kmn/IntegrasiAlat.wsdl";
    //public static final String CIS_NAMESPACE_URI = "http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/";
    public static final String CIS_SOAP_ENV_PREFIX = "";
    //public static final String CIS_SOAP_ENV_URI = "http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/";
    public static final String CIS_GET_PATIENT_LIST = "getPatientList";
    public static final String CIS_GET_PATIENT_LIST_REQUEST = "getPatientListRequest";
    public static final String REQKEYWORD = "reqKeyword";
    public static final String REQCLINICID = "reqClinicId";
    public static final String REQPAGENUMBER = "reqPageNumber";
    public static final String REQROWPERPAGE = "reqRowPerPage";

    public static final String CIS_GET_PATIENT_LIST_RESPONSE = "getPatientListResponse";

    public static final String QNAME_LOCAL_PART = "IntegrasiAlat";
    public static final String WSDL_LOCAL = "http://localhost:9090/kmn/services/KmnService.wsdl";
    public static final String DEFAULT_CLINIC_ID = "8";
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int DEFAULT_PAGE_ROW = 10;;
}
