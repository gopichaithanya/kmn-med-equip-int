package id.ac.idu.backend.model;
// Generated 11 Mar 12 23:21:10 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Mkec generated by hbm2java
 */
public class Mkec  implements java.io.Serializable {


     private int id;
     private String cnamaKec;
     private String kabId;
     private Date dcreateddate;
     private String ccreatedby;
     private Date dupdateddate;
     private String cupdatedby;
     private Set<Malumni> malumnis = new HashSet<Malumni>(0);

    public Mkec() {
    }

	
    public Mkec(int id, String cnamaKec, String kabId) {
        this.id = id;
        this.cnamaKec = cnamaKec;
        this.kabId = kabId;
    }
    public Mkec(int id, String cnamaKec, String kabId, Date dcreateddate, String ccreatedby, Date dupdateddate, String cupdatedby, Set<Malumni> malumnis) {
       this.id = id;
       this.cnamaKec = cnamaKec;
       this.kabId = kabId;
       this.dcreateddate = dcreateddate;
       this.ccreatedby = ccreatedby;
       this.dupdateddate = dupdateddate;
       this.cupdatedby = cupdatedby;
       this.malumnis = malumnis;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getCnamaKec() {
        return this.cnamaKec;
    }
    
    public void setCnamaKec(String cnamaKec) {
        this.cnamaKec = cnamaKec;
    }
    public String getKabId() {
        return this.kabId;
    }
    
    public void setKabId(String kabId) {
        this.kabId = kabId;
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
    public Set<Malumni> getMalumnis() {
        return this.malumnis;
    }
    
    public void setMalumnis(Set<Malumni> malumnis) {
        this.malumnis = malumnis;
    }




}

