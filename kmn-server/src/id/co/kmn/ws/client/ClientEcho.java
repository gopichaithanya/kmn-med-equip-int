package id.co.kmn.ws.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.xml.transform.ResourceSource;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.xpath.XPathExpression;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import java.io.IOException;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/12/12
 * Time: 11:01 PM
 */
public class ClientEcho extends WebServiceGatewaySupport {
    private Resource request;

    private XPathExpression expression;

    public void setRequest(Resource request) {
        this.request = request;
    }

    public void setExpression(XPathExpression expression) {
        this.expression = expression;
    }

    public void echo() throws IOException {
        Source requestSource = new ResourceSource(request);
        //StringResult result = new StringResult();
        DOMResult result = new DOMResult();
        getWebServiceTemplate().sendSourceAndReceiveToResult(requestSource, result);
        System.out.println();
        System.out.println("Echo: "+result);
        System.out.println();
        //String resultString = (String) expression.evaluateAsString(result.getNode());
        //String resultToString = result.toString();
        //System.out.println("resultString = " + resultString);
        //System.out.println("resultToString = " + resultToString);
    }

    public String echoString() throws IOException {
        Source requestSource = new ResourceSource(request);
        StringResult result = new StringResult();
        getWebServiceTemplate().sendSourceAndReceiveToResult(requestSource, result);
        System.out.println("Echo: "+result);
        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext-clientecho.xml", ClientEcho.class);
        ClientEcho clientEcho = (ClientEcho) applicationContext.getBean("clientEcho");
        clientEcho.echo();
    }
}
