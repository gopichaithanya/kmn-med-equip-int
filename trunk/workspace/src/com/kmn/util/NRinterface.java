/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.util;

import com.kmn.controller.InterfaceEvent;
import com.kmn.gui.workspace.ModelInterface;
import gnu.io.NRSerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author valeo
 */
public class NRinterface implements SerialPortEventListener, ModelInterface, Serializable {
    public static final String MSG_NO_COMM_PORT = "No comm ports found!";
    public static final String MSG_NOT_EXIST = " does not exist!";
    public static final String MSG_LINE_MON = "Need 2 ports for line monitor!";
    public static final String MSG_NO_PORT = "No serial ports found!";
    public static final String MSG_DETECTED = "Detected ";
    public static final String MSG_IN_USE_BY = " in use by ";
    public static final String CRLF = "\r\n";
    
    private InterfaceEvent event;
    private String port;
    private int speed;
    private int dataBit;
    private int stopBit;
    private int parity;
    private int flow;
    private NRSerialPort serial;
    private InputStream input;
    
    int n;
    byte[] bufferRead = new byte[100000];
    int bufferEnd = 0;
    
    String buffer;
    String sbuf;
    
//    public NRinterface(InterfaceEvent event, String port
//            , int speed, int dataBit, int stopBit, int parity, int flow)
    public NRinterface(InterfaceEvent event, String port, int speed) {
        this.event = event;
        this.port = port;
        this.speed = speed;
//        this.dataBit = dataBit;
//        this.stopBit = stopBit;
//        this.parity = parity;
//        this.flow = flow;
        Set<String> set = NRSerialPort.getAvailableSerialPorts();
        System.out.println("Available Ports: " + set.size());
        this.serial = new NRSerialPort(port, speed);   
    }

    public void connect() {
        System.out.println("\nConnecting to: " + serial.toString());
        serial.connect();
        serial.notifyOnDataAvailable(true);
        System.out.println("\nserial.isConnected(): " + serial.isConnected());
    }

    public void close() {
        serial.disconnect();
    }
    
    public void serialEvent(SerialPortEvent spe) {
        System.out.println("serialEvent: " + spe.toString());
        StringBuffer eventResponse = new StringBuffer();
        try {
            while ((n = input.available()) > 0) {
                n = input.read(bufferRead, bufferEnd, n);
                bufferEnd += n;
                if ((bufferRead[bufferEnd - 1] == 10) && (bufferRead[bufferEnd - 2] == 13)) {
                    buffer = new String(bufferRead, 0, bufferEnd - 2);
                    StringTokenizer st = new StringTokenizer(buffer, CRLF);
                    while (st.hasMoreTokens()) {
                        sbuf = st.nextToken();
                        eventResponse.append(sbuf);
                    }
                    bufferEnd = 0;
                }
            }
            /* Test new functionality */
            DataInputStream ins = new DataInputStream(serial.getInputStream());
            DataOutputStream outs = new DataOutputStream(serial.getOutputStream());
            byte b;
            try {
                b = (byte) ins.read();
                outs.write(b);
                System.out.print("\n"+outs.toString());
                System.out.print("\n"+String.valueOf(outs));
                //this.event.onReceive(outs.toString());
            } catch (IOException ex) {
                Logger.getLogger(NRinterface.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.print("\n"+eventResponse.toString());
            this.event.onReceive(eventResponse.toString());
        } catch (Exception ex) {
            this.event.onError(ex);
        }
    }
}
