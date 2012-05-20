/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.backend.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Hermanto
 */
@Entity
@Table(name = "sec_user", catalog = "kmn", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"USR_ID"}),
    @UniqueConstraint(columnNames = {"USR_LOGINNAME"})
})
@NamedQueries({@NamedQuery(name = "SecUser.findAll", query = "SELECT s FROM SecUser s")})
public class SecUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USR_ID", nullable = false)
    private Integer usrId;

    @Basic(optional = false)
    @Column(name = "USR_LOGINNAME", nullable = false, length = 50)
    private String usrLoginname;

    @Basic(optional = false)
    @Column(name = "USR_PASSWORD", nullable = false, length = 50)
    private String usrPassword;

    @Column(name = "USR_LASTNAME", length = 50)
    private String usrLastname;

    @Column(name = "USR_FIRSTNAME", length = 50)
    private String usrFirstname;

    @Column(name = "USR_EMAIL", length = 200)
    private String usrEmail;

    @Column(name = "USR_LOCALE", length = 5)
    private String usrLocale;

    @Basic(optional = false)
    @Column(name = "USR_ENABLED", nullable = false)
    private short usrEnabled;

    @Basic(optional = false)
    @Column(name = "USR_ACCOUNTNONEXPIRED", nullable = false)
    private short usrAccountnonexpired;

    @Basic(optional = false)
    @Column(name = "USR_CREDENTIALSNONEXPIRED", nullable = false)
    private short usrCredentialsnonexpired;

    @Basic(optional = false)
    @Column(name = "USR_ACCOUNTNONLOCKED", nullable = false)
    private short usrAccountnonlocked;

    @Column(name = "USR_TOKEN", length = 20)
    private String usrToken;

    @Basic(optional = false)
    @Column(name = "VERSION", nullable = false)
    private int version;

    public SecUser() {
    }

    public SecUser(Integer usrId) {
        this.usrId = usrId;
    }

    public SecUser(Integer usrId, String usrLoginname, String usrPassword, short usrEnabled, short usrAccountnonexpired, short usrCredentialsnonexpired, short usrAccountnonlocked, int version) {
        this.usrId = usrId;
        this.usrLoginname = usrLoginname;
        this.usrPassword = usrPassword;
        this.usrEnabled = usrEnabled;
        this.usrAccountnonexpired = usrAccountnonexpired;
        this.usrCredentialsnonexpired = usrCredentialsnonexpired;
        this.usrAccountnonlocked = usrAccountnonlocked;
        this.version = version;
    }

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public String getUsrLoginname() {
        return usrLoginname;
    }

    public void setUsrLoginname(String usrLoginname) {
        this.usrLoginname = usrLoginname;
    }

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    public String getUsrLastname() {
        return usrLastname;
    }

    public void setUsrLastname(String usrLastname) {
        this.usrLastname = usrLastname;
    }

    public String getUsrFirstname() {
        return usrFirstname;
    }

    public void setUsrFirstname(String usrFirstname) {
        this.usrFirstname = usrFirstname;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public String getUsrLocale() {
        return usrLocale;
    }

    public void setUsrLocale(String usrLocale) {
        this.usrLocale = usrLocale;
    }

    public short getUsrEnabled() {
        return usrEnabled;
    }

    public void setUsrEnabled(short usrEnabled) {
        this.usrEnabled = usrEnabled;
    }

    public short getUsrAccountnonexpired() {
        return usrAccountnonexpired;
    }

    public void setUsrAccountnonexpired(short usrAccountnonexpired) {
        this.usrAccountnonexpired = usrAccountnonexpired;
    }

    public short getUsrCredentialsnonexpired() {
        return usrCredentialsnonexpired;
    }

    public void setUsrCredentialsnonexpired(short usrCredentialsnonexpired) {
        this.usrCredentialsnonexpired = usrCredentialsnonexpired;
    }

    public short getUsrAccountnonlocked() {
        return usrAccountnonlocked;
    }

    public void setUsrAccountnonlocked(short usrAccountnonlocked) {
        this.usrAccountnonlocked = usrAccountnonlocked;
    }

    public String getUsrToken() {
        return usrToken;
    }

    public void setUsrToken(String usrToken) {
        this.usrToken = usrToken;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrId != null ? usrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecUser)) {
            return false;
        }
        SecUser other = (SecUser) object;
        if ((this.usrId == null && other.usrId != null) || (this.usrId != null && !this.usrId.equals(other.usrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.kmn.backend.entity.SecUser[usrId=" + usrId + "]";
    }

}
