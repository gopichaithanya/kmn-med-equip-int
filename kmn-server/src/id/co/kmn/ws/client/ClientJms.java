package id.co.kmn.ws.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.xml.transform.StringSource;

import javax.jms.JMSException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/12/12
 * Time: 9:34 PM
 */
public class ClientJms extends WebServiceGatewaySupport {
    private static final String PAYLOAD =
            "<IntegrasiAlat:GetFlightsRequest xmlns:airline=\"http://www.springframework.org/spring-ws/samples/airline/schemas/messages\">" +
                    "<airline:from>AMS</airline:from>" + "<airline:to>VCE</airline:to>" +
                    "<airline:departureDate>2006-01-31</airline:departureDate>" + "</airline:GetFlightsRequest>";

    public void getPatientList() throws SOAPException, IOException, TransformerException, JMSException {
        getWebServiceTemplate().sendSourceAndReceiveToResult(new StringSource(PAYLOAD), new StreamResult(System.out));
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml", ClientJms.class);
        ClientJms jmsClient = (ClientJms) applicationContext.getBean("jmsClient", ClientJms.class);
        jmsClient.getPatientList();
    }
}
