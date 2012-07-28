/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.ws.bean;

import javax.xml.soap.SOAPElement;

/**
 *
 * @author valeo
 */
public class Patient {
    public static final String PATIENTS = "patients";
    public static final String PATIENTID = "patientId";
    public static final String PATIENTNAME = "patientName";
    public static final String PATIENTBRM = "patientBrm";
    public static final String DOCID = "docId";
    public static final String DOCNAME = "docName";
    private String patientId;
    private String patientName;
    private String patientBrm;
    private String docId;
    private String docName;

    public Patient() {}

    public Patient(String patientId, String patientName, String patientBrm, String docId, String docName) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientBrm = patientBrm;
        this.docId = docId;
        this.docName = docName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientBrm() {
        return patientBrm;
    }

    public void setPatientBrm(String patientBrm) {
        this.patientBrm = patientBrm;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
    
    public void setVariable(SOAPElement soapElement) {
        if(soapElement.getElementQName().getLocalPart().equals(PATIENTID)){
            this.patientId = soapElement.getTextContent();
        } else if(soapElement.getElementQName().getLocalPart().equals(PATIENTNAME)){
            this.patientName = soapElement.getTextContent();
        } else if(soapElement.getElementQName().getLocalPart().equals(PATIENTBRM)){
            this.patientBrm = soapElement.getTextContent();
        } else if(soapElement.getElementQName().getLocalPart().equals(DOCID)){
            this.docId = soapElement.getTextContent();
        } else if(soapElement.getElementQName().getLocalPart().equals(DOCNAME)){
            this.docName = soapElement.getTextContent();
        }
    }
}
