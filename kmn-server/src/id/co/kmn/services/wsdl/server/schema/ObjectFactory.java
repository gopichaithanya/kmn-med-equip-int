
package id.co.kmn.services.wsdl.server.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the id.co.kmn.services.wsdl.server.schema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetPatientsResponse_QNAME = new QName("http://localhost:9090/kmn/schemas/messages", "GetPatientsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: id.co.kmn.services.wsdl.server.schema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PatientInfo.Patients }
     * 
     */
    public PatientInfo.Patients createPatientInfoPatients() {
        return new PatientInfo.Patients();
    }

    /**
     * Create an instance of {@link Patient }
     * 
     */
    public Patient createPatient() {
        return new Patient();
    }

    /**
     * Create an instance of {@link GetPatientsRequest }
     * 
     */
    public GetPatientsRequest createGetPatientsRequest() {
        return new GetPatientsRequest();
    }

    /**
     * Create an instance of {@link StoreResultsRequest }
     * 
     */
    public StoreResultsRequest createStoreResultsRequest() {
        return new StoreResultsRequest();
    }

    /**
     * Create an instance of {@link StoreResultsResponse }
     * 
     */
    public StoreResultsResponse createStoreResultsResponse() {
        return new StoreResultsResponse();
    }

    /**
     * Create an instance of {@link GetPatientListResponse }
     * 
     */
    public GetPatientListResponse createGetPatientListResponse() {
        return new GetPatientListResponse();
    }

    /**
     * Create an instance of {@link PatientInfo }
     * 
     */
    public PatientInfo createPatientInfo() {
        return new PatientInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PatientInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://localhost:9090/kmn/schemas/messages", name = "GetPatientsResponse")
    public JAXBElement<PatientInfo> createGetPatientsResponse(PatientInfo value) {
        return new JAXBElement<PatientInfo>(_GetPatientsResponse_QNAME, PatientInfo.class, null, value);
    }

}
