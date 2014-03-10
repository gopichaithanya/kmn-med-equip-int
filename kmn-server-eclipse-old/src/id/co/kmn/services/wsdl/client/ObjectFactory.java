
package id.co.kmn.services.wsdl.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the id.co.kmn.services.wsdl.client package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: id.co.kmn.services.wsdl.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PutPatientDataResponse }
     * 
     */
    public PutPatientDataResponse createPutPatientDataResponse() {
        return new PutPatientDataResponse();
    }

    /**
     * Create an instance of {@link PutPatientData }
     * 
     */
    public PutPatientData createPutPatientData() {
        return new PutPatientData();
    }

    /**
     * Create an instance of {@link GetPatientListResponse }
     * 
     */
    public GetPatientListResponse createGetPatientListResponse() {
        return new GetPatientListResponse();
    }

    /**
     * Create an instance of {@link GetPatientList }
     * 
     */
    public GetPatientList createGetPatientList() {
        return new GetPatientList();
    }

    /**
     * Create an instance of {@link javax.xml.bind.JAXBElement }{@code <}{@link String }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://localhost:9090/kmn/schemas/messages", name = "GetPatientsResponse")
    public JAXBElement<String> createGetPatientsResponse(String value) {
        return new JAXBElement<String>(_GetPatientsResponse_QNAME, String.class, null, value);
    }

}
