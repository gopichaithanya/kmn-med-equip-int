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
@Table(name = "m_med_room", catalog = "kmn", schema = "")
@NamedQueries({
    @NamedQuery(name = "MMedRoom.findAll", query = "SELECT m FROM MMedRoom m")})
public class MMedRoom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ROOM_CODE", nullable = false, length = 5)
    private String roomCode;
    @Basic(optional = false)
    @Column(name = "ROOM_NAME", nullable = false, length = 20)
    private String roomName;
    @Column(name = "DESCRIPTION", length = 50)
    private String description;
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

    public MMedRoom() {
    }

    public MMedRoom(Integer id) {
        this.id = id;
    }

    public MMedRoom(Integer id, String roomCode, String roomName) {
        this.id = id;
        this.roomCode = roomCode;
        this.roomName = roomName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof MMedRoom)) {
            return false;
        }
        MMedRoom other = (MMedRoom) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.kmn.backend.entity.MMedRoom[id=" + id + "]";
    }

}
