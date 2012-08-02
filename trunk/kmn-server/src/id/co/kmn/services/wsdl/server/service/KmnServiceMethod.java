package id.co.kmn.services.wsdl.server.service;

import id.co.kmn.services.wsdl.server.bean.Patient;
import id.co.kmn.services.wsdl.server.bean.PatientInfo;
import id.co.kmn.services.wsdl.server.schema.StoreResultsResponse;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/26/12
 * Time: 11:45 AM
 */
public interface KmnServiceMethod {
    /**
     * Returns a list of <code>Patient</code> with the given parameter.
     *
     * @param reqKeyword the patient name keyword
     * @param reqClinicId the clinic Id
     * @param reqPageNumber the page number of query results
     * @param reqRowPerPage the number of rows per page
     * @return a list of patients
     */
    List<Patient> getPatients(String reqKeyword, String reqClinicId, int reqPageNumber, int reqRowPerPage);

     /**
     * Returns a list of <code>Patient</code> with the given parameter.
     *
     * @param reqKeyword the patient name keyword
     * @param reqClinicId the clinic Id
     * @param reqPageNumber the page number of query results
     * @param reqRowPerPage the number of rows per page
     * @return a list of patients
     */
    PatientInfo getPatientInfo(String reqKeyword, String reqClinicId, int reqPageNumber, int reqRowPerPage);

    /**
     * Store the equipment output results with patient data attached.
     *
     * @param branchId
     * @param patientId
     * @param patientCode
     * @param patientName
     * @param remark
     * @param imageId
     * @param trxDate
     * @param timeStamp
     * @param dataLocation
     * @param xmlData
     * @param creatorId
     * @return the boolean success result
     */
    StoreResultsResponse storeResults(String branchId, String patientId, String patientCode, String patientName,
           String remark, int equipmentId, int imageId,DateTime trxDate, DateTime timeStamp,
           String dataLocation, String xmlData, String creatorId);
}
