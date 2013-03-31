package id.co.kmn.services.wsdl.server.service;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import static id.co.kmn.services.wsdl.WebServiceConstant.*;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/27/12
 * Time: 1:22 AM
 */
@WebServiceClient(name = QNAME_LOCAL_SERVER, targetNamespace = KMNSERVICE_TNS_URL, wsdlLocation = KMNSERVICE_WSDL_URL)
public class KmnService_Service extends Service {
    private final static URL KMNSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(KmnService_Service.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = KmnService_Service.class.getResource(".");
            url = new URL(baseUrl, KMNSERVICE_WSDL_URL);
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: '"+KMNSERVICE_WSDL_URL+"', retrying as a local file");
            logger.warning(e.getMessage());
        }
        KMNSERVICE_WSDL_LOCATION = url;
    }

    public KmnService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public KmnService_Service() {
        super(KMNSERVICE_WSDL_LOCATION, new QName(KMNSERVICE_TNS_URL, QNAME_LOCAL_SERVER));
    }

    /**
     *
     * @return
     *     returns KmnService
     */
    @WebEndpoint(name = QNAME_LOCAL_SOAP)
    public KmnService getKmnServiceSOAP() {
        return super.getPort(new QName(KMNSERVICE_TNS_URL, QNAME_LOCAL_SOAP), KmnService.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IntegrasiAlat
     */
    @WebEndpoint(name = QNAME_LOCAL_SOAP)
    public KmnService getKmnServiceSOAP(WebServiceFeature... features) {
        return super.getPort(new QName(KMNSERVICE_TNS_URL, QNAME_LOCAL_SOAP), KmnService.class, features);
    }
}
