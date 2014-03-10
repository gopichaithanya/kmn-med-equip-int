package id.co.kmn.backend.model;

import java.util.Date;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/25/12
 * Time: 4:26 AM
 */
public class Mmedequipment implements java.io.Serializable {
    private int id;
    private String equipmentCode;
    private String branchCode;
    private Mmedproducer mmedproducer;
    private Mmedequiptype mmedequiptype;
    private String name;
    private String dicomSts;
    private Mmedroom mmedroom;
    private String resultId;
    private Integer interfaceId;
    private Date createDate;
    private String creatorId;
    private Date lastUpdate;
    private String lastUserId;

    public Mmedequipment() {
    }


    public Mmedequipment(int id, String equipmentCode, String name) {
        this.id = id;
        this.equipmentCode = equipmentCode;
        this.name = name;
    }
    public Mmedequipment(int id, String equipmentCode, String branchCode, Mmedproducer mmedproducer, Mmedequiptype mmedequiptype,
        String name, String dicomSts, Mmedroom mmedroom, String resultId, Integer interfaceId, Date createDate,
        String creatorId, Date lastUpdate, String lastUserId) {
       this.id = id;
       this.equipmentCode = equipmentCode;
       this.branchCode = branchCode;
       this.mmedproducer = mmedproducer;
       this.mmedequiptype = mmedequiptype;
       this.name = name;
       this.dicomSts = dicomSts;
       this.mmedroom = mmedroom;
       this.resultId = resultId;
       this.interfaceId = interfaceId;
       this.createDate = createDate;
       this.creatorId = creatorId;
       this.lastUpdate = lastUpdate;
       this.lastUserId = lastUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchId) {
        this.branchCode = branchId;
    }

    public Mmedproducer getMmedproducer() {
        return mmedproducer;
    }

    public void setMmedproducer(Mmedproducer mmedproducer) {
        this.mmedproducer = mmedproducer;
    }

    public Mmedequiptype getMmedequiptype() {
        return mmedequiptype;
    }

    public void setMmedequiptype(Mmedequiptype mmedequiptype) {
        this.mmedequiptype = mmedequiptype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDicomSts() {
        return dicomSts;
    }

    public void setDicomSts(String dicomSts) {
        this.dicomSts = dicomSts;
    }

    public Mmedroom getMmedroom() {
        return mmedroom;
    }

    public void setMmedroom(Mmedroom mmedroom) {
        this.mmedroom = mmedroom;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public Integer getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
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
}
