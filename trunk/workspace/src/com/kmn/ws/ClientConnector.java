/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.ws;

import static com.kmn.ws.WebServiceConstants.*;
import com.kmn.ws.bean.PatientInfo;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import javax.xml.soap.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author valeo
 */
public class ClientConnector {
    private SOAPConnectionFactory connectionFactory;
    private MessageFactory messageFactory;
    private URL url;
    private TransformerFactory transfomerFactory;

    public ClientConnector(String url) throws SOAPException, MalformedURLException {
        connectionFactory = SOAPConnectionFactory.newInstance();
        messageFactory = MessageFactory.newInstance();
        transfomerFactory = TransformerFactory.newInstance();
        this.url = new URL(url);
    }

    private SOAPMessage createGetPatientsRequest(String reqKeyword, String reqClinicId, int reqPageNumber, int reqRowPerPage) throws SOAPException {
        SOAPMessage message = messageFactory.createMessage();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name getPatientsRequestName = envelope.createName(GET_PATIENTS_REQUEST, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPBodyElement getPatientsRequestElement = message.getSOAPBody().addBodyElement(getPatientsRequestName);
        Name reqKeywordName = envelope.createName(REQKEYWORD, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPElement reqKeywordElement = getPatientsRequestElement.addChildElement(reqKeywordName);
        reqKeywordElement.setValue(reqKeyword);
        Name reqClinicIdName = envelope.createName(REQCLINICID, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPElement reqClinicIdElement = getPatientsRequestElement.addChildElement(reqClinicIdName);
        reqClinicIdElement.setValue(reqClinicId);
        Name reqPageNumberName = envelope.createName(REQPAGENUMBER, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPElement reqPageNumberElement = getPatientsRequestElement.addChildElement(reqPageNumberName);
        reqPageNumberElement.setValue(String.valueOf(reqPageNumber));
        Name reqRowPerPageName = envelope.createName(REQROWPERPAGE, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPElement reqRowPerPageElement = getPatientsRequestElement.addChildElement(reqRowPerPageName);
        reqRowPerPageElement.setValue(String.valueOf(reqRowPerPage));
        return message;
    }

    public PatientInfo getPatients(String reqKeyword, String reqClinicId, int reqPageNumber, int reqRowPerPage) throws SOAPException, IOException, TransformerException {
        SOAPMessage request = createGetPatientsRequest(reqKeyword, reqClinicId, reqPageNumber, reqRowPerPage);
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        if (!response.getSOAPBody().hasFault()) {
            return writeGetPatientsResponse(response);
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println("Received SOAP Fault");
            System.err.println("SOAP Fault Code:   " + fault.getFaultCode());
            System.err.println("SOAP Fault String: " + fault.getFaultString());
            return null;
        }
    }

    private PatientInfo writeGetPatientsResponse(SOAPMessage message) throws SOAPException, TransformerException {
        PatientInfo patientInfo = new PatientInfo();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        //Name getPatientsResponseName = envelope.createName(GET_PATIENT_LIST_RESPONSE, NAME_PREFIX, MESSAGES_NAMESPACE);
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
            patientInfo.setVariable(soapElement);
        }
        return patientInfo;
    }
}
