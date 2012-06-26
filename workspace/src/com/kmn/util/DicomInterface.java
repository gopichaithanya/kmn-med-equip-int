/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.util;

import com.kmn.controller.InterfaceEvent;
import com.kmn.gui.workspace.ModelInterface;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author valeo
 */
public class DicomInterface implements ModelInterface, Serializable {
    private DcmRcvExt dcmrcv;
    private InterfaceEvent event;
    private int port;
    public DicomInterface(InterfaceEvent event, String ip, int port
            , String applicationEntity, String destination) {
        this.event = event;
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyyHHmmss");
        sdf.format(now);
        String ae = (!applicationEntity.isEmpty())?applicationEntity:"DCMRCV";
        //this.dcmrcv = new DcmRcv(ae);
        this.dcmrcv = new DcmRcvExt(ae, event);
        this.dcmrcv.setPort(port);
        this.port = port;
        //this.dcmrcv.setAEtitle(applicationEntity);
        //this.dcmrcv.setHostname(ip);
        if (!destination.isEmpty()) {
            dcmrcv.setDestination(destination);
            dcmrcv.setDestinationExt(destination);
        }
        dcmrcv.setPackPDV(false);
        dcmrcv.setTcpNoDelay(false);
        dcmrcv.setJournal(destination);
        dcmrcv.setJournalFilePathFormat("yy/MM/dd/HH/mm/ss");
        this.dcmrcv.initTransferCapability();
    }
    @Override
    public void connect() {
        try {
            this.dcmrcv.start();
            this.event.onMessage("Start Server listening on port "+ this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (this.dcmrcv != null) { 
            this.dcmrcv.stop();
        }
    }
    
}
