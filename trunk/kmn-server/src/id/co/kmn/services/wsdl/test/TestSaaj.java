package id.co.kmn.services.wsdl.test;

import static id.co.kmn.services.wsdl.WebServiceConstant.*;
/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/27/12
 * Time: 1:51 AM
 */
public class TestSaaj {
    public static void main(String[] args) throws Exception {
        SaajGetPatients getPatients = new SaajGetPatients(LOCAL_NAMESPACE_URI);
        getPatients.getPatients();
    }
}
