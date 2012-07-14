package id.ac.idu.backend.model;
// Generated 11 Mar 12 23:21:10 by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.Date;

/**
 * Mhistpnddkmhs generated by hbm2java
 */
public class Mhistpnddkmhs  implements java.io.Serializable {


     private int id;
     private Mprodi mprodi;
     private int mahasiswaId;
     private Muniv muniv;
     private Mjenjang mjenjang;
     private BigDecimal nhistipk;
     private Date dtgllulus;
     private String cbebanstudi;
     private Date dcreateddate;
     private String ccreatedby;
     private Date dupdateddate;
     private String cupdatedby;

    public Mhistpnddkmhs() {
    }

	
    public Mhistpnddkmhs(int id, Mprodi mprodi, int mahasiswaId, Mjenjang mjenjang) {
        this.id = id;
        this.mprodi = mprodi;
        this.mahasiswaId = mahasiswaId;
        this.mjenjang = mjenjang;
    }
    public Mhistpnddkmhs(int id, Mprodi mprodi, int mahasiswaId, Muniv muniv, Mjenjang mjenjang, BigDecimal nhistipk, Date dtgllulus, String cbebanstudi, Date dcreateddate, String ccreatedby, Date dupdateddate, String cupdatedby) {
       this.id = id;
       this.mprodi = mprodi;
       this.mahasiswaId = mahasiswaId;
       this.muniv = muniv;
       this.mjenjang = mjenjang;
       this.nhistipk = nhistipk;
       this.dtgllulus = dtgllulus;
       this.cbebanstudi = cbebanstudi;
       this.dcreateddate = dcreateddate;
       this.ccreatedby = ccreatedby;
       this.dupdateddate = dupdateddate;
       this.cupdatedby = cupdatedby;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Mprodi getMprodi() {
        return this.mprodi;
    }
    
    public void setMprodi(Mprodi mprodi) {
        this.mprodi = mprodi;
    }

    public int getMahasiswaId() {
        return mahasiswaId;
    }

    public void setMahasiswaId(int mahasiswaId) {
        this.mahasiswaId = mahasiswaId;
    }

    public Muniv getMuniv() {
        return this.muniv;
    }
    
    public void setMuniv(Muniv muniv) {
        this.muniv = muniv;
    }
    public Mjenjang getMjenjang() {
        return this.mjenjang;
    }
    
    public void setMjenjang(Mjenjang mjenjang) {
        this.mjenjang = mjenjang;
    }
    public BigDecimal getNhistipk() {
        return this.nhistipk;
    }
    
    public void setNhistipk(BigDecimal nhistipk) {
        this.nhistipk = nhistipk;
    }
    public Date getDtgllulus() {
        return this.dtgllulus;
    }
    
    public void setDtgllulus(Date dtgllulus) {
        this.dtgllulus = dtgllulus;
    }
    public String getCbebanstudi() {
        return this.cbebanstudi;
    }
    
    public void setCbebanstudi(String cbebanstudi) {
        this.cbebanstudi = cbebanstudi;
    }
    public Date getDcreateddate() {
        return this.dcreateddate;
    }
    
    public void setDcreateddate(Date dcreateddate) {
        this.dcreateddate = dcreateddate;
    }
    public String getCcreatedby() {
        return this.ccreatedby;
    }
    
    public void setCcreatedby(String ccreatedby) {
        this.ccreatedby = ccreatedby;
    }
    public Date getDupdateddate() {
        return this.dupdateddate;
    }
    
    public void setDupdateddate(Date dupdateddate) {
        this.dupdateddate = dupdateddate;
    }
    public String getCupdatedby() {
        return this.cupdatedby;
    }
    
    public void setCupdatedby(String cupdatedby) {
        this.cupdatedby = cupdatedby;
    }
}

