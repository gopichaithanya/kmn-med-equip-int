package id.co.kmn.services.wsdl.server.service.impl;

import com.sun.xml.internal.ws.message.ByteArrayAttachment;
import id.co.kmn.administrasi.dao.EquipmentDAO;
import id.co.kmn.administrasi.dao.SystemDAO;
import id.co.kmn.administrasi.dao.TransactionDAO;
import id.co.kmn.backend.dao.UserDAO;
import id.co.kmn.backend.model.SecUser;
import id.co.kmn.backend.model.Tmedequipment;
import id.co.kmn.services.wsdl.client.CisService;
import id.co.kmn.services.wsdl.server.bean.Patient;
import id.co.kmn.services.wsdl.server.bean.PatientInfo;
import id.co.kmn.services.wsdl.server.schema.StoreResultsResponse;
import id.co.kmn.services.wsdl.server.service.KmnServiceMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.mime.Attachment;

import javax.sql.rowset.serial.SerialBlob;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private UserDAO userDAO;

    @Autowired
    public KmnServiceMethodImpl(SystemDAO systemDAO, TransactionDAO transactionDAO, EquipmentDAO equipmentDAO, UserDAO userDAO) {
        this.systemDAO = systemDAO;
        this.transactionDAO = transactionDAO;
        this.equipmentDAO = equipmentDAO;
        this.userDAO = userDAO;
    }

    public List<Patient> getPatients(String reqKeyword, String reqClinicId, int reqPageNumber, int reqRowPerPage) {
        List<Patient> patientList;
        try {
            patientList = generateDefaultPatientList();
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
            if (logger.isDebugEnabled()) {
                logger.debug("KmnServiceMethodImpl.getPatientInfo - keyword: '" + reqKeyword + "' id: '" + reqClinicId + "' page: "
                        + reqPageNumber + "reqRowPerPage: " + reqRowPerPage);
            }
            //CisService cs = new CisService(CIS_NAMESPACE_URI);
            CisService cs = new CisService(systemDAO.getByCode("CIS_NAMESPACE_URI").getSystemValue());
            if (reqClinicId.equalsIgnoreCase("1")){
            	patientInfo = cs.getPatients(reqKeyword, systemDAO.getByCode("KDN_CLINIC_ID").getSystemValue(), reqPageNumber, reqRowPerPage);
            } else {
            	patientInfo = cs.getPatients(reqKeyword, systemDAO.getByCode("CIS_CLINIC_ID").getSystemValue(), reqPageNumber, reqRowPerPage);
            	//patientInfo = cs.getPatients(reqKeyword, reqClinicId, reqPageNumber, reqRowPerPage);
            }
        } catch (MalformedURLException e) {
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
    public StoreResultsResponse storeResults(String branchId, String patientId, String patientCode, String patientName, String remark, int equipmentId, int imageId, DateTime trxDate, DateTime timeStamp, String dataLocation, String xmlData, String creatorId, Attachment attachment) {

        StoreResultsResponse response = new StoreResultsResponse();
        response.setSuccess(false);
        try {
            //save to Database;
            Tmedequipment trx = transactionDAO.getNew();
            trx.setBranchCode(systemDAO.getByCode("CIS_CLINIC_ID").getSystemValue());
            //trx.setBranchCode(systemDAO.getByCode("CIS_CLINIC_ID").getSystemValue());
            trx.setPatientId(patientId);
            trx.setPatientCode(patientCode);
            trx.setLastName(patientName);
            trx.setRemark(remark);
            //trx.setMmedequipment(equipmentDAO.getById(equipmentId));
            trx.setMmedequipment(equipmentDAO.getByCode(String.valueOf(equipmentId)));
            trx.setImageId(imageId);
            trx.setTrxDate(trxDate.toDate());
            trx.setTimestamp(timeStamp.toDate());
            Blob blob = null;
            try {
                blob = new SerialBlob(xmlData.getBytes());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            trx.setDataOutput(blob);
            trx.setCreatorId(creatorId);
            Date now = new Date();
            trx.setCreateDate(now);
            trx.setLastUserId(creatorId);
            trx.setLastUpdate(now);
            SecUser user = userDAO.getUserByLoginname(creatorId);
            if(user!=null) trx.setSecUser(user);
            trx.setCisStatus("0");
            // save file to local server in public location
            String httpLocation = systemDAO.getByCode("HTTP_URL").getSystemValue();
            try {
                httpLocation += saveByteArrayToFile(attachment, dataLocation);
                trx.setDataLocation(httpLocation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            transactionDAO.saveOrUpdate(trx);
            response.setSuccess(true);
            response.setResult("Saved to Interface server\n");
            //notify cis;
            //CisService cs = new CisService(CIS_NAMESPACE_URI);
            CisService cs = new CisService(systemDAO.getByCode("CIS_NAMESPACE_URI").getSystemValue());
            response = cs.putPatientData(patientId, equipmentId, httpLocation, xmlData, trxDate);
            //response.setSuccess(true);
            //response.setResult(result);
            if(response.isSuccess()) {
                trx.setCisStatus("1");
            } else {
                trx.setCisStatus("0");
            }
            transactionDAO.saveOrUpdate(trx);
            return response;
        } catch (SOAPException e) {
            e.printStackTrace();
            response.setResult(response.getResult() + e.getMessage());
            //response.setSuccess(false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.setResult(response.getResult()+e.getMessage());
            //response.setSuccess(false);
        } catch (TransformerException e) {
            e.printStackTrace();
            response.setResult(response.getResult()+e.getMessage());
            //response.setSuccess(false);
        } catch (IOException e) {
            e.printStackTrace();
            response.setResult(response.getResult()+e.getMessage());
            //response.setSuccess(false);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            response.setResult(response.getResult()+e.getMessage());
            //response.setSuccess(false);
        }
        return response;
    }

    private String saveByteArrayToFile(Attachment attachment, String dataLocation) throws Exception {
        byte[] bytes = IOUtils.toByteArray(attachment.getInputStream());
        File tmp = File.createTempFile("TempAlat", ".dat");
        FileUtils.writeByteArrayToFile(tmp, bytes);

        String path = systemDAO.getByCode("IMAGE_DIR").getSystemValue();
        dataLocation = dataLocation.replaceFirst("C:/kmntmp", "");
        File dir = new File(dataLocation);
        String name = dataLocation.split("/")[1];
        if(dataLocation.contains(".xml")) name = "/"+name+".xml";
        if(dataLocation.contains(".pdf")) name = "/"+name+".pdf";
        if(dataLocation.contains(".jpg")) name = "/"+name+".jpg";

        //File file = new File(path+dataLocation);
        File file = new File(path+name);
        if(!file.exists()){
            file.getParentFile().mkdirs();
        }
        //FileUtils.writeByteArrayToFile(tmp, bytes);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bos.write(bytes);
        bos.flush();
        bos.close();
        //System.out.println("Wrote tmp file to: " + tmp.getAbsolutePath());
        System.out.println("Wrote real file to: " + file.getAbsolutePath());
        System.out.println("dataLocation: " + dataLocation);
        //return file.getAbsolutePath();
        //return dataLocation;
        return name;
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
