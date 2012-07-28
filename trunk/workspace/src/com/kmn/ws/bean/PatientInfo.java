/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.ws.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.soap.SOAPElement;
import org.w3c.dom.NodeList;

/**
 *
 * @author valeo
 */
public class PatientInfo {
    public static final String RESPAGENUMBER = "resPageNumber";
    public static final String RESROWTHISPAGE = "resRowThisPage";
    public static final String RESROWPERPAGE = "resRowPerPage";
    public static final String RESROWTOTAL = "resRowTotal";
    public static final String PATIENTS = "patients";
    public static final String PATIENT = "patient";

    protected int resPageNumber;
    protected int resRowThisPage;
    protected int resRowPerPage;
    protected int resRowTotal;
    protected List<Patient> patients;

    public int getResPageNumber() {
        return resPageNumber;
    }

    public void setResPageNumber(int resPageNumber) {
        this.resPageNumber = resPageNumber;
    }

    public int getResRowThisPage() {
        return resRowThisPage;
    }

    public void setResRowThisPage(int resRowThisPage) {
        this.resRowThisPage = resRowThisPage;
    }

    public int getResRowPerPage() {
        return resRowPerPage;
    }

    public void setResRowPerPage(int resRowPerPage) {
        this.resRowPerPage = resRowPerPage;
    }

    public int getResRowTotal() {
        return resRowTotal;
    }

    public void setResRowTotal(int resRowTotal) {
        this.resRowTotal = resRowTotal;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public void setVariable(SOAPElement soapElement) {
        if(soapElement.getElementQName().getLocalPart().equals(RESPAGENUMBER)){
            this.resPageNumber = Integer.valueOf(soapElement.getTextContent());
        } else if(soapElement.getElementQName().getLocalPart().equals(RESROWTHISPAGE)){
            this.resRowThisPage = Integer.valueOf(soapElement.getTextContent());
        } else if(soapElement.getElementQName().getLocalPart().equals(RESROWPERPAGE)){
            this.resRowPerPage = Integer.valueOf(soapElement.getTextContent());
        } else if(soapElement.getElementQName().getLocalPart().equals(RESROWTOTAL)){
            this.resRowTotal = Integer.valueOf(soapElement.getTextContent());
        } else if(soapElement.getElementQName().getLocalPart().equals(PATIENTS)){
            this.patients = setPatients(soapElement);
        }
    }
    public List<Patient> setPatients(SOAPElement soapElement) {
        List<Patient> patients = new ArrayList<Patient>();
        Patient patient = null;
        Iterator patientsIterator = soapElement.getChildElements();
        while (patientsIterator.hasNext()) {
            SOAPElement se = (SOAPElement) patientsIterator.next();
            if (se.getElementQName().getLocalPart().equals(PATIENT)) {
                patient = new Patient();
                Iterator patientIterator = se.getChildElements();
                while (patientIterator.hasNext()) {
                    SOAPElement patientElement = (SOAPElement) patientIterator.next();
                    patient.setVariable(patientElement);
                }
                patients.add(patient);
            }
        }
        return patients;
    }    
}
