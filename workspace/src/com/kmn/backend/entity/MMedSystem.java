/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.backend.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Hermanto
 */
@Entity
@Table(name = "m_med_system", catalog = "kmn", schema = "")
@NamedQueries({
    @NamedQuery(name = "MMedSystem.findAll", query = "SELECT m FROM MMedSystem m")})
public class MMedSystem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "SYSTEM_KEY", nullable = false, length = 20)
    private String systemKey;
    @Basic(optional = false)
    @Column(name = "SYSTEM_VALUE", nullable = false, length = 50)
    private String systemValue;
    @Column(name = "ACTIVE_STS", length = 1)
    private String activeSts;
    @Column(name = "CREATE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "CREATOR_ID", length = 20)
    private String creatorId;
    @Column(name = "LAST_UPDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "LAST_USER_ID", length = 20)
    private String lastUserId;

    public MMedSystem() {
    }

    public MMedSystem(Integer id) {
        this.id = id;
    }

    public MMedSystem(Integer id, String systemKey, String systemValue) {
        this.id = id;
        this.systemKey = systemKey;
        this.systemValue = systemValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MMedSystem)) {
            return false;
        }
        MMedSystem other = (MMedSystem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.kmn.backend.entity.MMedSystem[id=" + id + "]";
    }

}
