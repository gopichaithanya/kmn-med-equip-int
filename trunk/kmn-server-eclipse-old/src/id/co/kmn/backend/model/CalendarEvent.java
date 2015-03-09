package id.co.kmn.backend.model;
// Generated 06 Mar 12 10:03:27 by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * CalendarEvent generated by hbm2java
 */
public class CalendarEvent implements java.io.Serializable {


    private int cleId;
    private int version;
    private String cleTitle;
    private String cleContent;
    private Date cleBeginDate;
    private Date cleEndDate;
    private String cleHeaderColor;
    private String cleContentColor;
    private int cleUsrId;
    private Short cleLocked;

    public CalendarEvent() {
    }


    public CalendarEvent(int cleId, String cleContent, Date cleBeginDate, Date cleEndDate, int cleUsrId) {
        this.cleId = cleId;
        this.cleContent = cleContent;
        this.cleBeginDate = cleBeginDate;
        this.cleEndDate = cleEndDate;
        this.cleUsrId = cleUsrId;
    }

    public CalendarEvent(int cleId, String cleTitle, String cleContent, Date cleBeginDate, Date cleEndDate, String cleHeaderColor, String cleContentColor, int cleUsrId, Short cleLocked) {
        this.cleId = cleId;
        this.cleTitle = cleTitle;
        this.cleContent = cleContent;
        this.cleBeginDate = cleBeginDate;
        this.cleEndDate = cleEndDate;
        this.cleHeaderColor = cleHeaderColor;
        this.cleContentColor = cleContentColor;
        this.cleUsrId = cleUsrId;
        this.cleLocked = cleLocked;
    }

    public int getCleId() {
        return this.cleId;
    }

    public void setCleId(int cleId) {
        this.cleId = cleId;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCleTitle() {
        return this.cleTitle;
    }

    public void setCleTitle(String cleTitle) {
        this.cleTitle = cleTitle;
    }

    public String getCleContent() {
        return this.cleContent;
    }

    public void setCleContent(String cleContent) {
        this.cleContent = cleContent;
    }

    public Date getCleBeginDate() {
        return this.cleBeginDate;
    }

    public void setCleBeginDate(Date cleBeginDate) {
        this.cleBeginDate = cleBeginDate;
    }

    public Date getCleEndDate() {
        return this.cleEndDate;
    }

    public void setCleEndDate(Date cleEndDate) {
        this.cleEndDate = cleEndDate;
    }

    public String getCleHeaderColor() {
        return this.cleHeaderColor;
    }

    public void setCleHeaderColor(String cleHeaderColor) {
        this.cleHeaderColor = cleHeaderColor;
    }

    public String getCleContentColor() {
        return this.cleContentColor;
    }

    public void setCleContentColor(String cleContentColor) {
        this.cleContentColor = cleContentColor;
    }

    public int getCleUsrId() {
        return this.cleUsrId;
    }

    public void setCleUsrId(int cleUsrId) {
        this.cleUsrId = cleUsrId;
    }

    public Short getCleLocked() {
        return this.cleLocked;
    }

    public void setCleLocked(Short cleLocked) {
        this.cleLocked = cleLocked;
    }


}

