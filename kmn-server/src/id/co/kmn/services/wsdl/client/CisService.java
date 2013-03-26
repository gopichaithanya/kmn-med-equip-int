package id.co.kmn.services.wsdl.client;

import id.co.kmn.services.wsdl.server.bean.PatientInfo;
import id.co.kmn.services.wsdl.server.schema.StoreResultsResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import java.io.*;
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
    private static final Log logger = LogFactory.getLog(CisService.class);
    public static final String DEFAULT_CIS_URL = "http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/";
    public static final String MSG_SOAP_FAULT_HEAD = "Received SOAP Fault";
    public static final String MSG_SOAP_FAULT_CODE = "SOAP Fault Code:   ";
    public static final String MSG_SOAP_FAULT_DESC = "SOAP Fault String: ";

    private SOAPConnectionFactory connectionFactory;
    private MessageFactory messageFactory;
    private URL url;
    private TransformerFactory transfomerFactory;
    private String CIS_SOAP_ENV_URI;

    public CisService() throws SOAPException, MalformedURLException {
        connectionFactory = SOAPConnectionFactory.newInstance();
        messageFactory = MessageFactory.newInstance();
        transfomerFactory = TransformerFactory.newInstance();
        this.url = new URL(DEFAULT_CIS_URL);
        this.CIS_SOAP_ENV_URI = DEFAULT_CIS_URL;
    }
    public CisService(String url) throws SOAPException, MalformedURLException {
        connectionFactory = SOAPConnectionFactory.newInstance();
        messageFactory = MessageFactory.newInstance();
        transfomerFactory = TransformerFactory.newInstance();
        this.url = new URL(url);
        this.CIS_SOAP_ENV_URI = url;
    }

    public PatientInfo getPatients(String reqKeyword, String reqClinicId, int reqPageNumber, int reqRowPerPage) throws SOAPException, IOException, TransformerException {
        if (logger.isDebugEnabled()) {
            logger.debug("CisService.getPatients - URL: " + this.url + 
            		"keyword: '" + reqKeyword + "' id: '" + reqClinicId + "' page: "
                    + reqPageNumber + "reqRowPerPage: " + reqRowPerPage);
        }
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
        message.getSOAPHeader().detachNode();
        message.getSOAPPart().getEnvelope().addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
//        Accept: text/xml
//        Accept: multipart/*
//        Accept: application/soap
//        Content-Length: 398
//        Content-Type: text/xml; charset=utf-8
//        SOAPAction: "http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/getPatientList"

        MimeHeaders mimeHeader = message.getMimeHeaders();
        mimeHeader.removeAllHeaders();
        mimeHeader.addHeader("Accept", "text/xml");
        mimeHeader.addHeader("Accept", "multipart/*");
        mimeHeader.addHeader("Accept", "application/soap");
        mimeHeader.addHeader("Content-Length", "398");
        mimeHeader.addHeader("Content-Type", "text/xml; charset=utf-8");
        mimeHeader.addHeader("SOAPAction", "\"http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/getPatientList\"");

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
        System.out.println("getPatientsResponseElement: ");
        while (iterator.hasNext()) {
            SOAPElement soapElement = (SOAPElement) iterator.next();
            patientInfo.setVariable(soapElement);
            DOMSource source = new DOMSource(soapElement);
            transformer.transform(source, new StreamResult(System.out));
        }
        System.out.println("----------------------------");
        return patientInfo;
    }

    public StoreResultsResponse putPatientData(String reqPatientId, int reqDeviceId, String reqImageURL, String reqDataXML, DateTime reqDatetime) throws SOAPException, IOException, TransformerException, DatatypeConfigurationException {
        if (logger.isDebugEnabled()) {
            logger.debug("CisService.putPatientData - URL: "+ this.url
            		+ "reqPatientId: '" + reqPatientId + "' reqDeviceId: '" + reqDeviceId + "' reqImageURL: "
                    + reqImageURL + "reqDataXML: " + reqDataXML + "reqDatetime: " +reqDatetime);
        }
        SOAPMessage request = createPutPatientDataRequest(reqPatientId, reqDeviceId, reqImageURL, reqDataXML, reqDatetime);
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        StoreResultsResponse result;
        if (!response.getSOAPBody().hasFault()) {
            return writePutPatientDataResponse(response);
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println(MSG_SOAP_FAULT_HEAD);
            String faultString = MSG_SOAP_FAULT_CODE + fault.getFaultCode() +
            "\n"+ MSG_SOAP_FAULT_DESC + fault.getFaultString();
            System.err.println(faultString);
            result = new StoreResultsResponse();
            result.setSuccess(false);
            result.setResult(faultString);
            return result;
        }
    }

    private SOAPMessage createPutPatientDataRequest(String reqPatientId, int reqDeviceId, String reqImageURL, String reqDataXML, DateTime reqDatetime) throws SOAPException, DatatypeConfigurationException {
        SOAPMessage message = messageFactory.createMessage();
        message.getSOAPHeader().detachNode();
        message.getSOAPPart().getEnvelope().addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
//        Accept: text/xml
//        Accept: multipart/*
//        Accept: application/soap
//        Content-Length: 398
//        Content-Type: text/xml; charset=utf-8
//        SOAPAction: "http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/getPatientList"

        MimeHeaders mimeHeader = message.getMimeHeaders();
        mimeHeader.removeAllHeaders();
        mimeHeader.addHeader("Accept", "text/xml");
        mimeHeader.addHeader("Accept", "multipart/*");
        mimeHeader.addHeader("Accept", "application/soap");
        mimeHeader.addHeader("Content-Length", "398");
        mimeHeader.addHeader("Content-Type", "text/xml; charset=utf-8");
        mimeHeader.addHeader("SOAPAction", "\"http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/putPatientData\"");

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

    private StoreResultsResponse writePutPatientDataResponse(SOAPMessage message) throws SOAPException, TransformerException {
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name getPatientsResponseName = envelope.createName("putPatientDataResponse", CIS_SOAP_ENV_PREFIX, CIS_SOAP_ENV_URI);
        SOAPBodyElement getPatientsResponseElement = (SOAPBodyElement) message.getSOAPBody().getFirstChild();
        Iterator iterator = getPatientsResponseElement.getChildElements();
        Transformer transformer = transfomerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        System.out.println("writePutPatientDataResponse: ");
        StoreResultsResponse srr = new StoreResultsResponse();
        while (iterator.hasNext()) {
            SOAPElement soapElement = (SOAPElement) iterator.next();
            DOMSource source = new DOMSource(soapElement);
            setStoreResultResponse(soapElement, srr);
            transformer.transform(source, new StreamResult(System.out));
            transformer.transform(source, result);
        }
        System.out.println("-----------------------------");
        writer.flush();
        //return writer.toString();
        return srr;
    }

    public static final String RES_STATUS_XML = "resStatusXML";
    public static final String STATUS = "STATUS";
    public static final String STATUSNO = "STATUSNO";
    public static final String MESSAGE = "MESSAGE";

    public void setStoreResultResponse(SOAPElement soapElement, StoreResultsResponse srr) {
        if(soapElement.hasAttribute(RES_STATUS_XML) || soapElement.getNodeName().equalsIgnoreCase(RES_STATUS_XML)){
            srr.setResult(soapElement.getTextContent());
            //srr.setSuccess(true);
        } else if(soapElement.hasAttribute(STATUS) || soapElement.getNodeName().equalsIgnoreCase(STATUS)){
            srr.setResult(soapElement.getTextContent());
            //srr.setSuccess(true);
        } else if(soapElement.hasAttribute(STATUSNO) || soapElement.getNodeName().equalsIgnoreCase(STATUSNO)){
           if(Integer.valueOf(soapElement.getTextContent()) == 0) {
                srr.setSuccess(true);
           }
        } else if(soapElement.hasAttribute(MESSAGE) || soapElement.getNodeName().equalsIgnoreCase(MESSAGE)){
            srr.setResult(soapElement.getTextContent());
        }
    }
}
