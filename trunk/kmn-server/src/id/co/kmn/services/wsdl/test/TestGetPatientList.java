package id.co.kmn.services.wsdl.test;

import id.co.kmn.services.wsdl.client.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.ws.soap.SOAPFaultException;
import java.net.MalformedURLException;
import java.net.URL;

import static id.co.kmn.services.wsdl.WebServiceConstant.*;
/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/19/12
 * Time: 4:42 PM
 */
public class TestGetPatientList {
    public static void main(String[] args)  throws MalformedURLException, DatatypeConfigurationException {
        try {
            KmnService_Service service;
//            IntegrasiAlat_Service service = new IntegrasiAlat_Service();
//            QName serviceName = new QName(LOCAL_NAMESPACE_URI, QNAME_LOCAL_SERVER);
//            service = new IntegrasiAlat_Service(new URL(WSDL_LOCAL), serviceName);
            QName serviceName = new QName(LOCAL_NAMESPACE_URI, QNAME_LOCAL_SERVER);
            service = new KmnService_Service(new URL(WSDL_LOCAL), serviceName);

            KmnService kmnService = service.getKmnServiceSOAP();
            GetPatientList request = new GetPatientList();
            request.setReqClinicId("8");
            request.setReqKeyword("%a%");
            request.setReqPageNumber(1);
            request.setReqRowPerPage(10);
            System.out.format("Requesting patients: ", request.toString());
            //GetPatientListResponse response = integrasiAlat.;
        }
        catch (SOAPFaultException ex) {
            System.out.format("SOAP Fault Code    %1s%n", ex.getFault().getFaultCodeAsQName());
            System.out.format("SOAP Fault String: %1s%n", ex.getFault().getFaultString());
            System.out.format("SOAP Fault Actor: %1s%n", ex.getFault().getFaultActor());
            System.out.format("SOAP Fault Code: %1s%n", ex.getFault().getFaultCode());
            System.out.format("SOAP Fault Node: %1s%n", ex.getFault().getFaultNode());
            System.out.format("SOAP Fault BaseURI: %1s%n", ex.getFault().getBaseURI());
            System.out.format("SOAP Fault Detail: %1s%n", ex.getFault().getDetail().toString());
            System.out.format("SOAP Fault NameSpaceURI: %1s%n", ex.getFault().getNamespaceURI());
            System.out.format("SOAP Fault TextContents: %1s%n", ex.getFault().getTextContent());

        }
    }
}
