/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.util;

import com.kmn.controller.InterfaceEvent;
import com.kmn.gui.workspace.ModelInterface;
//import gnu.io.CommPortIdentifier;
//import gnu.io.PortInUseException;
//import gnu.io.SerialPort;
//import gnu.io.SerialPortEvent;
//import gnu.io.SerialPortEventListener;
//import gnu.io.UnsupportedCommOperationException;
import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TooManyListenersException;

/**
 *
 * @author Hermanto
 */
public class CommInterface implements SerialPortEventListener, ModelInterface, Serializable {
    private static final long serialVersionUID = -8352659530536077973L;
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
    static boolean	threaded = true,
					silentReceive = false,
					modemMode = false,
					friendly = false,
					allPorts = true,
					lineMonitor = false;
    
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
                System.out.println("No comm ports found!");
                this.event.onMessage("No comm ports found!");
                return;
            }
            while (portList.hasMoreElements()) {
                /*
                 *  Get the specific port
                 */
                portId = (CommPortIdentifier) portList.nextElement();
                if(portId.getName().equalsIgnoreCase("COM3")
                        ||portId.getName().equalsIgnoreCase("COM31")
                        ||portId.getName().equalsIgnoreCase("COM32")
                        ||portId.getName().equalsIgnoreCase("COM88")) {
                    System.out.println("Detected " + portId.getName() + " in ignore list");	
                }else{
                    this.addPort(portId);
                }
            }
        } else {
            try {
                portId = CommPortIdentifier.getPortIdentifier(port);
                this.addPort(portId);
            } catch (NoSuchPortException e) {
                System.out.println(port + " does not exist!");
                this.event.onMessage(port + " does not exist!");
            }
        }
    	if (portNum > 0) {
            if (lineMonitor) {
                if (portNum >= 2) {
                    portObj[0].setLineMonitor(portObj[1], true);
                } else {
                    System.out.println("Need 2 ports for line monitor!");
                    this.event.onMessage("Need 2 ports for line monitor!");
                    System.exit(0);
                }
            }
        } else {
            System.out.println("No serial ports found!");
            this.event.onMessage("No serial ports found!");
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
                    System.out.println("Detected " + portId.getName()
                                        + " in use by " + portId.getCurrentOwner());
                    this.event.onMessage("Detected " + portId.getName()
                                        + " in use by " + portId.getCurrentOwner());
            }
            /*
             *  Open the port and add it to our GUI
             */
            try {
                portObj[portNum] = new SerialPortObject(portId, threaded, friendly,
                                                                    silentReceive, modemMode, rcvDelay, this.event);
                this.portNum++;
            } catch (PortInUseException e) {
                System.out.println(portId.getName() + " in use by " + e.currentOwner);
                this.event.onMessage(portId.getName() + " in use by " + e.currentOwner);
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
                    StringTokenizer st = new StringTokenizer(buffer, "\r\n");
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
