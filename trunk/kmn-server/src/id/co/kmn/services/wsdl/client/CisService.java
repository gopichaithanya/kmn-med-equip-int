package id.co.kmn.services.wsdl.client;

import id.co.kmn.services.wsdl.server.bean.PatientInfo;
import org.joda.time.DateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
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
 * Time: 5:09 PM
 */
public class CisService {
    public static final String DEFAULT_CIS_URL = "http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/";
    public static final String MSG_SOAP_FAULT_HEAD = "Received SOAP Fault";
    public static final String MSG_SOAP_FAULT_CODE = "SOAP Fault Code:   ";
    public static final String MSG_SOAP_FAULT_DESC = "SOAP Fault String: ";

    private SOAPConnectionFactory connectionFactory;
    private MessageFactory messageFactory;
    private URL url;
    private TransformerFactory transfomerFactory;

    public CisService() throws SOAPException, MalformedURLException {
        connectionFactory = SOAPConnectionFactory.newInstance();
        messageFactory = MessageFactory.newInstance();
        transfomerFactory = TransformerFactory.newInstance();
        this.url = new URL(DEFAULT_CIS_URL);
    }
    public CisService(String url) throws SOAPException, MalformedURLException {
        connectionFactory = SOAPConnectionFactory.newInstance();
        messageFactory = MessageFactory.newInstance();
        transfomerFactory = TransformerFactory.newInstance();
        this.url = new URL(url);
    }

    public PatientInfo getPatients(String reqKeyword, String reqClinicId, int reqPageNumber, int reqRowPerPage) throws SOAPException, IOException, TransformerException {
        SOAPMessage request = createGetPatientsRequest(reqKeyword, reqClinicId, reqPageNumber, reqRowPerPage);
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        PatientInfo patientInfo = null;
        if (!response.getSOAPBody().hasFault()) {
            patientInfo = writeGetPatientsResponse(response);
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println(MSG_SOAP_FAULT_HEAD);
            System.err.println(MSG_SOAP_FAULT_CODE + fault.getFaultCode());
            System.err.println(MSG_SOAP_FAULT_DESC + fault.getFaultString());
        }
        return patientInfo;
    }

    private SOAPMessage createGetPatientsRequest(String reqKeyword, String reqClinicId, int reqPageNumber, int reqRowPerPage) throws SOAPException {
        SOAPMessage message = messageFactory.createMessage();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name getPatientsRequestName = envelope.createName(CIS_GET_PATIENT_LIST, CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPBodyElement getPatientsRequestElement = message.getSOAPBody().addBodyElement(getPatientsRequestName);
        Name reqKeywordName = envelope.createName(REQKEYWORD, CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPElement reqKeywordElement = getPatientsRequestElement.addChildElement(reqKeywordName);
        reqKeywordElement.setValue(reqKeyword);
        Name reqClinicIdName = envelope.createName(REQCLINICID, CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPElement reqClinicIdElement = getPatientsRequestElement.addChildElement(reqClinicIdName);
        reqClinicIdElement.setValue(reqClinicId);
        Name reqPageNumberName = envelope.createName(REQPAGENUMBER, CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPElement reqPageNumberElement = getPatientsRequestElement.addChildElement(reqPageNumberName);
        reqPageNumberElement.setValue(String.valueOf(reqPageNumber));
        Name reqRowPerPageName = envelope.createName(REQROWPERPAGE, CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPElement reqRowPerPageElement = getPatientsRequestElement.addChildElement(reqRowPerPageName);
        reqRowPerPageElement.setValue(String.valueOf(reqRowPerPage));
        return message;
    }

    private PatientInfo writeGetPatientsResponse(SOAPMessage message) throws SOAPException, TransformerException {
        PatientInfo patientInfo = new PatientInfo();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name getPatientsResponseName = envelope.createName(CIS_GET_PATIENT_LIST_RESPONSE, CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPBodyElement getPatientsResponseElement = (SOAPBodyElement) message.getSOAPBody().getFirstChild();
        Iterator iterator = getPatientsResponseElement.getChildElements();
        Transformer transformer = transfomerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        int count = 1;
        while (iterator.hasNext()) {
            System.out.println("getPatientsResponseElement: " + count);
            SOAPElement soapElement = (SOAPElement) iterator.next();
            patientInfo.setVariable(soapElement);
            DOMSource source = new DOMSource(soapElement);
            transformer.transform(source, new StreamResult(System.out));
            System.out.println("--------");
        }
        return patientInfo;
    }

    private void parseResult(Name name, String content) {
//            <xsd:element name="reqPatientId" type="xsd:string">
//            <xsd:element name="reqDeviceId" type="xsd:int">
//            <xsd:element name="reqImageURL" type="xsd:string">
//            <xsd:element name="reqDataXML" type="xsd:string">
//          <xsd:element name="reqDatetime" type="xsd:dateTime">
    }

    public String putPatientData(String reqPatientId, int reqDeviceId, String reqImageURL, String reqDataXML, DateTime reqDatetime) throws SOAPException, IOException, TransformerException, DatatypeConfigurationException {
        SOAPMessage request = createPutPatientDataRequest(reqPatientId, reqDeviceId, reqImageURL, reqDataXML, reqDatetime);
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        String result = "failed";
        if (!response.getSOAPBody().hasFault()) {
            result = writePutPatientDataResponse(response);
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println(MSG_SOAP_FAULT_HEAD);
            System.err.println(MSG_SOAP_FAULT_CODE + fault.getFaultCode());
            System.err.println(MSG_SOAP_FAULT_DESC + fault.getFaultString());
        }
        return result;
    }

    private SOAPMessage createPutPatientDataRequest(String reqPatientId, int reqDeviceId, String reqImageURL, String reqDataXML, DateTime reqDatetime) throws SOAPException, DatatypeConfigurationException {
        SOAPMessage message = messageFactory.createMessage();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name getPatientsRequestName = envelope.createName("putPatientData", CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPBodyElement getPatientsRequestElement = message.getSOAPBody().addBodyElement(getPatientsRequestName);
        Name reqKeywordName = envelope.createName("reqPatientId", CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPElement reqKeywordElement = getPatientsRequestElement.addChildElement(reqKeywordName);
        reqKeywordElement.setValue(reqPatientId);
        Name reqClinicIdName = envelope.createName("reqDeviceId", CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPElement reqClinicIdElement = getPatientsRequestElement.addChildElement(reqClinicIdName);
        reqClinicIdElement.setValue(String.valueOf(reqDeviceId));
        Name reqPageNumberName = envelope.createName("reqImageURL", CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPElement reqPageNumberElement = getPatientsRequestElement.addChildElement(reqPageNumberName);
        reqPageNumberElement.setValue(String.valueOf(reqImageURL));
        Name reqRowPerPageName = envelope.createName("reqDataXML", CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPElement reqRowPerPageElement = getPatientsRequestElement.addChildElement(reqRowPerPageName);
        reqRowPerPageElement.setValue(String.valueOf(reqDataXML));
        Name reqDatetimeName = envelope.createName("reqDatetime", CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPElement reqDatetimeElement = getPatientsRequestElement.addChildElement(reqDatetimeName);
        DatatypeFactory factory = DatatypeFactory.newInstance();
        reqDatetimeElement.setValue(factory.newXMLGregorianCalendar(reqDatetime.toGregorianCalendar()).toXMLFormat());
        return message;
    }

    private String writePutPatientDataResponse(SOAPMessage message) throws SOAPException, TransformerException {
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name getPatientsResponseName = envelope.createName("putPatientDataResponse", CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPBodyElement getPatientsResponseElement = (SOAPBodyElement) message.getSOAPBody().getFirstChild();
        Iterator iterator = getPatientsResponseElement.getChildElements();
        Transformer transformer = transfomerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        int count = 1;
        while (iterator.hasNext()) {
            System.out.println("writePutPatientDataResponse: " + count);
            SOAPElement soapElement = (SOAPElement) iterator.next();
            //patientInfo.setVariable(soapElement);
            DOMSource source = new DOMSource(soapElement);
            transformer.transform(source, new StreamResult(System.out));
            System.out.println("--------");
        }
        return "resultString";
    }
}
