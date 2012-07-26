package id.co.kmn.services.wsdl.server.schema.support;

import id.co.kmn.services.wsdl.server.bean.Patient;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/26/12
 * Time: 8:36 PM
 */
public abstract class SchemaConversionUtils {

    private SchemaConversionUtils() {
    }

    public static Patient toSchemaType(id.co.kmn.services.wsdl.server.bean.Patient domainPatient)
            throws DatatypeConfigurationException {
        Patient schemaPatient = new Patient();
        schemaPatient.setPatientId(domainPatient.getPatientId());
        schemaPatient.setPatientName(domainPatient.getPatientName());
        schemaPatient.setPatientBrm(domainPatient.getPatientBrm());
        schemaPatient.setDocId(domainPatient.getDocId());
        schemaPatient.setDocName(domainPatient.getDocName());
        return schemaPatient;
    }

    public static List<Patient> toSchemaType(List<id.co.kmn.services.wsdl.server.bean.Patient> domainPatients)
            throws DatatypeConfigurationException {
        List<Patient> schemaPatients = new ArrayList<Patient>(domainPatients.size());
        for (id.co.kmn.services.wsdl.server.bean.Patient domainPatient : domainPatients) {
            schemaPatients.add(toSchemaType(domainPatient));
        }
        return schemaPatients;
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(DateTime dateTime) throws DatatypeConfigurationException {
        DatatypeFactory factory = DatatypeFactory.newInstance();
        return factory.newXMLGregorianCalendar(dateTime.toGregorianCalendar());
    }

    public static DateTime toDateTime(XMLGregorianCalendar calendar) {
        int timeZoneMinutes = calendar.getTimezone();
        DateTimeZone timeZone = DateTimeZone.forOffsetHoursMinutes(timeZoneMinutes / 60, timeZoneMinutes % 60);
        return new DateTime(calendar.getYear(), calendar.getMonth(), calendar.getDay(), calendar.getHour(),
                calendar.getMinute(), calendar.getSecond(), calendar.getMillisecond(), timeZone);
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDate localDate)
            throws DatatypeConfigurationException {
        DatatypeFactory factory = DatatypeFactory.newInstance();
        return factory.newXMLGregorianCalendarDate(localDate.getYear(), localDate.getMonthOfYear(),
                localDate.getDayOfMonth(), DatatypeConstants.FIELD_UNDEFINED);
    }

    public static LocalDate toLocalDate(XMLGregorianCalendar calendar) {
        return new LocalDate(calendar.getYear(), calendar.getMonth(), calendar.getDay());
    }
}
