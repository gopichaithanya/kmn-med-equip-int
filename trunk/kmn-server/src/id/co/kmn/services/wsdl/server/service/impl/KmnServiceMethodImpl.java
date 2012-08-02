package id.co.kmn.services.wsdl.server.service.impl;

import com.sun.xml.internal.ws.message.ByteArrayAttachment;
import id.co.kmn.administrasi.dao.EquipmentDAO;
import id.co.kmn.administrasi.dao.SystemDAO;
import id.co.kmn.administrasi.dao.TransactionDAO;
import id.co.kmn.backend.model.Tmedequipment;
import id.co.kmn.services.wsdl.client.CisService;
import id.co.kmn.services.wsdl.server.bean.Patient;
import id.co.kmn.services.wsdl.server.bean.PatientInfo;
import id.co.kmn.services.wsdl.server.schema.StoreResultsResponse;
import id.co.kmn.services.wsdl.server.service.KmnServiceMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static id.co.kmn.services.wsdl.WebServiceConstant.CIS_NAMESPACE_URI;
import static id.co.kmn.services.wsdl.WebServiceConstant.CIS_WSDL_URL;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/26/12
 * Time: 12:25 PM
 */
@Service
@Transactional(readOnly = true)
public class KmnServiceMethodImpl implements KmnServiceMethod {
    private static final Log logger = LogFactory.getLog(KmnServiceMethodImpl.class);
    private SystemDAO systemDAO;
    private TransactionDAO transactionDAO;
    private EquipmentDAO equipmentDAO;
    //private TrxEquipmentSecurityService trxEquipmentSecurityService = new StubTrxEquipmentSecurityService();

//    @Autowired
//    public KmnServiceImpl(TrxEquipmentDAO trxEquipmentDao) {
//        this.trxEquipmentDao = trxEquipmentDao;
//    }
    @Autowired
    public KmnServiceMethodImpl(SystemDAO systemDAO, TransactionDAO transactionDAO, EquipmentDAO equipmentDAO) {
        this.systemDAO = systemDAO;
        this.transactionDAO = transactionDAO;
        this.equipmentDAO = equipmentDAO;
    }

//    @Autowired(required = false)
//    public void setTrxEquipmentSecurityService(TrxEquipmentSecurityService trxEquipmentSecurityService) {
//        this.trxEquipmentSecurityService = trxEquipmentSecurityService;
//    }

    public List<Patient> getPatients(String reqKeyword, String reqClinicId, int reqPageNumber, int reqRowPerPage) {
        List<Patient> patientList;
        try {
//            IntegrasiAlat_Service service;
//                QName serviceName = new QName(WebServiceConstant.CIS_NAMESPACE_URI, WebServiceConstant.QNAME_LOCAL_PART);
//                service = new IntegrasiAlat_Service(new URL(WebServiceConstant.WSDL_LOCATION_URL), serviceName);
//            IntegrasiAlat integrasiAlat = service.getIntegrasiAlatSOAP();
//            GetPatientList request = new GetPatientList();
//            request.setReqKeyword("%"+reqKeyword+"%");
//            if(!reqClinicId.isEmpty()) {
//                request.setReqClinicId(reqClinicId);
//            } else {
//                request.setReqClinicId(WebServiceConstant.DEFAULT_CLINIC_ID);
//            }
//            if(reqPageNumber >= 0){
//                request.setReqPageNumber(reqPageNumber);
//            } else {
//                request.setReqPageNumber(WebServiceConstant.DEFAULT_PAGE_NUMBER);
//            }
//            if(reqRowPerPage >= 0) {
//                request.setReqRowPerPage(reqRowPerPage);
//            } else {
//                request.setReqRowPerPage(WebServiceConstant.DEFAULT_PAGE_ROW);
//            }
//            System.out.format("Requesting patients: ", request.toString());
            //TO DO: complete process
            //GetPatientListResponse response = integrasiAlat.;
            //patientList = response;
            patientList = generateDefaultPatientList();
//        } catch (MalformedURLException e) {
//            System.out.println("Invalid wsdl location: " + WebServiceConstant.WSDL_LOCATION_URL);
//            e.printStackTrace();
//            patientList = generateDefaultPatientList();
        } catch (SOAPFaultException ex) {
            System.out.format("SOAP Fault Code    %1s%n", ex.getFault().getFaultCodeAsQName());
            System.out.format("SOAP Fault String: %1s%n", ex.getFault().getFaultString());
            patientList = generateDefaultPatientList();
        }
        return patientList;
    }

    public PatientInfo getPatientInfo(String reqKeyword, String reqClinicId, int reqPageNumber, int reqRowPerPage) {
        PatientInfo patientInfo;
        try {
            CisService cs = new CisService(CIS_NAMESPACE_URI);
            patientInfo = cs.getPatients(reqKeyword, reqClinicId, reqPageNumber, reqRowPerPage);
        } catch (MalformedURLException e) {
            System.out.println("Invalid wsdl location: " + CIS_WSDL_URL);
            e.printStackTrace();
            patientInfo = generateDefaultPatientInfo();
        } catch (SOAPException ex) {
            System.out.format("SOAP Exception Message:    %1s%n", ex.getMessage());
            patientInfo = generateDefaultPatientInfo();
        } catch (IOException e) {
            e.printStackTrace();
            patientInfo = generateDefaultPatientInfo();
        } catch (TransformerException e) {
            e.printStackTrace();
            patientInfo = generateDefaultPatientInfo();
        }
        return patientInfo;
    }

    @Override
    public StoreResultsResponse storeResults(String branchId, String patientId, String patientCode, String patientName, String remark, int equipmentId, int imageId, DateTime trxDate, DateTime timeStamp, String dataLocation, String xmlData, String creatorId) {

        StoreResultsResponse response = new StoreResultsResponse();
        try {
            //save to Database;
            Tmedequipment trx = transactionDAO.getNew();
            trx.setBranchCode(branchId);
            trx.setPatientId(patientId);
            trx.setPatientCode(patientCode);
            trx.setLastName(patientName);
            trx.setRemark(remark);
            trx.setMmedequipment(equipmentDAO.getById(equipmentId));
            trx.setImageId(String.valueOf(imageId));
            trx.setTrxDate(trxDate.toDate());
            trx.setTimestamp(timeStamp.toDate());
            trx.setDataLocation(dataLocation);
            //trx.setDataOutput(dataOutput);
            trx.setCreatorId(creatorId);
            transactionDAO.saveOrUpdate(trx);
            // save image to localserver in public location
            String path = systemDAO.getByCode("IMAGE_DIR").getSystemValue();
            dataLocation = dataLocation.replaceFirst("C:/kmntmp", "");
            File file = new File(path+dataLocation);
            if(!file.exists()){
                file.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            //bos.write(dataOutput);
            bos.flush();
            bos.close();

            //notify cis;

            CisService cs = new CisService(CIS_NAMESPACE_URI);
            cs.putPatientData(patientId, equipmentId, dataLocation, xmlData, trxDate);
            response.setSuccess(true);
            response.setResult("resStatusXML");
            return response;
        } catch (SOAPException e) {
            e.printStackTrace();
            response.setResult(e.getMessage());
            response.setSuccess(false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.setResult(e.getMessage());
            response.setSuccess(false);
        } catch (TransformerException e) {
            e.printStackTrace();
            response.setResult(e.getMessage());
            response.setSuccess(false);
        } catch (IOException e) {
            e.printStackTrace();
            response.setResult(e.getMessage());
            response.setSuccess(false);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            response.setResult(e.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public Boolean storeResults(String branchId, String patientId, String patientCode, String patientName, String remark, int equipmentId, int imageId, DateTime trxDate, DateTime timeStamp, String dataLocation, ByteArrayAttachment dataOutput, String xmlData, String creatorId) {
        //TO DO: complete process
        return false;
    }

    private PatientInfo generateDefaultPatientInfo() {
        PatientInfo patientInfo = new PatientInfo();
        patientInfo.setResPageNumber(1);
        patientInfo.setResRowThisPage(2);
        patientInfo.setResRowPerPage(10);
        patientInfo.setResRowTotal(2);
        patientInfo.setPatients(generateDefaultPatientList());
        return patientInfo;
    }

    private List<Patient> generateDefaultPatientList() {
        List<Patient> patientList = new ArrayList<Patient>();
        Patient p1 = new Patient("500488231", "STEPHANUS BUDI RAHARDJO", "", "100094871", "dr. HADI PRAKOSO, SpM");
        Patient p2 = new Patient("500490641", "BUDIYANTI BENYAMIN", "", "140", "dr. HENRY WAROUW, SpM");
        patientList.add(p1);
        patientList.add(p2);
        return patientList;
    }
}
