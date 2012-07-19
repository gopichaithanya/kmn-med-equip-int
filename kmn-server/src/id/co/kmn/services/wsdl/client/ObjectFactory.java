
package id.co.kmn.services.wsdl.client;

import javax.xml.bind.annotation.XmlRegistry;


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

}
