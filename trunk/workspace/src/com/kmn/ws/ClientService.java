/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.ws;

import com.kmn.controller.props.ServerProperties;
import static com.kmn.ws.WebServiceConstants.LOCAL_NAMESPACE_URI;
import com.kmn.ws.bean.PatientInfo;
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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
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
    public static final String XML_ATTR_MID_TRUE = "\" selected=\"true\">";
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
    private String URI = LOCAL_NAMESPACE_URI;
    private ClientConnector cc;
    public ClientService() {
        ServerProperties sp = new ServerProperties(null);
        sp.load();
        sp.getIp();
        sp.getPort();
        URI = "http://"+sp.getIp()+":"+sp.getPort()+"/kmn/services";
        //this.cc = new ClientConnector(URI);
    }
    public PatientInfo retrievePatients(String reqKeyword, String reqClinicId, int reqPageNumber, int reqRowPerPage) throws SOAPException, MalformedURLException, IOException, TransformerException {
        //ClientConnector cc = new ClientConnector(URI);
        if(this.cc==null) this.cc = new ClientConnector(URI);
        return this.cc.getPatients(reqKeyword, reqClinicId, reqPageNumber, reqRowPerPage);
    }
    public Boolean storeResults(String branchId, String patientId, String patientCode, String patientName, String remark, int equipmentId
            , int imageId,DateTime trxDate, DateTime timeStamp, String dataLocation, File dataOutput, String xmlData
            , String creatorId) throws SOAPException, MalformedURLException, IOException, TransformerException, DatatypeConfigurationException {

        //ClientConnector cc = new ClientConnector(URI);
        if(this.cc==null) this.cc = new ClientConnector(URI);
        return this.cc.storeResults(branchId, patientId, patientCode, patientName, remark, equipmentId, imageId, trxDate , timeStamp
                , dataLocation, dataOutput, xmlData, creatorId);
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
    
    public Document convertMessageToXml(String messageFormat, String messageString, String absolutePath, String equipCode) {
        if(equipCode.equals("34")){
            return convertSysmexKX21ToXml(messageString, absolutePath);
        } else {
            return convertMessageToXml(messageFormat, messageString, absolutePath);
        }
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
    
    public Document convertSysmexKX21ToXml(String messageString, String absolutePath) {
        try {
            BufferedReader br = new BufferedReader(new StringReader(messageString));
            StringBuilder sb = new StringBuilder(XML_HEADER+XML_COMM_PREFIX);
            String strLine;
            int count = 1;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                if(!strLine.isEmpty()) {
                    strLine = strLine.replaceAll(AMP, AMP_PE);
                    strLine = strLine.replaceAll(LT, LT_PE);
                    strLine = strLine.replaceAll(GT, GT_PE);
                    strLine = strLine.replaceAll(APOS, APOS_PE);
                    strLine = strLine.replaceAll(QUOT, QUOT_PE);
                    int srcBegin = strLine.indexOf("00C")+3;
                    if (strLine.length()>=srcBegin+90){
                        int WBC_START = srcBegin, WBC_END = srcBegin+5;
                        int RBC_START = srcBegin+5, RBC_END = srcBegin+10;
                        int HGB_START = srcBegin+10, HGB_END = srcBegin+15;
                        int HCT_START = srcBegin+15, HCT_END = srcBegin+20;
                        int MCV_START = srcBegin+20, MCV_END = srcBegin+25;
                        int MCH_START = srcBegin+25, MCH_END = srcBegin+30;
                        int MCHC_START = srcBegin+30, MCHC_END = srcBegin+35;
                        int PLT_START = srcBegin+35, PLT_END = srcBegin+40;
                        int LYMPCT_START = srcBegin+40, LYMPCT_END = srcBegin+45;
                        int MXDPCT_START = srcBegin+45, MXDPCT_END = srcBegin+50;
                        int NEUTPCT_START = srcBegin+50, NEUTPCT_END = srcBegin+55;
                        int LYMCNT_START = srcBegin+55, LYMCNT_END = srcBegin+60;
                        int MXDCNT_START = srcBegin+60, MXDCNT_END = srcBegin+65;
                        int NEUTCNT_START = srcBegin+65, NEUTCNT_END = srcBegin+70;
                        int RDW_START = srcBegin+70, RDW_END = srcBegin+75;
                        int PDW_START = srcBegin+75, PDW_END = srcBegin+80;
                        int MPV_START = srcBegin+80, MPV_END = srcBegin+85;
                        int PLCR_START = srcBegin+85, PLCR_END = srcBegin+90;

                        appendStringBuilder(sb, "WBC", parseSysmexKX21StringToDecimal(3, strLine.substring(WBC_START,WBC_END)));
                        appendStringBuilder(sb, "RBC", parseSysmexKX21StringToDecimal(2, strLine.substring(RBC_START,RBC_END)));
                        appendStringBuilder(sb, "HGB", parseSysmexKX21StringToDecimal(3, strLine.substring(HGB_START,HGB_END)));
                        appendStringBuilder(sb, "HCT", parseSysmexKX21StringToDecimal(3, strLine.substring(HCT_START,HCT_END)));
                        appendStringBuilder(sb, "MCV", parseSysmexKX21StringToDecimal(3, strLine.substring(MCV_START,MCV_END)));
                        appendStringBuilder(sb, "MCH", parseSysmexKX21StringToDecimal(3, strLine.substring(MCH_START,MCH_END)));
                        appendStringBuilder(sb, "MCHC", parseSysmexKX21StringToDecimal(3, strLine.substring(MCHC_START,MCHC_END)));
                        appendStringBuilder(sb, "PLT", parseSysmexKX21StringToDecimal(4, strLine.substring(PLT_START,PLT_END)));
                        appendStringBuilder(sb, "LYM%", parseSysmexKX21StringToDecimal(3, strLine.substring(LYMPCT_START,LYMPCT_END)));
                        appendStringBuilder(sb, "MXD%", parseSysmexKX21StringToDecimal(3, strLine.substring(MXDPCT_START,MXDPCT_END)));
                        appendStringBuilder(sb, "NEUT%", parseSysmexKX21StringToDecimal(3, strLine.substring(NEUTPCT_START,NEUTPCT_END)));
                        appendStringBuilder(sb, "LYM#", parseSysmexKX21StringToDecimal(3, strLine.substring(LYMCNT_START,LYMCNT_END)));
                        appendStringBuilder(sb, "MXD#", parseSysmexKX21StringToDecimal(3, strLine.substring(MXDCNT_START,MXDCNT_END)));
                        appendStringBuilder(sb, "NEUT#", parseSysmexKX21StringToDecimal(3, strLine.substring(NEUTCNT_START,NEUTCNT_END)));
                        appendStringBuilder(sb, "RDW", parseSysmexKX21StringToDecimal(3, strLine.substring(RDW_START,RDW_END)));
                        appendStringBuilder(sb, "PDW", parseSysmexKX21StringToDecimal(3, strLine.substring(PDW_START,PDW_END)));
                        appendStringBuilder(sb, "MPV", parseSysmexKX21StringToDecimal(3, strLine.substring(MPV_START,MPV_END)));
                        appendStringBuilder(sb, "P-LCR", parseSysmexKX21StringToDecimal(3, strLine.substring(PLCR_START,PLCR_END)));
                    } else {
                        return null;//appendStringBuilder(sb, DESC+count, strLine);
                    }
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
    
    private String parseSysmexKX21StringToDecimal(int offset, String string) {
        if(string.substring(string.length()-1, string.length()).equals("0")) {
            if(string.substring(offset, string.length()-1).isEmpty()) {
                return Integer.valueOf(string.substring(0,offset)).toString();
            } else {
                return Integer.valueOf(string.substring(0,offset)) + "." + string.substring(offset, string.length()-1);
            }
        } else {
            return Integer.valueOf(string.substring(0,offset)) + "." + string.substring(offset, string.length()-1) + "-";
        }
    }
    private void appendStringBuilder(StringBuilder sb, String description, String value) {
        sb.append(XML_ATTR_PREFIX);
        sb.append(description);
        sb.append(XML_ATTR_MID_TRUE);
        sb.append(value);
        sb.append(XML_ATTR_SUFFIX);
    }
    
    public String toString(Document document) throws TransformerConfigurationException, TransformerException {
        TransformerFactory factory1 = TransformerFactory.newInstance();
        Transformer transformer = factory1.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        document.getElementsByTagName("item");
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
    
    public String getParsedStringFromXmlFile(String absolutePath) throws TransformerConfigurationException, TransformerException, ParserConfigurationException, SAXException, IOException {
        Document doc = readXml(absolutePath);
        DocumentTraversal traversal = (DocumentTraversal) doc;
        Node a = doc.getDocumentElement();
        NodeIterator iterator = traversal.createNodeIterator(a, NodeFilter.SHOW_ELEMENT, null, true);
        for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
            Element e = (Element) n;
            if(e.getAttribute(ATTR_SELECTED).equals("false")){
                a.removeChild(n);
            } else {
                e.removeAttribute(ATTR_SELECTED);
                e.removeAttribute(ATTR_NAME);
            }
        }
        return toString(a.getOwnerDocument());
    }
}
