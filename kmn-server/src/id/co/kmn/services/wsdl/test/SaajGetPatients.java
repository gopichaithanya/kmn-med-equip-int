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

import static id.co.kmn.services.wsdl.WebServiceConstant.*;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/27/12
 * Time: 1:52 AM
 */
public class SaajGetPatients {
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
        Name getPatientsRequestName = envelope.createName(GET_PATIENTS_REQUEST, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPBodyElement getPatientsRequestElement = message.getSOAPBody().addBodyElement(getPatientsRequestName);
        Name reqKeywordName = envelope.createName(REQKEYWORD, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPElement reqKeywordElement = getPatientsRequestElement.addChildElement(reqKeywordName);
        reqKeywordElement.setValue("a");
        Name reqClinicIdName = envelope.createName(REQCLINICID, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPElement reqClinicIdElement = getPatientsRequestElement.addChildElement(reqClinicIdName);
        reqClinicIdElement.setValue("8");
        Name reqPageNumberName = envelope.createName(REQPAGENUMBER, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPElement reqPageNumberElement = getPatientsRequestElement.addChildElement(reqPageNumberName);
        reqPageNumberElement.setValue("1");
        Name reqRowPerPageName = envelope.createName(REQROWPERPAGE, NAME_PREFIX, MESSAGES_NAMESPACE);
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
        Name getPatientsResponseName = envelope.createName(GET_PATIENT_LIST_RESPONSE, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPBodyElement getPatientsResponseElement = (SOAPBodyElement) message.getSOAPBody().getFirstChild();
        Iterator iterator = getPatientsResponseElement.getChildElements();
        Transformer transformer = transfomerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        int count = 1;
        while (iterator.hasNext()) {
            System.out.println("getPatientsResponseElement: " + count);
            SOAPElement soapElement = (SOAPElement) iterator.next();
            DOMSource source = new DOMSource(soapElement);
            transformer.transform(source, new StreamResult(System.out));
            System.out.println("--------");
        }
        org.w3c.dom.Node resPageNumber = getPatientsResponseElement.getFirstChild();
        System.out.println("resPageNumber " + resPageNumber.getTextContent());
    }
}
