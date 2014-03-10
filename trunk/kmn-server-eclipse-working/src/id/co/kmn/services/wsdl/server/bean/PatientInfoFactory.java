package id.co.kmn.services.wsdl.server.bean;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/27/12
 * Time: 9:07 PM
 */
public abstract class PatientInfoFactory {
    /**
     * Default constructor is protected on purpose.
     */
    protected PatientInfoFactory() { }

    public static PatientInfoFactory newInstance() {
        return null;
    }

    public static PatientInfoFactory newInstance(String factoryClassName, ClassLoader classLoader) {
        return null;
    }
}
