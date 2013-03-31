package id.co.kmn.services.wsdl.server;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.mime.Attachment;
import org.springframework.ws.server.endpoint.interceptor.EndpointInterceptorAdapter;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import java.io.File;
/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 8/2/12
 * Time: 6:54 PM
 */
public class MessageEndpointInterceptorAdapter extends EndpointInterceptorAdapter {
    private static final String CONTENT_ID = "dataOutput";

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        SaajSoapMessage requestMessage = (SaajSoapMessage)messageContext.getRequest();

        Attachment attachment = requestMessage.getAttachment(CONTENT_ID);
        System.out.println("attachment size: "+attachment.getSize());
        //Write the file to disk
        saveByteArrayToFile(attachment);
        return true;
    }

    private void saveByteArrayToFile(Attachment attachment) throws Exception {
        byte[] bytes = IOUtils.toByteArray(attachment.getInputStream());
        File tmp = File.createTempFile("C;/somefile", ".dat");
        FileUtils.writeByteArrayToFile(tmp, bytes);
        System.out.println("Wrote tmp file to: " + tmp.getAbsolutePath());
    }
}
