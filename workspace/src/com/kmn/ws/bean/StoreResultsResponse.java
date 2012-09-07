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
public class StoreResultsResponse {
    public static final String SUCCESS = "success";
    public static final String RESULT = "result";
    public boolean success = false;
    public String result = "";

    public StoreResultsResponse() {
    }
    
    public StoreResultsResponse(boolean success, String result) {
        this.success = success;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public void setStoreResultsResponse(SOAPElement soapElement) {
        if(soapElement.getElementQName().getLocalPart().equals(SUCCESS)){
            this.success = Boolean.valueOf(soapElement.getTextContent());
        } else if(soapElement.getElementQName().getLocalPart().equals(RESULT)){
            this.result = soapElement.getTextContent();
        }
    }
}
