/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.ws;

//import com.kmn.util.IOUtils;
import static com.kmn.ws.WebServiceConstants.*;
import com.kmn.ws.bean.PatientInfo;
import com.kmn.ws.bean.StoreResultsResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.soap.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.joda.time.DateTime;

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
        reqClinicIdElement.setValue((reqClinicId!=null)?reqClinicId:"0");
        Name reqPageNumberName = envelope.createName(REQPAGENUMBER, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPElement reqPageNumberElement = getPatientsRequestElement.addChildElement(reqPageNumberName);
        reqPageNumberElement.setValue("1");//String.valueOf(reqPageNumber));
        Name reqRowPerPageName = envelope.createName(REQROWPERPAGE, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPElement reqRowPerPageElement = getPatientsRequestElement.addChildElement(reqRowPerPageName);
        reqRowPerPageElement.setValue("10");//String.valueOf(reqRowPerPage));
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
        System.out.println("getPatientsResponseElement: ");
        while (iterator.hasNext()) {
            SOAPElement soapElement = (SOAPElement) iterator.next();
            DOMSource source = new DOMSource(soapElement);
            transformer.transform(source, new StreamResult(System.out));
            patientInfo.setVariable(soapElement);
        }
        System.out.println("----------------------------");
        return patientInfo;
    }
    
    public StoreResultsResponse storeResults(String branchId, String patientId, String patientCode, String patientName, String remark, int equipmentId
            , int imageId,DateTime trxDate, DateTime timeStamp, String dataLocation, File dataOutput, String xmlData
            , String creatorId) throws SOAPException, TransformerException, DatatypeConfigurationException, MalformedURLException, FileNotFoundException, IOException {
        
        SOAPMessage request = createStoreResultsRequest(branchId, patientId, patientCode, patientName, remark, equipmentId
                , imageId, trxDate, timeStamp, dataLocation, dataOutput, xmlData, creatorId);
        
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        
        if (!response.getSOAPBody().hasFault()) {
            return writeStoreResultsResponse(response);
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println("Received SOAP Fault");
            String errorStr = "SOAP Fault Code:   " + fault.getFaultCode() + 
                    "\nSOAP Fault String: " + fault.getFaultString();
            System.err.println(errorStr);
            //System.err.println("SOAP Fault Code:   " + fault.getFaultCode());
            //System.err.println("SOAP Fault String: " + fault.getFaultString());
            return new StoreResultsResponse(false,errorStr);
        }
    }
    
    private SOAPMessage createStoreResultsRequest(String branchId, String patientId, String patientCode, String patientName,
           String remark, int equipmentId, int imageId, DateTime trxDate, DateTime timeStamp,
           String dataLocation, File dataOutput, String xmlData, String creatorId)
           throws SOAPException, DatatypeConfigurationException, MalformedURLException, FileNotFoundException, IOException {
        
        SOAPMessage message = messageFactory.createMessage();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name name = envelope.createName(STORE_RESULTS_REQUEST, NAME_PREFIX, MESSAGES_NAMESPACE);
        SOAPBodyElement body = message.getSOAPBody().addBodyElement(name);
        
        this.addElement(envelope, body, BRANCHID, NAME_PREFIX, MESSAGES_NAMESPACE, branchId);
        this.addElement(envelope, body, PATIENTID, NAME_PREFIX, MESSAGES_NAMESPACE, patientId);
        this.addElement(envelope, body, PATIENTCODE, NAME_PREFIX, MESSAGES_NAMESPACE, patientCode);
        this.addElement(envelope, body, PATIENTNAME, NAME_PREFIX, MESSAGES_NAMESPACE, patientName);
        this.addElement(envelope, body, REMARK, NAME_PREFIX, MESSAGES_NAMESPACE, remark);
        this.addElement(envelope, body, EQUIPMENTID, NAME_PREFIX, MESSAGES_NAMESPACE, String.valueOf(equipmentId));
        this.addElement(envelope, body, IMAGEID, NAME_PREFIX, MESSAGES_NAMESPACE, String.valueOf(imageId));
        DatatypeFactory factory = DatatypeFactory.newInstance();
        this.addElement(envelope, body, TRXDATE, NAME_PREFIX, MESSAGES_NAMESPACE, factory.newXMLGregorianCalendar(trxDate.toGregorianCalendar()).toXMLFormat());
        this.addElement(envelope, body, TIMESTAMP, NAME_PREFIX, MESSAGES_NAMESPACE, factory.newXMLGregorianCalendar(timeStamp.toGregorianCalendar()).toXMLFormat());
        this.addElement(envelope, body, DATALOCATION, NAME_PREFIX, MESSAGES_NAMESPACE, dataLocation);

        DataHandler dh = new DataHandler(new FileDataSource(dataOutput));
        AttachmentPart ap = message.createAttachmentPart(dh);
        ap.setContentId(DATAOUTPUT);
        message.addAttachmentPart(ap);
        
        this.addElement(envelope, body, XMLDATA, NAME_PREFIX, MESSAGES_NAMESPACE, xmlData);
        this.addElement(envelope, body, CREATORID, NAME_PREFIX, MESSAGES_NAMESPACE, creatorId);
        
        return message;
    }
    
    private void addElement(SOAPEnvelope envelope, SOAPBodyElement body, String localName
            , String prefix, String uri, Object value) throws SOAPException {
        
        Name name = envelope.createName(localName, prefix, uri);
        SOAPElement reqKeywordElement = body.addChildElement(name);
        
        reqKeywordElement.setValue((String) value);
    }
    
    private StoreResultsResponse writeStoreResultsResponse(SOAPMessage message) throws SOAPException, TransformerException {
        StoreResultsResponse srr = new StoreResultsResponse();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        SOAPBodyElement getResponseElement = (SOAPBodyElement) message.getSOAPBody().getFirstChild();
        Iterator iterator = getResponseElement.getChildElements();
        Transformer transformer = transfomerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        System.out.println("writeStoreResultsResponse: ");
        while (iterator.hasNext()) {
            SOAPElement soapElement = (SOAPElement) iterator.next();
            DOMSource source = new DOMSource(soapElement);
            transformer.transform(source, new StreamResult(System.out));
            srr.setStoreResultsResponse(soapElement);
        }
        System.out.println("---------------------------");
        return srr;
    }
    
    
}
