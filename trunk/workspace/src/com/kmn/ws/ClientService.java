/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.ws;

import static com.kmn.ws.WebServiceConstants.LOCAL_NAMESPACE_URI;
import com.kmn.ws.bean.PatientInfo;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
            StringBuilder sb = new StringBuilder("<root>");
            sb.append(resRowsXML);
            sb.append("</root>");
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
                String line = data1 + " " + data2 + " " + data3+ " " + data4+ " " + data5;
                System.out.println(line);
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
    
    public void createXml(String xmlString, String absolutePath) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));  
        TransformerFactory tranFactory = TransformerFactory.newInstance();  
        Transformer aTransformer = tranFactory.newTransformer();
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Source src = new DOMSource( doc );  
        Result dest = new StreamResult( new File(absolutePath));  
        aTransformer.transform( src, dest ); 
    }
    
    public Document readXml(String absolutePath) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();  
        DocumentBuilder db =dbf.newDocumentBuilder();  
        return (Document) db.parse(absolutePath);
    }
}
