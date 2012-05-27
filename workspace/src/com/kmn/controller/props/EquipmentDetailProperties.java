/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.controller.props;

/**
 *
 * @author Hermanto
 */
public class EquipmentDetailProperties {
    private String ecode;
    private String code;
    private String name;
    private String type;
    private String com;
    private String rate;
    private String dataBit;
    private String parity;
    private String stopBit;
    private String flow;
    private String ip;
    private String port;
    private String interfaceType;

    public EquipmentDetailProperties() {
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getDataBit() {
        return dataBit;
    }

    public void setDataBit(String dataBit) {
        this.dataBit = dataBit;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParity() {
        return parity;
    }

    public void setParity(String parity) {
        this.parity = parity;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStopBit() {
        return stopBit;
    }

    public void setStopBit(String stopBit) {
        this.stopBit = stopBit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }
}
