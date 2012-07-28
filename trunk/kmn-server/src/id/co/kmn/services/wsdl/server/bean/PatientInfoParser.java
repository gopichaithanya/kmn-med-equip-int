package id.co.kmn.services.wsdl.server.bean;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/27/12
 * Time: 9:21 PM
 */
public class PatientInfoParser {
//    public void setVariable(SOAPElement soapElement) {
//        if(soapElement.hasAttribute(RESPAGENUMBER)){
//            this.resPageNumber = Integer.valueOf(soapElement.getTextContent());
//        } else if(soapElement.hasAttribute(RESROWTHISPAGE)){
//            this.resRowThisPage = Integer.valueOf(soapElement.getTextContent());
//        } else if(soapElement.hasAttribute(RESROWPERPAGE)){
//            this.resPageNumber = Integer.valueOf(soapElement.getTextContent());
//        } else if(soapElement.hasAttribute(RESROWTOTAL)){
//            this.resPageNumber = Integer.valueOf(soapElement.getTextContent());
//        } else if(soapElement.hasAttribute(PATIENTS)){
//            this.patients = setPatientsByXml(soapElement.getNodeValue());
//        }
//    }

    public List<Patient> setPatientsByXml(String resRowsXML) {
        List<Patient> patients = new ArrayList<Patient>();
        try {
            StringBuilder sb = new StringBuilder("<root>");
            sb.append(resRowsXML);
            sb.append("</root>");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(sb.toString())));
            DOMSource src = new DOMSource( doc );
            NodeList patientInfo = doc.getElementsByTagName(Patient.TAG_PATIENTINFO);
            String data1 = "", data2 = "", data3 = "", data4 = "", data5 = "";
            for (int i = 0; i < patientInfo.getLength(); i++) {
                data1 = getStringNodeValue(doc.getElementsByTagName(Patient.TAG_PATIENTID).item(i));
                data2 = getStringNodeValue(doc.getElementsByTagName(Patient.TAG_PATIENTNAME).item(i));
                data3 = getStringNodeValue(doc.getElementsByTagName(Patient.TAG_PATIENTBRM).item(i));
                data4 = getStringNodeValue(doc.getElementsByTagName(Patient.TAG_DOCID).item(i));
                data5 = getStringNodeValue(doc.getElementsByTagName(Patient.TAG_DOCNAME).item(i));
                String line = data1 + " " + data2 + " " + data3+ " " + data4+ " " + data5;
                System.out.println(line);
                Patient patient = new Patient(data1, data2, data3, data4, data5);
                patients.add(patient);
            }
        } catch (SAXException e) {

        } catch (IOException e) {

        } catch (ParserConfigurationException e) {

        }
        return null;
    }

    public String getStringNodeValue(Node node) {
        String result = "";
        if(node.hasChildNodes())
            result = node.getFirstChild().getNodeValue();
        return result;
    }
}
