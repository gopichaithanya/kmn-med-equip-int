package id.co.kmn.services.wsdl.client;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/27/12
 * Time: 1:22 AM
 */
@WebServiceClient(name = "KmnService", targetNamespace = "http://localhost:9090/kmn/KmnService/", wsdlLocation = "http://localhost:9090/kmn/KmnService.wsdl")
public class KmnService_Service extends Service {
    private final static URL KMNSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(id.co.kmn.services.wsdl.client.KmnService_Service.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = id.co.kmn.services.wsdl.client.KmnService_Service.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:9090/kmn/KmnService.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:9090/kmn/KmnService.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        KMNSERVICE_WSDL_LOCATION = url;
    }

    public KmnService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public KmnService_Service() {
        super(KMNSERVICE_WSDL_LOCATION, new QName("http://localhost:9090/kmn/KmnService/", "KmnService"));
    }

    /**
     *
     * @return
     *     returns KmnService
     */
    @WebEndpoint(name = "KmnServiceSOAP")
    public KmnService getKmnServiceSOAP() {
        return super.getPort(new QName("http://localhost:9090/kmn/KmnService/", "KmnServiceSOAP"), KmnService.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IntegrasiAlat
     */
    @WebEndpoint(name = "KmnServiceSOAP")
    public KmnService getKmnServiceSOAP(WebServiceFeature... features) {
        return super.getPort(new QName("http://localhost:9090/kmn/KmnService/", "KmnServiceSOAP"), KmnService.class, features);
    }
}
