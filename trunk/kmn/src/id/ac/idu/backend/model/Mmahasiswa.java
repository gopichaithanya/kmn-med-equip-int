package id.ac.idu.backend.model;
// Generated 11 Mar 12 23:21:10 by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Mmahasiswa generated by hbm2java
 */
public class Mmahasiswa implements java.io.Serializable {


    private int id;
    private Mprodi mprodi;
    private Mpeminatan mpeminatan;
    private Mjenjang mjenjang;
    private String cnim;
    private String cnama;
    private String noktp;
    private String cjnsmhs;
    private String calamat;
    private String cnormh;
    private String crt;
    private String crw;
    private String cnotelp;
    private MkodePos kodeposId;
    private String ctemplhr;
    private Date dtglhr;
    private Character cjenkel;
    private Character cgoldar;
    private String ckdagama;
    private Character cstatnkh;
    private Character cwarga;
    private String cnosttb;
    private Date dtglsttb;
    private Date dtglmasuk;
    private Date dtglwisuda;
    private Date dtglyudisi;
    private Date dtglteori;
    private Character cflagspbm;
    private Character cflagnilai;
    private String cemail;
    private String cnohp;
    private String cnmkntor;
    private String calmkntor;
    private String ctelpkntor;
    private String cfaxkantor;
    private MkodePos ckdposkntor;
    private String calamatsrt;
    private MkodePos kodeposSrtId;
    private Mstatusmhs mstatusmhs;
    private String cthnlaporan;
    private String cketkerja;
    private String ctujuan;
    private String cgelombang;
    private String cstatawal;
    private Date dcreateddate;
    private String ccreatedby;
    private Date dupdateddate;
    private String cupdatedby;
    private Mmhspascakhs mmhspascakhs;
    private Set<Mppumhskhusus> mppumhskhususes = new HashSet<Mppumhskhusus>(0);
    private Set<Mhistpangkatmhs> mhistpangkatmhses = new HashSet<Mhistpangkatmhs>(0);
    private Set<Tabsenmhs> tabsenmhses = new HashSet<Tabsenmhs>(0);
    private Set<Mkgtmhs> mkgtmhses = new HashSet<Mkgtmhs>(0);
    private Set<Mkaryamhs> mkaryamhses = new HashSet<Mkaryamhs>(0);
    private Set<Mpbahasamhs> mpbahasamhses = new HashSet<Mpbahasamhs>(0);
    private Set<Tlognilai> tlognilais = new HashSet<Tlognilai>(0);
    private Set<Malumni> malumnis = new HashSet<Malumni>(0);
    private Set<Mkurmhs> mkurmhses = new HashSet<Mkurmhs>(0);
    private Set<Mhistkursusmhs> mhistkursusmhses = new HashSet<Mhistkursusmhs>(0);
    private Set<Tirspasca> tirspascas = new HashSet<Tirspasca>(0);
    private Set<Mhistpnddkmhs> mhistpnddkmhses = new HashSet<Mhistpnddkmhs>(0);
    private Set<Mprestasimhs> mprestasimhses = new HashSet<Mprestasimhs>(0);
    private Set<Tcutimhs> tcutimhses = new HashSet<Tcutimhs>(0);
    private Set<Tfeedbackalumni> tfeedbackalumnis = new HashSet<Tfeedbackalumni>(0);

    public Mmahasiswa() {
    }


    public Mmahasiswa(int id, String cnim) {
        this.id = id;
        this.cnim = cnim;
    }

    public Mmahasiswa(int id, Mprodi mprodi, Mpeminatan mpeminatan, Mjenjang mjenjang, String cnim, String cnama
            , String noktp, String cjnsmhs, String calamat, String cnormh, String crt, String crw, String cnotelp
            , MkodePos kodeposId, String ctemplhr, Date dtglhr, Character cjenkel, Character cgoldar, String ckdagama
            , Character cstatnkh, Character cwarga, String cnosttb, Date dtglsttb, Date dtglmasuk, Date dtglwisuda
            , Date dtglyudisi, Date dtglteori, Character cflagspbm, Character cflagnilai, String cemail, String cnohp
            , String cnmkntor, String calmkntor, String ctelpkntor, String cfaxkantor, MkodePos ckdposkntor
            , String calamatsrt, MkodePos kodeposSrtId, Mstatusmhs mstatusmhs, String cthnlaporan, String cketkerja
            , String ctujuan, String cgelombang, String cstatawal, Date dcreateddate, String ccreatedby
            , Date dupdateddate, String cupdatedby, Mmhspascakhs mmhspascakhs, Set<Tabsenmhs> tabsenmhses, Set<Mkgtmhs> mkgtmhses
            , Set<Mkaryamhs> mkaryamhses, Set<Mhistpangkatmhs> mhistpangkatmhses, Set<Mpbahasamhs> mpbahasamhses, Set<Tlognilai> tlognilais
            , Set<Malumni> malumnis, Set<Mkurmhs> mkurmhses , Set<Mppumhskhusus> mppumhskhususes
            , Set<Mhistkursusmhs> mhistkursusmhses, Set<Tirspasca> tirspascas , Set<Mhistpnddkmhs> mhistpnddkmhses
            , Set<Mprestasimhs> mprestasimhses, Set<Tcutimhs> tcutimhses) {
        this.id = id;
        this.mprodi = mprodi;
        this.mpeminatan = mpeminatan;
        this.mjenjang = mjenjang;
        this.cnim = cnim;
        this.cnama = cnama;
        this.noktp = noktp;
        this.cjnsmhs = cjnsmhs;
        this.calamat = calamat;
        this.cnormh = cnormh;
        this.crt = crt;
        this.crw = crw;
        this.cnotelp = cnotelp;
        this.kodeposId = kodeposId;
        this.ctemplhr = ctemplhr;
        this.dtglhr = dtglhr;
        this.cjenkel = cjenkel;
        this.cgoldar = cgoldar;
        this.ckdagama = ckdagama;
        this.cstatnkh = cstatnkh;
        this.cwarga = cwarga;
        this.cnosttb = cnosttb;
        this.dtglsttb = dtglsttb;
        this.dtglmasuk = dtglmasuk;
        this.dtglwisuda = dtglwisuda;
        this.dtglyudisi = dtglyudisi;
        this.dtglteori = dtglteori;
        this.cflagspbm = cflagspbm;
        this.cflagnilai = cflagnilai;
        this.cemail = cemail;
        this.cnohp = cnohp;
        this.cnmkntor = cnmkntor;
        this.calmkntor = calmkntor;
        this.ctelpkntor = ctelpkntor;
        this.cfaxkantor = cfaxkantor;
        this.ckdposkntor = ckdposkntor;
        this.calamatsrt = calamatsrt;
        this.kodeposSrtId = kodeposSrtId;
        this.mstatusmhs = mstatusmhs;
        this.cthnlaporan = cthnlaporan;
        this.cketkerja = cketkerja;
        this.ctujuan = ctujuan;
        this.cgelombang = cgelombang;
        this.cstatawal = cstatawal;
        this.dcreateddate = dcreateddate;
        this.ccreatedby = ccreatedby;
        this.dupdateddate = dupdateddate;
        this.cupdatedby = cupdatedby;
        this.mmhspascakhs = mmhspascakhs;
        this.tabsenmhses = tabsenmhses;
        this.mkgtmhses = mkgtmhses;
        this.mkaryamhses = mkaryamhses;
        this.mhistpangkatmhses = mhistpangkatmhses;
        this.mpbahasamhses = mpbahasamhses;
        this.tlognilais = tlognilais;
        this.malumnis = malumnis;
        this.mkurmhses = mkurmhses;
        this.mppumhskhususes = mppumhskhususes;
        this.mhistkursusmhses = mhistkursusmhses;
        this.tirspascas = tirspascas;
        this.mhistpnddkmhses = mhistpnddkmhses;
        this.mprestasimhses = mprestasimhses;
        this.tcutimhses = tcutimhses;
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

    public Mpeminatan getMpeminatan() {
        return this.mpeminatan;
    }

    public void setMpeminatan(Mpeminatan mpeminatan) {
        this.mpeminatan = mpeminatan;
    }

    public Mjenjang getMjenjang() {
        return this.mjenjang;
    }

    public void setMjenjang(Mjenjang mjenjang) {
        this.mjenjang = mjenjang;
    }

    public String getCnim() {
        return this.cnim;
    }

    public void setCnim(String cnim) {
        this.cnim = cnim;
    }

    public String getCnama() {
        return this.cnama;
    }

    public void setCnama(String cnama) {
        this.cnama = cnama;
    }

    public String getNoktp() {
        return this.noktp;
    }

    public void setNoktp(String noktp) {
        this.noktp = noktp;
    }

    public String getCjnsmhs() {
        return this.cjnsmhs;
    }

    public void setCjnsmhs(String cjnsmhs) {
        this.cjnsmhs = cjnsmhs;
    }

    public String getCalamat() {
        return this.calamat;
    }

    public void setCalamat(String calamat) {
        this.calamat = calamat;
    }

    public String getCnormh() {
        return this.cnormh;
    }

    public void setCnormh(String cnormh) {
        this.cnormh = cnormh;
    }

    public String getCrt() {
        return this.crt;
    }

    public void setCrt(String crt) {
        this.crt = crt;
    }

    public String getCrw() {
        return this.crw;
    }

    public void setCrw(String crw) {
        this.crw = crw;
    }

    public String getCnotelp() {
        return this.cnotelp;
    }

    public void setCnotelp(String cnotelp) {
        this.cnotelp = cnotelp;
    }

    public MkodePos getKodeposId() {
        return kodeposId;
    }

    public void setKodeposId(MkodePos kodeposId) {
        this.kodeposId = kodeposId;
    }

    public String getCtemplhr() {
        return this.ctemplhr;
    }

    public void setCtemplhr(String ctemplhr) {
        this.ctemplhr = ctemplhr;
    }

    public Date getDtglhr() {
        return this.dtglhr;
    }

    public void setDtglhr(Date dtglhr) {
        this.dtglhr = dtglhr;
    }

    public Character getCjenkel() {
        return this.cjenkel;
    }

    public void setCjenkel(Character cjenkel) {
        this.cjenkel = cjenkel;
    }

    public Character getCgoldar() {
        return this.cgoldar;
    }

    public void setCgoldar(Character cgoldar) {
        this.cgoldar = cgoldar;
    }

    public String getCkdagama() {
        return this.ckdagama;
    }

    public void setCkdagama(String ckdagama) {
        this.ckdagama = ckdagama;
    }

    public Character getCstatnkh() {
        return this.cstatnkh;
    }

    public void setCstatnkh(Character cstatnkh) {
        this.cstatnkh = cstatnkh;
    }

    public Character getCwarga() {
        return this.cwarga;
    }

    public void setCwarga(Character cwarga) {
        this.cwarga = cwarga;
    }

    public String getCnosttb() {
        return this.cnosttb;
    }

    public void setCnosttb(String cnosttb) {
        this.cnosttb = cnosttb;
    }

    public Date getDtglsttb() {
        return this.dtglsttb;
    }

    public void setDtglsttb(Date dtglsttb) {
        this.dtglsttb = dtglsttb;
    }

    public Date getDtglmasuk() {
        return this.dtglmasuk;
    }

    public void setDtglmasuk(Date dtglmasuk) {
        this.dtglmasuk = dtglmasuk;
    }

    public Date getDtglwisuda() {
        return this.dtglwisuda;
    }

    public void setDtglwisuda(Date dtglwisuda) {
        this.dtglwisuda = dtglwisuda;
    }

    public Date getDtglyudisi() {
        return this.dtglyudisi;
    }

    public void setDtglyudisi(Date dtglyudisi) {
        this.dtglyudisi = dtglyudisi;
    }

    public Date getDtglteori() {
        return this.dtglteori;
    }

    public void setDtglteori(Date dtglteori) {
        this.dtglteori = dtglteori;
    }

    public Character getCflagspbm() {
        return this.cflagspbm;
    }

    public void setCflagspbm(Character cflagspbm) {
        this.cflagspbm = cflagspbm;
    }

    public void setCflagspbm(boolean cflagspbm) {
        if(cflagspbm) setCflagspbm('Y');
        else setCflagspbm('N');
    }

    public Character getCflagnilai() {
        return this.cflagnilai;
    }

    public void setCflagnilai(Character cflagnilai) {
        this.cflagnilai = cflagnilai;
    }

    public void setCflagnilai(boolean cflagnilai) {
        if(cflagnilai) setCflagnilai('Y');
        else setCflagnilai('N');
    }

    public String getCemail() {
        return this.cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getCnohp() {
        return this.cnohp;
    }

    public void setCnohp(String cnohp) {
        this.cnohp = cnohp;
    }

    public String getCnmkntor() {
        return this.cnmkntor;
    }

    public void setCnmkntor(String cnmkntor) {
        this.cnmkntor = cnmkntor;
    }

    public String getCalmkntor() {
        return this.calmkntor;
    }

    public void setCalmkntor(String calmkntor) {
        this.calmkntor = calmkntor;
    }

    public String getCtelpkntor() {
        return this.ctelpkntor;
    }

    public void setCtelpkntor(String ctelpkntor) {
        this.ctelpkntor = ctelpkntor;
    }

    public String getCfaxkantor() {
        return this.cfaxkantor;
    }

    public void setCfaxkantor(String cfaxkantor) {
        this.cfaxkantor = cfaxkantor;
    }

    public MkodePos getCkdposkntor() {
        return ckdposkntor;
    }

    public void setCkdposkntor(MkodePos ckdposkntor) {
        this.ckdposkntor = ckdposkntor;
    }

    public String getCalamatsrt() {
        return this.calamatsrt;
    }

    public void setCalamatsrt(String calamatsrt) {
        this.calamatsrt = calamatsrt;
    }

    public MkodePos getKodeposSrtId() {
        return kodeposSrtId;
    }

    public void setKodeposSrtId(MkodePos kodeposSrtId) {
        this.kodeposSrtId = kodeposSrtId;
    }

    public Mstatusmhs getMstatusmhs() {
        return mstatusmhs;
    }

    public void setMstatusmhs(Mstatusmhs mstatusmhs) {
        this.mstatusmhs = mstatusmhs;
    }

    public String getCthnlaporan() {
        return this.cthnlaporan;
    }

    public void setCthnlaporan(String cthnlaporan) {
        this.cthnlaporan = cthnlaporan;
    }

    public String getCketkerja() {
        return this.cketkerja;
    }

    public void setCketkerja(String cketkerja) {
        this.cketkerja = cketkerja;
    }

    public String getCtujuan() {
        return this.ctujuan;
    }

    public void setCtujuan(String ctujuan) {
        this.ctujuan = ctujuan;
    }

    public String getCgelombang() {
        return this.cgelombang;
    }

    public void setCgelombang(String cgelombang) {
        this.cgelombang = cgelombang;
    }

    public String getCstatawal() {
        return this.cstatawal;
    }

    public void setCstatawal(String cstatawal) {
        this.cstatawal = cstatawal;
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

    public Mmhspascakhs getMmhspascakhs() {
        return mmhspascakhs;
    }

    public void setMmhspascakhs(Mmhspascakhs mmhspascakhs) {
        if(mmhspascakhs!=null) mmhspascakhs.setMahasiswaId(this.id);
        this.mmhspascakhs = mmhspascakhs;
    }

    public Set<Tabsenmhs> getTabsenmhses() {
        return this.tabsenmhses;
    }

    public void setTabsenmhses(Set<Tabsenmhs> tabsenmhses) {
        this.tabsenmhses = tabsenmhses;
    }

    public Set<Mkgtmhs> getMkgtmhses() {        
        return this.mkgtmhses;
    }

    public void setMkgtmhses(Set<Mkgtmhs> mkgtmhses) {
        for(Object anKegiatan : mkgtmhses)
            ((Mkgtmhs) anKegiatan).setMahasiswaId(this.id);

        this.mkgtmhses = mkgtmhses;
    }

    public Set<Mkaryamhs> getMkaryamhses() {
        return mkaryamhses;
    }

    public void setMkaryamhses(Set<Mkaryamhs> mkaryamhses) {
        for(Object anKarya : mkaryamhses)
            ((Mkaryamhs) anKarya).setMahasiswaId(this.id);

        this.mkaryamhses = mkaryamhses;
    }

    public Set<Mpbahasamhs> getMpbahasamhses() {
        return this.mpbahasamhses;
    }

    public void setMpbahasamhses(Set<Mpbahasamhs> mpbahasamhses) {
        for(Object anBahasa : mpbahasamhses)
            ((Mpbahasamhs) anBahasa).setMahasiswaId(this.id);
        
        this.mpbahasamhses = mpbahasamhses;
    }

    public Set<Tlognilai> getTlognilais() {
        return this.tlognilais;
    }

    public void setTlognilais(Set<Tlognilai> tlognilais) {
        this.tlognilais = tlognilais;
    }

    public Set<Malumni> getMalumnis() {
        return this.malumnis;
    }

    public void setMalumnis(Set<Malumni> malumnis) {
        this.malumnis = malumnis;
    }

    public Set<Mkurmhs> getMkurmhses() {
        return this.mkurmhses;
    }

    public void setMkurmhses(Set<Mkurmhs> mkurmhses) {
        this.mkurmhses = mkurmhses;
    }

    public Set<Mppumhskhusus> getMppumhskhususes() {
        return this.mppumhskhususes;
    }

    public void setMppumhskhususes(Set<Mppumhskhusus> mppumhskhususes) {
        for(Object anMppumhs : mppumhskhususes)
            ((Mppumhskhusus) anMppumhs).setMahasiswaId(this.id);
        
        this.mppumhskhususes = mppumhskhususes;
    }

    public Set<Mhistpangkatmhs> getMhistpangkatmhses() {
        return this.mhistpangkatmhses;
    }

    public void setMhistpangkatmhses(Set<Mhistpangkatmhs> mhistpangkatmhses) {
        for(Object anHisPangkat : mhistpangkatmhses)
            ((Mhistpangkatmhs) anHisPangkat).setMahasiswaId(this.id);

        this.mhistpangkatmhses = mhistpangkatmhses;
    }

    public Set<Mhistpnddkmhs> getMhistpnddkmhses() {
        return this.mhistpnddkmhses;
    }

    public void setMhistpnddkmhses(Set<Mhistpnddkmhs> mhistpnddkmhses) {
        for(Object anHisPnddk : mhistpnddkmhses)
            ((Mhistpnddkmhs) anHisPnddk).setMahasiswaId(this.id);
        
        this.mhistpnddkmhses = mhistpnddkmhses;
    }

    public Set<Mhistkursusmhs> getMhistkursusmhses() {
        return this.mhistkursusmhses;
    }

    public void setMhistkursusmhses(Set<Mhistkursusmhs> mhistkursusmhses) {
        for(Object anHisKursrs : mhistkursusmhses)
            ((Mhistkursusmhs) anHisKursrs).setMahasiswaId(this.id);

        this.mhistkursusmhses = mhistkursusmhses;
    }

    public Set<Tirspasca> getTirspascas() {
        return this.tirspascas;
    }

    public void setTirspascas(Set<Tirspasca> tirspascas) {
        this.tirspascas = tirspascas;
    }

    public Set<Mprestasimhs> getMprestasimhses() {
        return this.mprestasimhses;
    }

    public void setMprestasimhses(Set<Mprestasimhs> mprestasimhses) {
        for(Object anMprestasimhs : mprestasimhses)
            ((Mprestasimhs) anMprestasimhs).setMahasiswaId(this.id);

        this.mprestasimhses = mprestasimhses;
    }

    public Set<Tcutimhs> getTcutimhses() {
        return this.tcutimhses;
    }

    public void setTcutimhses(Set<Tcutimhs> tcutimhses) {
        this.tcutimhses = tcutimhses;
    }

    public Set<Tfeedbackalumni> getTfeedbackalumni() {
        return this.tfeedbackalumnis;
    }

    public void setTfeedbackalumni(Set<Tfeedbackalumni> tfeedbackalumnis) {
        this.tfeedbackalumnis = tfeedbackalumnis;
    }
}

