package id.ac.idu.backend.model;
// Generated 06 Mar 12 10:03:27 by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Filiale generated by hbm2java
 */
public class Filiale implements java.io.Serializable {


    private int filId;
    private int version;
    private String filNr;
    private String filBezeichnung;
    private String filName1;
    private String filName2;
    private String filOrt;
    private Set<Kunde> kundes = new HashSet<Kunde>(0);

    public Filiale() {
    }


    public Filiale(int filId, String filNr) {
        this.filId = filId;
        this.filNr = filNr;
    }

    public Filiale(int filId, String filNr, String filBezeichnung, String filName1, String filName2, String filOrt, Set<Kunde> kundes) {
        this.filId = filId;
        this.filNr = filNr;
        this.filBezeichnung = filBezeichnung;
        this.filName1 = filName1;
        this.filName2 = filName2;
        this.filOrt = filOrt;
        this.kundes = kundes;
    }

    public int getFilId() {
        return this.filId;
    }

    public void setFilId(int filId) {
        this.filId = filId;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getFilNr() {
        return this.filNr;
    }

    public void setFilNr(String filNr) {
        this.filNr = filNr;
    }

    public String getFilBezeichnung() {
        return this.filBezeichnung;
    }

    public void setFilBezeichnung(String filBezeichnung) {
        this.filBezeichnung = filBezeichnung;
    }

    public String getFilName1() {
        return this.filName1;
    }

    public void setFilName1(String filName1) {
        this.filName1 = filName1;
    }

    public String getFilName2() {
        return this.filName2;
    }

    public void setFilName2(String filName2) {
        this.filName2 = filName2;
    }

    public String getFilOrt() {
        return this.filOrt;
    }

    public void setFilOrt(String filOrt) {
        this.filOrt = filOrt;
    }

    public Set<Kunde> getKundes() {
        return this.kundes;
    }

    public void setKundes(Set<Kunde> kundes) {
        this.kundes = kundes;
    }


}

