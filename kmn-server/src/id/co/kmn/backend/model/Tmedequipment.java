package id.co.kmn.backend.model;

import java.sql.Blob;
import java.util.Date;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/31/12
 * Time: 11:38 PM
 */
public class Tmedequipment implements java.io.Serializable {
    private int id;
    private String branchCode;
    private String patientId;
    private String patientCode;
    private String firstName;
    private String lastName;
    private String remark;
    private Mmedequipment mmedequipment;
    private int imageId;
    private Date trxDate;
    private String cisStatus;
    private Date timestamp;
    private String dataLocation;
    private Blob dataOutput;
    private Date createDate;
    private String creatorId;
    private Date lastUpdate;
    private String lastUserId;
    private SecUser secUser;

    public Tmedequipment() {
    }


    public Tmedequipment(int id) {
        this.id = id;
    }
    public Tmedequipment( int id,
    String branchCode,
    String patientId,
    String patientCode,
    String firstName,
    String lastName,
    String remark,
    Mmedequipment mmedequipment,
    int imageId,
    Date trxDate,
    String cisStatus,
    Date timestamp,
    String dataLocation,
    Blob dataOutput,
    Date createDate,
    String creatorId,
    Date lastUpdate,
    String lastUserId,
    SecUser secUser) {
       this.id = id;
       this.branchCode = branchCode;
       this.patientId = patientId;
       this.patientCode = patientCode;
       this.firstName = firstName;
       this.lastName = lastName;
       this.remark = remark;
       this.mmedequipment = mmedequipment;
       this.imageId = imageId;
       this.trxDate = trxDate;
       this.cisStatus = cisStatus;
       this.timestamp = timestamp;
       this.dataLocation = dataLocation;
       this.dataOutput = dataOutput;
       this.createDate = createDate;
       this.creatorId = creatorId;
       this.lastUpdate = lastUpdate;
       this.lastUserId = lastUserId;
       this.secUser = secUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Mmedequipment getMmedequipment() {
        return mmedequipment;
    }

    public void setMmedequipment(Mmedequipment mmedequipment) {
        this.mmedequipment = mmedequipment;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Date getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(Date trxDate) {
        this.trxDate = trxDate;
    }

    public String getCisStatus() {
        return cisStatus;
    }

    public void setCisStatus(String cisStatus) {
        this.cisStatus = cisStatus;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDataLocation() {
        return dataLocation;
    }

    public void setDataLocation(String dataLocation) {
        this.dataLocation = dataLocation;
    }

    public Blob getDataOutput() {
        return dataOutput;
    }

    public void setDataOutput(Blob dataOutput) {
        this.dataOutput = dataOutput;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUserId() {
        return lastUserId;
    }

    public void setLastUserId(String lastUserId) {
        this.lastUserId = lastUserId;
    }

    public SecUser getSecUser() {
        return secUser;
    }

    public void setSecUser(SecUser secUser) {
        this.secUser = secUser;
    }
}
