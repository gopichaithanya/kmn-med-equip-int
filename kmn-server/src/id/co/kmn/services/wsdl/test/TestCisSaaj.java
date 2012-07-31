package id.co.kmn.services.wsdl.test;

import id.co.kmn.services.wsdl.client.CisService;
import org.joda.time.DateTime;

import javax.xml.soap.*;

import static id.co.kmn.services.wsdl.WebServiceConstant.*;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/31/12
 * Time: 4:58 PM
 */
public class TestCisSaaj {
    public static void main(String[] args) throws Exception {
        //CisService cs = new CisService(CIS_NAMESPACE_URI);
        //http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/
        CisService cs = new CisService("http://192.168.13.10:2221/apps/kmn/IntegrasiAlat");
        //SaajGetPatients getPatients = new SaajGetPatients(LOCAL_NAMESPACE_URI);
        //getPatients.getPatients();
        sendSoapLoginMessage("a","8","1","10");
        cs.getPatients("a", "8", 1, 10);
        cs.putPatientData("reqPatientId", 1, "reqImageURL", "reqDataXML", new DateTime());
    }

    public static String sendSoapLoginMessage(String REQKEYWORDinput, String REQCLINICIDinput,
                                       String REQPAGENUMBERinput, String REQROWPERPAGEinput) throws Exception
    {
        SOAPFactory soapFactory = SOAPFactory.newInstance();
        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage message = mf.createMessage();
        SOAPBody body = message.getSOAPBody();
        // You can detach the SOAP header in this example
        // msg.getSOAPHeader().detachNode();


        Name bodyName = soapFactory.createName(CIS_GET_PATIENT_LIST , "", "http://192.168.13.10:2221/apps/kmn/IntegrasiAlat");

        SOAPElement xmlElement = body.addBodyElement(bodyName);


        Name REQKEYWORDName = soapFactory.createName(REQKEYWORD);
        SOAPElement loginElement = xmlElement.addChildElement(REQKEYWORDName);

        SOAPElement child = soapFactory.createElement(REQKEYWORD);
        child.addTextNode(REQKEYWORDinput);

        loginElement.addChildElement(child);

        child = soapFactory.createElement(REQCLINICID);
        child.addTextNode(REQCLINICIDinput);

        loginElement.addChildElement(child);

        child = soapFactory.createElement(REQPAGENUMBER);
        child.addTextNode(REQPAGENUMBERinput);

        loginElement.addChildElement(child);

        child = soapFactory.createElement(REQROWPERPAGE);
        child.addTextNode(REQROWPERPAGEinput);

        loginElement.addChildElement(child);

        message.saveChanges();

        // Send the message
        System.out.println("Send the SOAP message\n");


        String server = "http://192.168.13.10:2221/apps/kmn/IntegrasiAlat";
        //String response = "";
        SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(message, server);
        if (!response.getSOAPBody().hasFault()) {
            //patientInfo = writeGetPatientsResponse(response);
            System.out.println("success");
            return "true";
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println("failed");
            System.err.println("MSG_SOAP_FAULT_CODE" + fault.getFaultCode());
            System.err.println("MSG_SOAP_FAULT_DESC" + fault.getFaultString());
            return "false";
        }
//        try
//        {
//            if(out==null)
//            {
//                if(uc == null)
//                {
//                    u = new URL(DEFAULT_SERVER);
//                    uc = u.openConnection();
//                }
//
//                if(urlconnection == null)
//                {
//                    urlconnection = (HttpURLConnection) uc;
//                    urlconnection.setDoOutput(true);
//                    urlconnection.setDoInput(true);
//                    urlconnection.setRequestMethod("POST");
//                    urlconnection.setRequestProperty("SOAPAction", SOAP_ACTION);
//                }
//                out = urlconnection.getOutputStream();
//            }
//
//            if(wout == null)
//                wout = new OutputStreamWriter(out);
//
//            String soapmessage = printMessage(message);
//            wout.write(soapmessage);
//
//            wout.flush();
//            //wout.close();
//            System.out.println(2);
//            if(is == null)
//                is = urlconnection.getInputStream();
//            response = readServerResponseMessage(is);
//            //is.close();
//            int sizemore = "</loginResponse>".length();
//            response = response.substring(response.indexOf("<loginResponse>"),response.indexOf("</loginResponse>")+sizemore);
//            System.out.println("[SERVER RESPONSE]"+response);
//        }
//        catch (IOException e)
//        {
//            System.err.println(e);
//        }
//        catch(Exception eb)
//        {
//            System.err.println(eb);
//        }
//        return response;
    }

//    public void closeConnections()
//    {
//            try
//            {
//                    wout.close();
//                    is.close();
//            }
//            catch(Exception e)
//            {
//                    e.printStackTrace();
//            }
//    }
//
//    public String readServerResponseMessage(InputStream is)
//    {
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        char[] readChars = new char[65000];
//        String response = "";
//        try
//        {
//                while (br.read(readChars) != -1)
//                {
//                        response = new String(readChars);
//                     break;
//
//                }
//
//        }
//        catch (IOException e)
//        {
//                        e.printStackTrace();
//        }
//        catch(Exception e)
//        {
//                        e.printStackTrace();
//        }
//
//        return response;
//    }
}
