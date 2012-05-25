/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kmn.controller.props;

import com.kmn.controller.Confirm;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Hermanto
 */
public class EquipmentProperties implements PropertiesController {
    private static final String SYS_DIR = System.getProperty("user.dir");
    private static final String SYS_SEPARATOR = System.getProperty("file.separator");
    private static final String CONF_DIR = "conf";

    private Properties prop;

    private Confirm confirm;
    private String eqcode;
    private FileInputStream inProp;
    private FileOutputStream outProp;
    private List<EquipmentDetailProperties> eqList = new ArrayList<EquipmentDetailProperties>();

    public EquipmentProperties(Confirm confirm) {
        this.confirm = confirm;
    }
    
    @Override
    public void load() {
        File receiverDir = new File(SYS_DIR + SYS_SEPARATOR + CONF_DIR);
        File[] dirs = receiverDir.listFiles();
        for (File d : dirs) {
            String propName = d.getName();
            String[] lname = propName.split("-");
            if(propName.indexOf("equipment") < 1) continue;

            try {
                inProp = new FileInputStream(d);
                prop = new Properties();
                prop.load(inProp);
                
                EquipmentDetailProperties detail = new EquipmentDetailProperties(lname[0]);
                detail.setCode(prop.getProperty(lname[0] + "." + EQUIPMENT_CODE));
                detail.setName(prop.getProperty(lname[0] + "." + EQUIPMENT_NAME));
                detail.setType(prop.getProperty(lname[0] + "." + EQUIPMENT_TYPE));
                detail.setCom(prop.getProperty(lname[0] + "." + EQUIPMENT_COM));
                detail.setRate(prop.getProperty(lname[0] + "." + EQUIPMENT_BOUND_RATE));
                detail.setDataBit(prop.getProperty(lname[0] + "." + EQUIPMENT_DATA_BIT));
                detail.setParity(prop.getProperty(lname[0] + "." + EQUIPMENT_PARITY));
                detail.setStopBit(prop.getProperty(lname[0] + "." + EQUIPMENT_STOP_BIT));
                detail.setFlow(prop.getProperty(lname[0] + "." + EQUIPMENT_FLOW_CONTROL));
                detail.setIp(prop.getProperty(lname[0] + "." + EQUIPMENT_IP));
                detail.setPort(prop.getProperty(lname[0] + "." + EQUIPMENT_PORT));

                eqList.add(detail);
                
            } catch (IOException ex) {
                confirm.onError(ex);
            }
        }
    }

    @Override
    public void store() {
        for(EquipmentDetailProperties detail : eqList) {
            prop = new Properties();
            prop.setProperty(this.eqcode + "." + EQUIPMENT_CODE, detail.getCode());
            prop.setProperty(this.eqcode + "." + EQUIPMENT_NAME, detail.getName());
            prop.setProperty(this.eqcode + "." + EQUIPMENT_TYPE, detail.getType());
            prop.setProperty(this.eqcode + "." + EQUIPMENT_COM, detail.getCom());
            prop.setProperty(this.eqcode + "." + EQUIPMENT_BOUND_RATE, detail.getRate());
            prop.setProperty(this.eqcode + "." + EQUIPMENT_DATA_BIT, detail.getDataBit());
            prop.setProperty(this.eqcode + "." + EQUIPMENT_PARITY, detail.getParity());
            prop.setProperty(this.eqcode + "." + EQUIPMENT_STOP_BIT, detail.getStopBit());
            prop.setProperty(this.eqcode + "." + EQUIPMENT_FLOW_CONTROL, detail.getFlow());
            prop.setProperty(this.eqcode + "." + EQUIPMENT_IP, detail.getIp());
            prop.setProperty(this.eqcode + "." + EQUIPMENT_PORT, detail.getPort());

            try {
                if(inProp != null) inProp.close();

                outProp = new FileOutputStream(SYS_DIR + SYS_SEPARATOR + CONF_DIR + SYS_SEPARATOR + this.eqcode + "-" + EQUIPMENT_FILE);
                outProp.flush();
                prop.store(outProp, null);
                outProp.close();

            } catch (IOException ex) {
                confirm.onError(ex);
            }
        }
    }

    public String getEqcode() {
        return eqcode;
    }

    public void setEqcode(String eqcode) {
        this.eqcode = eqcode;
    }

    public EquipmentDetailProperties getDetalProperies(String ecode) {
        for(EquipmentDetailProperties li : eqList) {
            if(li.getEcode().equals(ecode))
            return li;
        }
        
        return null;
    }

    public void addDetalProperies(EquipmentDetailProperties detail) {
        eqList.add(detail);
    }

    public List<EquipmentDetailProperties> getEqList() {
        return eqList;
    }    
}
