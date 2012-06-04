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
    private FileInputStream inProp;
    private FileOutputStream outProp;
    private List<EquipmentDetailProperties> listDetailEquipment = new ArrayList<EquipmentDetailProperties>();

    public EquipmentProperties(Confirm confirm) {
        this.confirm = confirm;
    }
    
    //@Override
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
                
                EquipmentDetailProperties detail = new EquipmentDetailProperties();
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
                detail.setInterfaceType(prop.getProperty(lname[0] + "." + EQUIPMENT_INTERFACE));

                listDetailEquipment.add(detail);
                inProp.close();
                
            } catch (IOException ex) {
                confirm.onError(ex);
            }
        }
    }

    private boolean isExist(String code) {
        boolean isexist = false;
        for(EquipmentDetailProperties detail : listDetailEquipment) {
            if(detail.getCode().equals(code)) isexist = true;
        }

        return isexist;
    }

    //@Override
    public void store() {
        for(EquipmentDetailProperties detail : listDetailEquipment) {
            prop = new Properties();
            prop.setProperty(detail.getCode() + "." + EQUIPMENT_CODE, (detail.getCode()==null)?"":detail.getCode());
            prop.setProperty(detail.getCode() + "." + EQUIPMENT_NAME, (detail.getName()==null)?"":detail.getName());
            prop.setProperty(detail.getCode() + "." + EQUIPMENT_TYPE, (detail.getType()==null)?"":detail.getType());
            prop.setProperty(detail.getCode() + "." + EQUIPMENT_COM, (detail.getCom()==null)?"":detail.getCom());
            prop.setProperty(detail.getCode() + "." + EQUIPMENT_BOUND_RATE, (detail.getRate()==null)?"":detail.getRate());
            prop.setProperty(detail.getCode() + "." + EQUIPMENT_DATA_BIT, (detail.getDataBit()==null)?"":detail.getDataBit());
            prop.setProperty(detail.getCode() + "." + EQUIPMENT_PARITY, (detail.getParity()==null)?"":detail.getParity());
            prop.setProperty(detail.getCode() + "." + EQUIPMENT_STOP_BIT, (detail.getStopBit()==null)?"":detail.getStopBit());
            prop.setProperty(detail.getCode() + "." + EQUIPMENT_FLOW_CONTROL, (detail.getFlow()==null)?"":detail.getFlow());
            prop.setProperty(detail.getCode() + "." + EQUIPMENT_IP, (detail.getIp()==null)?"":detail.getIp());
            prop.setProperty(detail.getCode() + "." + EQUIPMENT_PORT, (detail.getPort()==null)?"":detail.getPort());
            prop.setProperty(detail.getCode() + "." + EQUIPMENT_INTERFACE, (detail.getInterfaceType()==null)?"":detail.getInterfaceType());

            try {
                outProp = new FileOutputStream(SYS_DIR + SYS_SEPARATOR
                        + CONF_DIR + SYS_SEPARATOR + detail.getCode()
                        + "-" + EQUIPMENT_FILE);
                outProp.flush();
                prop.store(outProp, null);
                outProp.close();
            }
            catch (IOException ex) { confirm.onError(ex); }
        }

        prop.clear();
        
        File receiverDir = new File(SYS_DIR + SYS_SEPARATOR + CONF_DIR);
        File[] dirs = receiverDir.listFiles();
        for (File d : dirs) {
            String propName = d.getName();
            String[] lname = propName.split("-");
            if(!isExist(lname[0]))
                d.delete();
        }
        
        this.load();

    }

    public EquipmentDetailProperties getDetalProperies(String ecode) {
        for(EquipmentDetailProperties li : listDetailEquipment) {
            if(li.getCode().equals(ecode)) return li;
        }
        
        return null;
    }

    public List<EquipmentDetailProperties> getListDetailEquipment() {
        return listDetailEquipment;
    }

    public void setListDetailEquipment(List<EquipmentDetailProperties> listDetailEquipment) {
        this.listDetailEquipment = listDetailEquipment;
    }
}
