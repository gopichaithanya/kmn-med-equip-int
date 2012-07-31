/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.ws;

import static com.kmn.ws.WebServiceConstants.LOCAL_NAMESPACE_URI;
import com.kmn.ws.bean.PatientInfo;
import com.sun.xml.internal.ws.message.ByteArrayAttachment;
import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author valeo
 */
public class ClientService {
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n";
    public static final String XML_ROOT_PREFIX = "<root>";
    public static final String XML_ROOT_SUFFIX = "</root>";
    public static final String XML_COMM_PREFIX = "<comm>\r\n";
    public static final String XML_COMM_SUFFIX = "</comm>";
    public static final String XML_ATTR_PREFIX = "<attr name=\"";
    public static final String XML_ATTR_MID = "\" selected=\"false\">";
    public static final String XML_ATTR_SUFFIX = "</attr>\r\n";
    public static final String TAG_COMM = "comm";
    public static final String TAG_ATTR = "attr";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_SELECTED = "selected";
    
    public static final String DESC = "desc";
    public static final String CHR_SPACE = " ";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    /* XML Predefined entities */
    public static final String AMP = "&";
    public static final String AMP_PE = "&amp;";
    public static final String LT = "<";
    public static final String LT_PE = "&lt;";
    public static final String GT = ">"; 
    public static final String GT_PE = "&gt;";
    public static final String APOS = "\'";
    public static final String APOS_PE = "&apos;";
    public static final String QUOT = "\"";
    public static final String QUOT_PE = "&quot;";
    
    public static final String RESPONSE = "<SOAP-ENV:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" ><SOAP-ENV:Body><getPatientListResponse xmlns=\"http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/\">"
    +"<resPageNumber>1</resPageNumber>"
    +"<resRowThisPage>2</resRowThisPage>"
    +"<resRowPerPage>2</resRowPerPage>"
    +"<resRowTotal>13</resRowTotal>"
    +"<resRowsXML>&lt;PATIENTINFO&gt;&lt;PATIENTID&gt;500488231&lt;/PATIENTID&gt;&lt;PATIENTNAME&gt;STEPHANUS BUDI RAHARDJO&lt;/PATIENTNAME&gt;&lt;PATIENTBRM&gt;&lt;/PATIENTBRM&gt;&lt;DOCID&gt;100094871&lt;/DOCID&gt;&lt;DOCNAME&gt;dr. HADI PRAKOSO, SpM&lt;/DOCNAME&gt;&lt;/PATIENTINFO&gt;&lt;PATIENTINFO&gt;&lt;PATIENTID&gt;500490641&lt;/PATIENTID&gt;&lt;PATIENTNAME&gt;BUDIYANTI BENYAMIN&lt;/PATIENTNAME&gt;&lt;PATIENTBRM&gt;&lt;/PATIENTBRM&gt;&lt;DOCID&gt;140&lt;/DOCID&gt;&lt;DOCNAME&gt;dr. HENRY WAROUW, SpM&lt;/DOCNAME&gt;&lt;/PATIENTINFO&gt;</resRowsXML>"
    +"</getPatientListResponse>"
    +"</SOAP-ENV:Body>"
    +"</SOAP-ENV:Envelope>";
    public static final String RES_ROWS_XML = "&lt;PATIENTINFO&gt;&lt;PATIENTID&gt;500488231&lt;/PATIENTID&gt;&lt;PATIENTNAME&gt;STEPHANUS BUDI RAHARDJO&lt;/PATIENTNAME&gt;&lt;PATIENTBRM&gt;&lt;/PATIENTBRM&gt;&lt;DOCID&gt;100094871&lt;/DOCID&gt;&lt;DOCNAME&gt;dr. HADI PRAKOSO, SpM&lt;/DOCNAME&gt;&lt;/PATIENTINFO&gt;&lt;PATIENTINFO&gt;&lt;PATIENTID&gt;500490641&lt;/PATIENTID&gt;&lt;PATIENTNAME&gt;BUDIYANTI BENYAMIN&lt;/PATIENTNAME&gt;&lt;PATIENTBRM&gt;&lt;/PATIENTBRM&gt;&lt;DOCID&gt;140&lt;/DOCID&gt;&lt;DOCNAME&gt;dr. HENRY WAROUW, SpM&lt;/DOCNAME&gt;&lt;/PATIENTINFO&gt;";
    public static final String FORMATED_RES_ROWS_XML = "<PATIENTINFO><PATIENTID>500488231</PATIENTID><PATIENTNAME>STEPHANUS BUDI RAHARDJO</PATIENTNAME><PATIENTBRM></PATIENTBRM><DOCID>100094871</DOCID><DOCNAME>dr. HADI PRAKOSO, SpM</DOCNAME></PATIENTINFO><PATIENTINFO><PATIENTID>500490641</PATIENTID><PATIENTNAME>BUDIYANTI BENYAMIN</PATIENTNAME><PATIENTBRM></PATIENTBRM><DOCID>140</DOCID><DOCNAME>dr. HENRY WAROUW, SpM</DOCNAME></PATIENTINFO>";
    
    public static final String TAG_RESROWSXML = "resRowsXML";
    public static final String TAG_PATIENTINFO = "PATIENTINFO";
    public static final String TAG_PATIENTID = "PATIENTID";
    public static final String TAG_PATIENTNAME = "PATIENTNAME";
    public static final String TAG_PATIENTBRM = "PATIENTBRM";
    public static final String TAG_DOCID = "DOCID";
    public static final String TAG_DOCNAME = "DOCNAME";
    public static final String TEMP_XML_PATH = "C:\\kmntmp\\tempXml.xml";
    public static final String TEMP_XML_DATA_PATH = "C:\\kmntmp\\tempDataXml.xml";
    
    public ClientService() {
        
    }
    public PatientInfo retrievePatients(String reqKeyword, String reqClinicId, int reqPageNumber, int reqRowPerPage) throws SOAPException, MalformedURLException, IOException, TransformerException {
        ClientConnector cc = new ClientConnector(LOCAL_NAMESPACE_URI);
        return cc.getPatients(reqKeyword, reqClinicId, reqPageNumber, reqRowPerPage);
    }
    public Boolean storeResults(String branchId, String patientId, String patientCode, String patientName,
           String remark, int equipmentId, int imageId,DateTime trxDate, DateTime timeStamp,
           String dataLocation, ByteArrayAttachment dataOutput, String xmlData, String creatorId) throws SOAPException, MalformedURLException, IOException, TransformerException, DatatypeConfigurationException {
        ClientConnector cc = new ClientConnector(LOCAL_NAMESPACE_URI);
        return cc.storeResults(branchId, patientId, patientCode, patientName, remark, equipmentId, imageId, trxDate, timeStamp, dataLocation, dataOutput, xmlData, creatorId);
    }
    public List<Object[]> retrieveLocalPatients() {
        List<Object[]> patients = new ArrayList<Object[]>();
        //fetch from web service
        //handle response  
        try {
            //Create temp XML
            createXml(RESPONSE, TEMP_XML_PATH); 
            //Read temp XML
            Document tempDoc = readXml(TEMP_XML_PATH);
            String resRowsXML = tempDoc.getElementsByTagName(TAG_RESROWSXML).item(0).getFirstChild().getNodeValue();
            //Create temp data XML
            StringBuilder sb = new StringBuilder(XML_ROOT_PREFIX);
            sb.append(resRowsXML);
            sb.append(XML_ROOT_SUFFIX);
            createXml(sb.toString(), TEMP_XML_DATA_PATH);
            //Read temp data XML
            Document doc = readXml(TEMP_XML_DATA_PATH);
            NodeList patientInfo = doc.getElementsByTagName(TAG_PATIENTINFO);
            NodeList n1 = doc.getElementsByTagName(TAG_PATIENTID);
            NodeList n2 = doc.getElementsByTagName(TAG_PATIENTNAME);
            NodeList n3 = doc.getElementsByTagName(TAG_PATIENTBRM);
            NodeList n4 = doc.getElementsByTagName(TAG_DOCID);
            NodeList n5 = doc.getElementsByTagName(TAG_DOCNAME);
            String data1, data2, data3, data4, data5;
            for (int i = 0; i < patientInfo.getLength(); i++) {
                data1 = getStringNodeValue(n1.item(i));
                data2 = getStringNodeValue(n2.item(i));
                data3 = getStringNodeValue(n3.item(i));
                data4 = getStringNodeValue(n4.item(i));
                data5 = getStringNodeValue(n5.item(i));
                patients.add(new Object[]{data1,data2,data3,data4,data5});
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return patients;
    }
    
    public String getStringNodeValue(Node node) {
        String result = "";
        if(node.hasChildNodes())
            result = node.getFirstChild().getNodeValue();
        return result;
    }
    
    public void setStringNodeValue(Node node, String value) {
        if(node.hasChildNodes())
            node.getFirstChild().setNodeValue(value);
    }
    
    public Document createXml(String xmlString, String absolutePath) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        File file = new File(absolutePath);
        if(!file.exists()) {
            file.getParentFile().mkdirs();
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));  
        saveXml(doc, absolutePath); 
        return doc;
    }
    
    public Document saveXml(Document document, String absolutePath) throws TransformerConfigurationException, TransformerException {
        TransformerFactory tranFactory = TransformerFactory.newInstance();  
        Transformer aTransformer = tranFactory.newTransformer();
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Source src = new DOMSource( document );  
        Result dest = new StreamResult( new File(absolutePath));  
        aTransformer.transform( src, dest );
        return document;
    }
    
    public Document readXml(String absolutePath) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();  
        DocumentBuilder db =dbf.newDocumentBuilder();  
        return (Document) db.parse(absolutePath);
    }
    
    public Document convertMessageToXml(String messageFormat, String messageString, String absolutePath) {
        try {
            BufferedReader br = new BufferedReader(new StringReader(messageString));
            StringBuilder sb = new StringBuilder(XML_HEADER+XML_COMM_PREFIX);
            String strLine;
            int count = 1;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                if(!strLine.isEmpty()) {
                    sb.append(XML_ATTR_PREFIX);
                    sb.append(DESC);
                    sb.append(count);
                    sb.append(XML_ATTR_MID);
                    strLine = strLine.replaceAll(AMP, AMP_PE);
                    strLine = strLine.replaceAll(LT, LT_PE);
                    strLine = strLine.replaceAll(GT, GT_PE);
                    strLine = strLine.replaceAll(APOS, APOS_PE);
                    strLine = strLine.replaceAll(QUOT, QUOT_PE);
                    sb.append(strLine);
                    sb.append(XML_ATTR_SUFFIX);
                    count++;
                }
            }
            sb.append(XML_COMM_SUFFIX);
            return createXml(sb.toString().trim(), absolutePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public String toString(Document document) throws TransformerConfigurationException, TransformerException {
        TransformerFactory factory1 = TransformerFactory.newInstance();
        Transformer transformer = factory1.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
        String s = writer.toString();
        return s;
    }
    
    public String getStringFromXmlFile(String absolutePath) throws TransformerConfigurationException, TransformerException, ParserConfigurationException, SAXException, IOException {
        return toString(readXml(absolutePath));
    }
    
    public byte[] getByteArrayFromXmlFile(String absolutePath) throws TransformerConfigurationException, TransformerException, ParserConfigurationException, SAXException, IOException {
        return convertDocumentToByteArray(readXml(absolutePath));
    }
    
    public byte[] convertDocumentToByteArray(Node node) throws TransformerConfigurationException, TransformerException {
        Source source = new DOMSource(node);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Result result = new StreamResult(out);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.transform(source, result);
        return out.toByteArray();
    }
    
    public String convertDateTimeToString(DateTime dateTime) throws DatatypeConfigurationException {
        DatatypeFactory factory = DatatypeFactory.newInstance();
        return factory.newXMLGregorianCalendar(dateTime.toGregorianCalendar()).toXMLFormat();
    }
}
