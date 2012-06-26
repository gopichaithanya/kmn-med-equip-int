package id.ac.idu.backend.model;
// Generated 11 Mar 12 23:21:10 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Mprodi generated by hbm2java
 */
public class Mprodi  implements java.io.Serializable {


     private int id;
     private Msekolah msekolah;
     private Mjenjang mjenjang;
     private String ckdprogst;
     private String cnmprogst;
     private String csingkat;
     private String cstatus;
     private String cnmproging;
     private String cnip;
     private String cnipsekprodi;
     private Date dcreateddate;
     private String ccreatedby;
     private Date dupdateddate;
     private String cupdatedby;
     private Set<Mhistpnddkmhs> mhistpnddkmhses = new HashSet<Mhistpnddkmhs>(0);
     private Set<Malumni> malumnis = new HashSet<Malumni>(0);
     private Set<Tjadkulmaster> tjadkulmasters = new HashSet<Tjadkulmaster>(0);
     private Set<Mcalakademik> mcalakademiks = new HashSet<Mcalakademik>(0);
     private Set<Mdetilkurikulum> mdetilkurikulums = new HashSet<Mdetilkurikulum>(0);
     private Set<Mkurmhs> mkurmhses = new HashSet<Mkurmhs>(0);
     private Set<Tjadujian> tjadujians = new HashSet<Tjadujian>(0);
     private Set<Tlognilai> tlognilais = new HashSet<Tlognilai>(0);
     private Set<Tpaketkuliah> tpaketkuliahs = new HashSet<Tpaketkuliah>(0);
     private Set<Mkurikulum> mkurikulums = new HashSet<Mkurikulum>(0);
     private Set<Mpeminatan> mpeminatans = new HashSet<Mpeminatan>(0);
     private Set<Mgrade> mgrades = new HashSet<Mgrade>(0);
     private Set<Tjadkuldetil> tjadkuldetils = new HashSet<Tjadkuldetil>(0);
     private Set<Tabsenmhs> tabsenmhses = new HashSet<Tabsenmhs>(0);
     private Set<Thistkerja> thistkerjas = new HashSet<Thistkerja>(0);
     private Set<Tabsendosen> tabsendosens = new HashSet<Tabsendosen>(0);
     private Set<Tirspasca> tirspascas = new HashSet<Tirspasca>(0);
     private Set<Mmahasiswa> mmahasiswas = new HashSet<Mmahasiswa>(0);

    public Mprodi() {
    }

	
    public Mprodi(int id, Mjenjang mjenjang, String ckdprogst, String cnmprogst) {
        this.id = id;
        this.mjenjang = mjenjang;
        this.ckdprogst = ckdprogst;
        this.cnmprogst = cnmprogst;
    }
    public Mprodi(int id, Msekolah msekolah, Mjenjang mjenjang, String ckdprogst, String cnmprogst, String csingkat, String cstatus, String cnmproging, String cnip, String cnipsekprodi, Date dcreateddate, String ccreatedby, Date dupdateddate, String cupdatedby, Set<Mhistpnddkmhs> mhistpnddkmhses, Set<Malumni> malumnis, Set<Tjadkulmaster> tjadkulmasters, Set<Mcalakademik> mcalakademiks, Set<Mdetilkurikulum> mdetilkurikulums, Set<Mkurmhs> mkurmhses, Set<Tjadujian> tjadujians, Set<Tlognilai> tlognilais, Set<Tpaketkuliah> tpaketkuliahs, Set<Mkurikulum> mkurikulums, Set<Mpeminatan> mpeminatans, Set<Mgrade> mgrades, Set<Tjadkuldetil> tjadkuldetils, Set<Tabsenmhs> tabsenmhses, Set<Thistkerja> thistkerjas, Set<Tabsendosen> tabsendosens, Set<Tirspasca> tirspascas, Set<Mmahasiswa> mmahasiswas) {
       this.id = id;
       this.msekolah = msekolah;
       this.mjenjang = mjenjang;
       this.ckdprogst = ckdprogst;
       this.cnmprogst = cnmprogst;
       this.csingkat = csingkat;
       this.cstatus = cstatus;
       this.cnmproging = cnmproging;
       this.cnip = cnip;
       this.cnipsekprodi = cnipsekprodi;
       this.dcreateddate = dcreateddate;
       this.ccreatedby = ccreatedby;
       this.dupdateddate = dupdateddate;
       this.cupdatedby = cupdatedby;
       this.mhistpnddkmhses = mhistpnddkmhses;
       this.malumnis = malumnis;
       this.tjadkulmasters = tjadkulmasters;
       this.mcalakademiks = mcalakademiks;
       this.mdetilkurikulums = mdetilkurikulums;
       this.mkurmhses = mkurmhses;
       this.tjadujians = tjadujians;
       this.tlognilais = tlognilais;
       this.tpaketkuliahs = tpaketkuliahs;
       this.mkurikulums = mkurikulums;
       this.mpeminatans = mpeminatans;
       this.mgrades = mgrades;
       this.tjadkuldetils = tjadkuldetils;
       this.tabsenmhses = tabsenmhses;
       this.thistkerjas = thistkerjas;
       this.tabsendosens = tabsendosens;
       this.tirspascas = tirspascas;
       this.mmahasiswas = mmahasiswas;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Msekolah getMsekolah() {
        return this.msekolah;
    }
    
    public void setMsekolah(Msekolah msekolah) {
        this.msekolah = msekolah;
    }
    public Mjenjang getMjenjang() {
        return this.mjenjang;
    }
    
    public void setMjenjang(Mjenjang mjenjang) {
        this.mjenjang = mjenjang;
    }
    public String getCkdprogst() {
        return this.ckdprogst;
    }
    
    public void setCkdprogst(String ckdprogst) {
        this.ckdprogst = ckdprogst;
    }
    public String getCnmprogst() {
        return this.cnmprogst;
    }
    
    public void setCnmprogst(String cnmprogst) {
        this.cnmprogst = cnmprogst;
    }
    public String getCsingkat() {
        return this.csingkat;
    }
    
    public void setCsingkat(String csingkat) {
        this.csingkat = csingkat;
    }
    public String getCstatus() {
        return this.cstatus;
    }
    
    public void setCstatus(String cstatus) {
        this.cstatus = cstatus;
    }
    public String getCnmproging() {
        return this.cnmproging;
    }
    
    public void setCnmproging(String cnmproging) {
        this.cnmproging = cnmproging;
    }
    public String getCnip() {
        return this.cnip;
    }
    
    public void setCnip(String cnip) {
        this.cnip = cnip;
    }
    public String getCnipsekprodi() {
        return this.cnipsekprodi;
    }
    
    public void setCnipsekprodi(String cnipsekprodi) {
        this.cnipsekprodi = cnipsekprodi;
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
    public Set<Mhistpnddkmhs> getMhistpnddkmhses() {
        return this.mhistpnddkmhses;
    }
    
    public void setMhistpnddkmhses(Set<Mhistpnddkmhs> mhistpnddkmhses) {
        this.mhistpnddkmhses = mhistpnddkmhses;
    }
    public Set<Malumni> getMalumnis() {
        return this.malumnis;
    }
    
    public void setMalumnis(Set<Malumni> malumnis) {
        this.malumnis = malumnis;
    }
    public Set<Tjadkulmaster> getTjadkulmasters() {
        return this.tjadkulmasters;
    }
    
    public void setTjadkulmasters(Set<Tjadkulmaster> tjadkulmasters) {
        this.tjadkulmasters = tjadkulmasters;
    }
    public Set<Mcalakademik> getMcalakademiks() {
        return this.mcalakademiks;
    }
    
    public void setMcalakademiks(Set<Mcalakademik> mcalakademiks) {
        this.mcalakademiks = mcalakademiks;
    }
    public Set<Mdetilkurikulum> getMdetilkurikulums() {
        return this.mdetilkurikulums;
    }
    
    public void setMdetilkurikulums(Set<Mdetilkurikulum> mdetilkurikulums) {
        this.mdetilkurikulums = mdetilkurikulums;
    }
    public Set<Mkurmhs> getMkurmhses() {
        return this.mkurmhses;
    }
    
    public void setMkurmhses(Set<Mkurmhs> mkurmhses) {
        this.mkurmhses = mkurmhses;
    }
    public Set<Tjadujian> getTjadujians() {
        return this.tjadujians;
    }
    
    public void setTjadujians(Set<Tjadujian> tjadujians) {
        this.tjadujians = tjadujians;
    }
    public Set<Tlognilai> getTlognilais() {
        return this.tlognilais;
    }
    
    public void setTlognilais(Set<Tlognilai> tlognilais) {
        this.tlognilais = tlognilais;
    }
    public Set<Tpaketkuliah> getTpaketkuliahs() {
        return this.tpaketkuliahs;
    }
    
    public void setTpaketkuliahs(Set<Tpaketkuliah> tpaketkuliahs) {
        this.tpaketkuliahs = tpaketkuliahs;
    }
    public Set<Mkurikulum> getMkurikulums() {
        return this.mkurikulums;
    }
    
    public void setMkurikulums(Set<Mkurikulum> mkurikulums) {
        this.mkurikulums = mkurikulums;
    }
    public Set<Mpeminatan> getMpeminatans() {
        return this.mpeminatans;
    }
    
    public void setMpeminatans(Set<Mpeminatan> mpeminatans) {
        this.mpeminatans = mpeminatans;
    }
    public Set<Mgrade> getMgrades() {
        return this.mgrades;
    }
    
    public void setMgrades(Set<Mgrade> mgrades) {
        this.mgrades = mgrades;
    }
    public Set<Tjadkuldetil> getTjadkuldetils() {
        return this.tjadkuldetils;
    }
    
    public void setTjadkuldetils(Set<Tjadkuldetil> tjadkuldetils) {
        this.tjadkuldetils = tjadkuldetils;
    }
    public Set<Tabsenmhs> getTabsenmhses() {
        return this.tabsenmhses;
    }
    
    public void setTabsenmhses(Set<Tabsenmhs> tabsenmhses) {
        this.tabsenmhses = tabsenmhses;
    }
    public Set<Thistkerja> getThistkerjas() {
        return this.thistkerjas;
    }
    
    public void setThistkerjas(Set<Thistkerja> thistkerjas) {
        this.thistkerjas = thistkerjas;
    }
    public Set<Tabsendosen> getTabsendosens() {
        return this.tabsendosens;
    }
    
    public void setTabsendosens(Set<Tabsendosen> tabsendosens) {
        this.tabsendosens = tabsendosens;
    }
    public Set<Tirspasca> getTirspascas() {
        return this.tirspascas;
    }
    
    public void setTirspascas(Set<Tirspasca> tirspascas) {
        this.tirspascas = tirspascas;
    }
    public Set<Mmahasiswa> getMmahasiswas() {
        return this.mmahasiswas;
    }
    
    public void setMmahasiswas(Set<Mmahasiswa> mmahasiswas) {
        this.mmahasiswas = mmahasiswas;
    }




}

