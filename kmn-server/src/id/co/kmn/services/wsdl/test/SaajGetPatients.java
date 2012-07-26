package id.co.kmn.services.wsdl.test;

import javax.xml.soap.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/27/12
 * Time: 1:52 AM
 */
public class SaajGetPatients {
    public static final String NAMESPACE_URI = "http://localhost:9090/kmn/schemas/messages";

    public static final String PREFIX = "kmn";

    private SOAPConnectionFactory connectionFactory;

    private MessageFactory messageFactory;

    private URL url;

    private TransformerFactory transfomerFactory;

    public SaajGetPatients(String url) throws SOAPException, MalformedURLException {
        connectionFactory = SOAPConnectionFactory.newInstance();
        messageFactory = MessageFactory.newInstance();
        transfomerFactory = TransformerFactory.newInstance();
        this.url = new URL(url);
    }

    private SOAPMessage createGetPatientsRequest() throws SOAPException {
        SOAPMessage message = messageFactory.createMessage();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name getPatientsRequestName = envelope.createName("GetPatientsRequest", PREFIX, NAMESPACE_URI);
        SOAPBodyElement getPatientsRequestElement = message.getSOAPBody().addBodyElement(getPatientsRequestName);
        Name reqKeywordName = envelope.createName("reqKeyword", PREFIX, NAMESPACE_URI);
        SOAPElement reqKeywordElement = getPatientsRequestElement.addChildElement(reqKeywordName);
        reqKeywordElement.setValue("a");
        Name reqClinicIdName = envelope.createName("reqClinicId", PREFIX, NAMESPACE_URI);
        SOAPElement reqClinicIdElement = getPatientsRequestElement.addChildElement(reqClinicIdName);
        reqClinicIdElement.setValue("8");
        Name reqPageNumberName = envelope.createName("reqPageNumber", PREFIX, NAMESPACE_URI);
        SOAPElement reqPageNumberElement = getPatientsRequestElement.addChildElement(reqPageNumberName);
        reqPageNumberElement.setValue("1");
        Name reqRowPerPageName = envelope.createName("reqRowPerPage", PREFIX, NAMESPACE_URI);
        SOAPElement reqRowPerPageElement = getPatientsRequestElement.addChildElement(reqRowPerPageName);
        reqRowPerPageElement.setValue("1");
        return message;
    }

    public void getPatients() throws SOAPException, IOException, TransformerException {
        SOAPMessage request = createGetPatientsRequest();
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        if (!response.getSOAPBody().hasFault()) {
            writeGetPatientsResponse(response);
        }
        else {
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println("Received SOAP Fault");
            System.err.println("SOAP Fault Code:   " + fault.getFaultCode());
            System.err.println("SOAP Fault String: " + fault.getFaultString());
        }
    }

    private void writeGetPatientsResponse(SOAPMessage message) throws SOAPException, TransformerException {
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name getPatientsResponseName = envelope.createName("GetPatientListResponse", PREFIX, NAMESPACE_URI);
        SOAPBodyElement getPatientsResponseElement =
                (SOAPBodyElement) message.getSOAPBody().getChildElements(getPatientsResponseName).next();
//        resPageNumber;
//        resRowThisPage;
//        resRowPerPage;
//        resRowTotal;
//        resRowsXML;
        Name resPageNumberName = envelope.createName("resPageNumber", PREFIX, NAMESPACE_URI);
        Iterator iterator = getPatientsResponseElement.getChildElements(resPageNumberName);
        Transformer transformer = transfomerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        int count = 1;
        while (iterator.hasNext()) {
            System.out.println("Patient " + count);
            System.out.println("--------");
            SOAPElement flightElement = (SOAPElement) iterator.next();
            DOMSource source = new DOMSource(flightElement);
            transformer.transform(source, new StreamResult(System.out));
        }
    }
}
