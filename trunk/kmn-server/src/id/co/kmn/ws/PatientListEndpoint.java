package id.co.kmn.ws;

import org.springframework.ws.server.endpoint.AbstractSaxPayloadEndpoint;
import org.xml.sax.ContentHandler;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/9/12
 * Time: 6:04 PM
 */
public class PatientListEndpoint extends AbstractSaxPayloadEndpoint {
    private static final String NAMESPACE_URI = "http://www.springframework.org/spring-ws/samples/pox";

    private static final String CONTANT_NAME = "Contact";

    private static final String CONTANT_COUNT_NAME = "ContactCount";

    private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    @Override
    protected ContentHandler createContentHandler() throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected Source getResponse(ContentHandler contentHandler) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
