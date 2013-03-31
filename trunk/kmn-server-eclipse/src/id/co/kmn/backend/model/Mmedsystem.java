package id.co.kmn.backend.model;

import java.util.Date;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 6:59 AM
 */
public class Mmedsystem implements java.io.Serializable {
    private int id;
    private String systemKey;
    private String systemValue;
    private String activeSts;
    private Date createDate;
    private String creatorId;
    private Date lastUpdate;
    private String lastUserId;

    public Mmedsystem() {
    }

    public Mmedsystem(int id, String systemKey, String systemValue, String activeSts) {
        this.id = id;
        this.systemKey = systemKey;
        this.systemValue = systemValue;
        this.activeSts = activeSts;
    }

    public Mmedsystem(int id, String systemKey, String systemValue, String activeSts, Date createDate, String creatorId, Date lastUpdate, String lastUserId) {
       this.id = id;
       this.systemKey = systemKey;
       this.systemValue = systemValue;
       this.activeSts = activeSts;
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

    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    public String getSystemValue() {
        return systemValue;
    }

    public void setSystemValue(String systemValue) {
        this.systemValue = systemValue;
    }

    public String getActiveSts() {
        return activeSts;
    }

    public void setActiveSts(String activeSts) {
        this.activeSts = activeSts;
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
