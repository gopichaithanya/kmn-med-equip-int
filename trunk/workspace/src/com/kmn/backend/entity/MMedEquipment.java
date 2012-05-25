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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "m_med_equipment", catalog = "kmn", schema = "")
@NamedQueries({
    @NamedQuery(name = "MMedEquipment.findAll", query = "SELECT m FROM MMedEquipment m")})
public class MMedEquipment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "EQUIPMENT_CODE", nullable = false, length = 5)
    private String equipmentCode;
    @Column(name = "BRANCH_ID", length = 20)
    private String branchId;
    @Basic(optional = false)
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;
    @Basic(optional = false)
    @Column(name = "DICOM_STS", nullable = false, length = 1)
    private String dicomSts;
    @Column(name = "RESULT_ID", length = 1)
    private String resultId;
    @Column(name = "INTERFACE_ID")
    private Integer interfaceId;
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
    @JoinColumn(name = "TYPE_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private MMedEquiptype typeId;
    @JoinColumn(name = "PROD_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private MMedProducer prodId;
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ID")
    @ManyToOne
    private MMedRoom roomId;

    public MMedEquipment() {
    }

    public MMedEquipment(Integer id) {
        this.id = id;
    }

    public MMedEquipment(Integer id, String equipmentCode, String name, String dicomSts) {
        this.id = id;
        this.equipmentCode = equipmentCode;
        this.name = name;
        this.dicomSts = dicomSts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
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

    public MMedEquiptype getTypeId() {
        return typeId;
    }

    public void setTypeId(MMedEquiptype typeId) {
        this.typeId = typeId;
    }

    public MMedProducer getProdId() {
        return prodId;
    }

    public void setProdId(MMedProducer prodId) {
        this.prodId = prodId;
    }

    public MMedRoom getRoomId() {
        return roomId;
    }

    public void setRoomId(MMedRoom roomId) {
        this.roomId = roomId;
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
        if (!(object instanceof MMedEquipment)) {
            return false;
        }
        MMedEquipment other = (MMedEquipment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.kmn.backend.entity.MMedEquipment[id=" + id + "]";
    }

}
