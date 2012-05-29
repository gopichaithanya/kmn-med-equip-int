/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.util;

import com.kmn.controller.InterfaceEvent;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
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
public class CommInterface implements SerialPortEventListener, Serializable {
    private static final long serialVersionUID = -8352659530536077973L;
    private Enumeration portList;
    private CommPortIdentifier portId;
    private SerialPort port;
    private List<String> respon = new ArrayList<String>();

    private InterfaceEvent event;
    private InputStream input;
    private OutputStream output;

    int n;
    byte[] bufferRead = new byte[100000];
    int bufferEnd = 0;
    
    String buffer;
    String sbuf;

    public CommInterface(InterfaceEvent event) {
        this.event = event;
    }

    public void connect(String port, int speed, int dataBit, int stopBit, int parity, int flow) {

        this.portList = CommPortIdentifier.getPortIdentifiers();
        while (this.portList.hasMoreElements()) {
            portId = (CommPortIdentifier) this.portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(port)) {
                    try {
                        // Buka Port Serial
                        this.port = (SerialPort) portId.open("JavaAT", 2000);
                        this.event.onMessage("trying connect to port " + port);
                    } catch (PortInUseException piue) {
                        this.event.onMessage("Port " + port + " already in use");
                    }
                }
            }
        }

        try {
            input = this.port.getInputStream();
            output = this.port.getOutputStream();
        } catch (IOException ex) {
            this.event.onError(ex);
        }

        try {
            this.port.setSerialPortParams(speed, dataBit, stopBit, parity);
            this.port.setFlowControlMode(flow);
            this.port.notifyOnDataAvailable(true);

            this.event.onMessage("Connected...");

            String lspeed = Integer.toString(this.port.getBaudRate());
            String lstopBit = Integer.toString(this.port.getStopBits());
            String ldataBit = Integer.toString(this.port.getDataBits());
            String lparity = Integer.toString(this.port.getParity());
            String lflow = Integer.toString(this.port.getFlowControlMode());
        } catch (UnsupportedCommOperationException ex) {
            this.event.onError(ex);
        }

        try {
            this.port.addEventListener(this);
        } catch (TooManyListenersException ex) {
            this.event.onError(ex);
        }
    }

    public void disconnect() {
        port.close();
        respon.clear();
    }

    public void sendCommand(String command) {
        try {
            output.write((command + "\r\n").getBytes());
            output.flush();
        } catch (IOException ex) {
            this.event.onError(ex);
        }
    }

    public void ctrlZ(String command) {
        String ATCommand = command.toUpperCase();
        try {
            output.write((ATCommand + "\32").getBytes());
            output.flush();
        }
        catch (IOException ex) {
            this.event.onError(ex);
        }

    }

    @Override
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
