package id.co.kmn.ws;

import org.springframework.ws.server.endpoint.AbstractSaxPayloadEndpoint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/12/12
 * Time: 10:00 PM
 */
public class GetPatientListEndpoint extends AbstractSaxPayloadEndpoint {
    private static final String NAMESPACE_URI = "http://localhost:9090/kmn/";
    private static final String CIS_URI = "http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/";
    private static final String REQ_LIST_NAME = "getPatientList";
    private static final String RES_LIST_NAME = "getPatientListResponse";

    private static final String REQ_KEYWORD = "reqKeyword";
    private static final String REQ_CLINIC_ID = "reqClinicId";
    private static final String REQ_PAGE_NUMBER = "reqPageNumber";
    private static final String REQ_ROW_PER_PAGE = "reqRowPerPage";

    private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    public GetPatientListEndpoint() {
        documentBuilderFactory.setNamespaceAware(true);
    }
    @Override
    protected ContentHandler createContentHandler() throws Exception {
        return new PatientList();
    }
    @Override
    protected Source getResponse(ContentHandler contentHandler) throws Exception {
        PatientList patientList = (PatientList) contentHandler;

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document response = documentBuilder.newDocument();
        Element contactCountElement = response.createElementNS(NAMESPACE_URI, RES_LIST_NAME);
        response.appendChild(contactCountElement);
        //contactCountElement.setTextContent(Integer.toString(patientList.resPageNumber));
        contactCountElement.setTextContent("Test Result");

        return new DOMSource(response);
    }

    private static class PatientList extends DefaultHandler {

        private int contactCount = 0;
        private int resPageNumber = 0;
        private int resRowThisPage = 0;
        private int resRowPerPage = 0;
        private int resRowTotal = 0;
        private String resRowsXML = "";

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            System.out.println("URI: " + uri);
            System.out.println("localName: " + localName);
            System.out.println("qName: " + qName);
            System.out.println("Attributes: " + attributes);
            if (NAMESPACE_URI.equals(uri) && REQ_LIST_NAME.equals(localName)) {
                resPageNumber++;
                //send request to cis
                //return response to client
            }
        }
    }
}
