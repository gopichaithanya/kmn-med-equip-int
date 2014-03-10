package id.co.kmn.backend.model;

import java.util.Date;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 5:02 AM
 */
public class Mmedequiptype implements java.io.Serializable {
    private int id;
    private String typeCode;
    private String typeName;
    private String activeSts;
    private Date createDate;
    private String creatorId;
    private Date lastUpdate;
    private String lastUserId;

    public Mmedequiptype() {
    }


    public Mmedequiptype(int id, String typeCode, String typeName, String activeSts) {
        this.id = id;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.activeSts = activeSts;
    }
    public Mmedequiptype(int id, String typeCode, String typeName, String activeSts, Date createDate, String creatorId, Date lastUpdate, String lastUserId) {
       this.id = id;
       this.typeCode = typeCode;
       this.typeName = typeName;
       this.activeSts = activeSts;
       this.createDate = createDate;
       this.creatorId = creatorId;
       this.lastUpdate = lastUpdate;
       this.lastUserId = lastUserId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getActiveSts() {
        return this.activeSts;
    }

    public void setActiveSts(String activeSts) {
        this.activeSts = activeSts;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUserId() {
        return this.lastUserId;
    }

    public void setLastUserId(String lastUserId) {
        this.lastUserId = lastUserId;
    }
}
