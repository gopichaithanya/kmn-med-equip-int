/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.util;

import com.kmn.controller.InterfaceEvent;
import com.kmn.gui.workspace.ModelInterface;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.comm.*;

/**
 *
 * @author Hermanto
 */
public class CommInterface implements SerialPortEventListener, ModelInterface, Serializable {
    public static final String MSG_NO_COMM_PORT = "No comm ports found!";
    public static final String MSG_NOT_EXIST = " does not exist!";
    public static final String MSG_LINE_MON = "Need 2 ports for line monitor!";
    public static final String MSG_NO_PORT = "No serial ports found!";
    public static final String MSG_DETECTED = "Detected ";
    public static final String MSG_IN_USE_BY = " in use by ";
    public static final String CRLF = "\r\n";
    static final long serialVersionUID = -8352659530536077973L;
    @SuppressWarnings("rawtypes")
    Enumeration portList;
    CommPortIdentifier portId;
    //private SerialPort port;
    //private List<String> respon = new ArrayList<String>();

    private InterfaceEvent event;
    private InputStream input;
    //private OutputStream output;

    int n;
    byte[] bufferRead = new byte[100000];
    int bufferEnd = 0;
    
    String buffer;
    String sbuf;
    
    static SerialPortObject[]	portObj;
    static int portNum = 0, panelNum = 0, rcvDelay = 0;
    static boolean threaded = true, silentReceive = true, modemMode = false,
                   friendly = false, allPorts = true, lineMonitor = false;
    
    private String port;
    private int speed;
    private int dataBit;
    private int stopBit;
    private int parity;
    private int flow;

    public CommInterface(InterfaceEvent event, String port
            , int speed, int dataBit, int stopBit, int parity, int flow) {
        this.event = event;
        this.port = port;
        this.speed = speed;
        this.dataBit = dataBit;
        this.stopBit = stopBit;
        this.parity = parity;
        this.flow = flow;
    }

    @Override
    public void connect() {
    	boolean allPorts = false, lineMonitor = false;
    	/* initialize number of ports to be read */
    	portObj = new SerialPortObject[20];
    	if (allPorts) {
            /*
             *  Get an enumeration of all of the comm ports on the machine
             */
            portList =  CommPortIdentifier.getPortIdentifiers();
            if (portList == null) {
                System.out.println(MSG_NO_COMM_PORT);
                this.event.onMessage(MSG_NO_COMM_PORT);
                return;
            }
            while (portList.hasMoreElements()) {
                /*
                 *  Get the specific port
                 */
                portId = (CommPortIdentifier) portList.nextElement();
                this.addPort(portId);
            }
        } else {
            try {
                portId = CommPortIdentifier.getPortIdentifier(port);
                this.addPort(portId);
            } catch (NoSuchPortException e) {
                System.out.println(port + MSG_NOT_EXIST);
                this.event.onMessage(port + MSG_NOT_EXIST);
            }
        }
    	if (portNum > 0) {
            if (lineMonitor) {
                if (portNum >= 2) {
                    portObj[0].setLineMonitor(portObj[1], true);
                } else {
                    System.out.println(MSG_LINE_MON);
                    this.event.onMessage(MSG_LINE_MON);
                    System.exit(0);
                }
            }
        } else {
            System.out.println(MSG_NO_PORT);
            this.event.onMessage(MSG_NO_PORT);
            System.exit(0);
        }
    }
    
    @Override
    public void close(){
        for (SerialPortObject spo : portObj) {
            try {
                spo.closeBBPort();
            } catch (Exception e) {
            }
        }
    }
    
    @SuppressWarnings("static-access")
    private void addPort(CommPortIdentifier	portId) {
        /*
         *  Is this a serial port?
         */
        if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            //  Is the port in use?	 
            if (portId.isCurrentlyOwned()) {
                    System.out.println(MSG_DETECTED + portId.getName()
                                        + MSG_IN_USE_BY + portId.getCurrentOwner());
                    this.event.onMessage(MSG_DETECTED + portId.getName()
                                        + MSG_IN_USE_BY + portId.getCurrentOwner());
            }
            /*
             *  Open the port and add it to our GUI
             */
            try {
                portObj[portNum] = new SerialPortObject(portId, threaded, friendly,
                                    silentReceive, modemMode, rcvDelay, this.event);
                this.portNum++;
            } catch (PortInUseException e) {
                System.out.println(portId.getName() + MSG_IN_USE_BY + e.currentOwner);
                this.event.onMessage(portId.getName() + MSG_IN_USE_BY + e.currentOwner);
            }
        }
    }
    
    //@Override
    public void serialEvent(SerialPortEvent spe) {
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
            this.event.onReceive(eventResponse.toString());
        } catch (Exception ex) {
            this.event.onError(ex);
        }
    }
}
