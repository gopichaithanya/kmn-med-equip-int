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
    public static final String SDF = "ddMMMyyyyHHmmss";
    public static final String AE = "DCMRCV";
    public static final String JFPH = "yy/MM/dd/HH/mm/ss";
    public static final String MSG_START_LISTENING = "Start Server listening on port ";
    
    private DcmRcvExt dcmrcv;
    private InterfaceEvent event;
    private int port;
    
    public DicomInterface(InterfaceEvent event, String ip, int port
            , String applicationEntity, String destination) {
        constructDicomInterface(event, ip, port, applicationEntity, destination, 1);
    }
    
    public DicomInterface(InterfaceEvent event, String ip, int port
            , String applicationEntity, String destination, int frameNumber) {
        constructDicomInterface(event, ip, port, applicationEntity, destination, frameNumber);
    }
    
    private void constructDicomInterface(InterfaceEvent event, String ip, int port
            , String applicationEntity, String destination, int frameNumber) {
        this.event = event;
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(SDF);
        sdf.format(now);
        String ae = (!applicationEntity.isEmpty())?applicationEntity:AE;
        this.dcmrcv = new DcmRcvExt(ae, event, frameNumber);
        this.dcmrcv.setPort(port);
        this.port = port;
        if (!destination.isEmpty()) {
            dcmrcv.setDestination(destination);
            dcmrcv.setDestinationExt(destination);
        }
        dcmrcv.setPackPDV(false);
        dcmrcv.setTcpNoDelay(false);
        dcmrcv.setJournal(destination);
        dcmrcv.setJournalFilePathFormat(JFPH);
        this.dcmrcv.initTransferCapability();
    }
    @Override
    public void connect() {
        try {
            this.dcmrcv.start();
            this.event.onMessage(MSG_START_LISTENING + this.port);
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

    public void getOutputString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
