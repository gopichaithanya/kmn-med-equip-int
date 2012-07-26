package id.co.kmn.services.wsdl.test;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/27/12
 * Time: 1:51 AM
 */
public class TestSaaj {
    public static void main(String[] args) throws Exception {
        String url = "http://localhost:9090/kmn/services";
        SaajGetPatients getPatients = new SaajGetPatients(url);
        getPatients.getPatients();
    }
}
