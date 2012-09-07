package com.kmn.util;
/*
 * @(#)Receiver.java	1.14 98/08/13 SMI
 *
 * Author: Tom Corson
 *
 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license 
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS
 * BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 */

import java.io.IOException;

import java.lang.Character;

import java.awt.Panel;
import java.awt.TextArea;
import java.awt.BorderLayout;

import javax.comm.SerialPort;

public class Receiver implements Runnable {
    private String resultText = "";
    //private ReceiveOptions options;
    //private ByteStatistics counter;
    private SerialPortObject owner;
    private byte[] buffer;
    private int textCount;
    private int delay;
    private boolean silentReceive;

    public Receiver(SerialPortObject owner, int rows, int cols) {
            super();
            this.owner = owner;
            this.buffer = new byte[2048];
            this.textCount = 0;
            this.delay = 0;
            this.silentReceive = false;
    }

    public Receiver(SerialPortObject owner, int	rows, int cols, int delay) {
            this(owner, rows, cols);
            this.delay = delay;
    }

    public Receiver(SerialPortObject owner, int rows, int cols, int delay, boolean silentReceive) {
            this(owner, rows, cols,delay);
            this.silentReceive = silentReceive;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public boolean isSilentReceive() {
        return silentReceive;
    }

    public void setSilentReceive(boolean silentReceive) {
        this.silentReceive = silentReceive;
    }

    public void setPort(SerialPort	port) {
        //this.counter.setPort(port);
        //this.options.setPort(port);
    }

    public void showValues() {
        //this.options.showValues();
        //this.counter.showValues();
    }

    public void clearValues() {
        //this.text.setText("");
        //this.counter.clearValues();
        //this.options.clearValues();
    }

    public void setBitsPerCharacter(int	val) {
        //this.counter.setBitsPerCharacter(val);
    }

    public void run() {
        while (this.owner.open) {
            try {
                synchronized (this) {
                        wait(10000);
                }
            } catch (InterruptedException e) {
            }
            //if (this.owner.ctlSigs.DA) {
                this.readData();
            //}
        }
    }

    private String displayText(byte[] bytes, int byteCount) {
        String	str;
        int	i, idx;
        byte[] nb;
        /*  Don't let the text area get too big! */
        if (this.textCount > 5000) {
                //this.text.setText("");
                this.textCount = 0;
        }
        nb = new byte[byteCount * 4];
        for (i = 0, idx = 0; i < byteCount; i++) {
            /*  Wrap any control characters	*/
            if (Character.isISOControl((char) bytes[i]) && !Character.isWhitespace((char) bytes[i])) {
                nb[idx++] = (byte) '<';				
                nb[idx++] = (byte) '^';				
                nb[idx++] = (byte) (bytes[i] + 64);
                nb[idx++] = (byte) '>';				
            } else {
                nb[idx++] = bytes[i];
            }
        }
        if (!silentReceive) {
            str = new String(nb, 0, idx);
            this.resultText = str;
            this.owner.intEvt.onReceive(str);
            System.out.println(owner.port.getName() + ": Receiver.displayText(): " + str);
        } else {
            str =  new String(nb, 0, idx);
            this.resultText += str;
            System.out.println(owner.port.getName() + ": Receiver.displayText.silentReceive: " + str);
            /*
             * Send results to UI for Equipment that does not send 
             * SerialPortEvent.CTS twice.
             */
            if(this.resultText.endsWith("<^C>")) {
                String[] strArr = this.resultText.split("<.C>");
                for(int j = 0; j< strArr.length; j++) {
                    //owner.intEvt.onReceive(getResultText());
                    owner.intEvt.onReceive(strArr[j]);
                }
                setResultText("");
            }
        }
        //this.counter.incrementValue((long) byteCount);
        this.textCount += byteCount;
        return(str);
    }

    public void readData() {
        String	str;
        int	bytes;
        long endTime, now;
        try {
            while (this.owner.open && (this.owner.in.available() > 0 )) {
                bytes = this.owner.in.read(this.buffer);
                if (bytes > 0) {
                    if (bytes > this.buffer.length) {
                        System.out.println(owner.port.getName() + ": Input buffer overflow!");
                    }
                    System.out.println(owner.port.getName() + ": Receiver.readData");
                    str =  this.displayText(this.buffer, bytes);
                    if (this.owner.lineMonitor) {
                        this.owner.transmitter.sendString(str);
                    }
                }
                if (this.delay > 0) {
                    endTime = System.currentTimeMillis() + (long) delay;
                    now = 0;
                    while (now < endTime) {
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                        }
                        now =  System.currentTimeMillis();
                    }
                }
            } 
        } catch (IOException ex) {
            System.out.println(owner.port.getName() + ": Cannot read input stream");
        }
    } 
}
