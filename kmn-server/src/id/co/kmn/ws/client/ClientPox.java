package id.co.kmn.ws.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.xml.transform.ResourceSource;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.xpath.XPathExpression;

import javax.xml.transform.Source;
import java.io.IOException;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/12/12
 * Time: 9:39 PM
 */
public class ClientPox extends WebServiceGatewaySupport {
    private Resource request;

    private XPathExpression expression;

    public ClientPox(WebServiceMessageFactory messageFactory) {
        super(messageFactory);
    }

    public void setRequest(Resource request) {
        this.request = request;
    }

    public void setExpression(XPathExpression expression) {
        this.expression = expression;
    }

    public void getPatientList() throws IOException {
		System.out.println("Running POX Client on local server");
        Source requestSource = new ResourceSource(request);
        //DOMResult result = new DOMResult();
        StringResult result = new StringResult();
        getWebServiceTemplate().sendSourceAndReceiveToResult(requestSource, result);
        //int resultInt = (int) expression.evaluateAsNumber(result.getNode());
        //String resultString = (String) expression.evaluateAsString(result.getNode());
        String resultToString = result.toString();
        //System.out.println("resultInt = " + resultInt);
        //System.out.println("resultString = " + resultString);
        System.out.println("resultToString = " + resultToString);
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml", ClientPox.class);
        ClientPox clientPox = (ClientPox) applicationContext.getBean("clientPox");
        clientPox.getPatientList();
    }
}
