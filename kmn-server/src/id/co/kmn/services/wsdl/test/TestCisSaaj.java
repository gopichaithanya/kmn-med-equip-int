package id.co.kmn.services.wsdl.test;

import id.co.kmn.services.wsdl.client.CisService;
import org.joda.time.DateTime;
import static id.co.kmn.services.wsdl.WebServiceConstant.*;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/31/12
 * Time: 4:58 PM
 */
public class TestCisSaaj {
    public static void main(String[] args) throws Exception {
        CisService cs = new CisService(CIS_NAMESPACE_URI);
        //http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/
        //CisService cs = new CisService("http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/");
        //SaajGetPatients getPatients = new SaajGetPatients(LOCAL_NAMESPACE_URI);
        //cs.getPatients("%joko%", "8", 1, 10);
        DateTime dt = new DateTime();
        cs.putPatientData("500488231", 1, "reqImageURL", "reqDataXML", dt);
    }
}
