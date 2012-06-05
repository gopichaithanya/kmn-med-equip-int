package com.kmn.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.CommPortOwnershipListener;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;

import com.kmn.controller.InterfaceEvent;
import com.kmn.gui.workspace.WorkspaceModel;

public class SerialPortObject implements SerialPortEventListener, MouseListener, CommPortOwnershipListener, InterfaceEvent {
	protected CommPortIdentifier portID;
	protected SerialPort port = null;
	protected InputStream in;
    protected OutputStream out, outSave;
	protected boolean lineMonitor, silentReceive, modemMode, open = false;
	protected int numDataBits = 1;
	
	private int rcvDelay;
	private Thread rcvThread = null;
	private WorkspaceModel owner;
	protected InterfaceEvent intEvt;
	private boolean	threadRcv, friendly, waiting = false;

	/*Panel			display,
				statePanel,
				textPanel;
	Label			portName = null;
	
	BaudRate		baudRate;
	DataBits		dataBits;
	Parity			parity;
	StopBits		stopBits;
	CtlSigDisplay		ctlSigs;
	FlowCtlDisplay		flowCtl;*/
	Receiver		receiver;
	Transmitter		transmitter;


	public SerialPortObject(CommPortIdentifier portID,
						boolean threadRcv,
						boolean	friendly,
						boolean	silentReceive,
						boolean	modemMode,
						int	rcvDelay, InterfaceEvent intEvt) throws PortInUseException {
		super();
		this.intEvt = intEvt;
		this.lineMonitor = false;
		this.outSave = null;
		this.portID = portID;
		this.threadRcv = threadRcv;
		this.friendly = friendly;
		this.rcvDelay = rcvDelay;
		this.silentReceive = silentReceive;
		this.modemMode = modemMode;
		this.openBBPort();
	}

	private boolean openBBPort() throws PortInUseException {
		if (this.open) {
			this.closeBBPort();
		}
		/*
		 *  Register an ownership listener so we can 
		 *  manage the port. 
		 */
		this.portID.addPortOwnershipListener(this);
		/*
		 *  Try to open the port
		 */
		try {
			port = (SerialPort) portID.open("KMN MEI Client", 2000);
			if (port == null) {
				System.out.println("Error opening port " + portID.getName());
                                this.intEvt.onMessage("Error opening port " + portID.getName());
				return false;
			} else {
				this.open = true;
				this.waiting = false;
			}
			/*
			 *  Get the input stream
			 */
			try {
				in = this.port.getInputStream();
			} catch (IOException e) {
				System.out.println("Cannot open input stream");
                                this.intEvt.onMessage("Cannot open input stream");
			}
			/*
			 *  Get the output stream
			 */
			try {
				if (modemMode) {
					out = new ConvertedOutputStream(port.getOutputStream());
				} else {
					out = port.getOutputStream();
				}
			} catch (IOException e) {
				System.out.println("Cannot open output stream");
                                this.intEvt.onMessage("Cannot open output stream");
			}
			//this.createPanel();
			//this.showValues();
			//receiver.setPort(this.port);
			//transmitter.setPort(this.port);
			receiver = new Receiver(this, 6, 40, this.rcvDelay, this.silentReceive);
			transmitter = new Transmitter(this, 6, 40, this.modemMode);
			/*
			 *  Setup an event listener for the port
			 */
			try {
				port.addEventListener(this);
			} catch (TooManyListenersException tmle) {
			    tmle.printStackTrace();
			}
			/*
			 *  These are the events we want to know about
			 */
			port.notifyOnCTS(true);
			port.notifyOnDSR(true);
			port.notifyOnRingIndicator(true);
			port.notifyOnCarrierDetect(true);
			port.notifyOnOverrunError(true);
			port.notifyOnParityError(true);
			port.notifyOnFramingError(true);
			port.notifyOnBreakInterrupt(true);
			port.notifyOnDataAvailable(true);
			port.notifyOnOutputEmpty(true);
			/*
			 *  Create the receiver thread
			 */
			if (this.threadRcv && (rcvThread == null)){
				rcvThread = new Thread(this.receiver, 
							"Rcv " + port.getName());
				rcvThread.start();
			} else {
				rcvThread = null;
			}
		} catch (PortInUseException e) {
			System.out.println("Queueing open for " + portID.getName()
					  + ": port in use by " + e.currentOwner);
                        this.intEvt.onMessage("Queueing open for " + portID.getName()
					  + ": port in use by " + e.currentOwner);
			/*if (portName != null) {
				portName.setForeground(Color.yellow);
			}*/
			this.waiting = true;
//			throw(e);
		}
		return true;
	}

	public void closeBBPort() {
		if (this.open) {
			System.out.println("Closing " + this.port.getName());
                        this.intEvt.onMessage("Closing " + this.port.getName());
			//this.portName.setForeground(Color.red);
			this.open = false;
			/*
			 *  Stop transmitting
			 */
			this.transmitter.stopTransmit();
			/*
			 *  Stop receiving
			 */
			if (this.rcvThread != null){
				this.rcvThread.interrupt();
				this.rcvThread = null;
			}
			/*
			 *  Remove the event listener for the port
			 */
			this.port.removeEventListener();
			/*
			 *  Remove the ownership event listener
			 */
			this.portID.removePortOwnershipListener(this);
			/*
			 *  Close the port
			 */
			this.port.close();
			this.port = null;
			//ctlSigs.setPort(this.port);
			//flowCtl.setPort(this.port);
			receiver.setPort(this.port);
			transmitter.setPort(this.port);
			transmitter.clearValues();
			receiver.clearValues();
			//flowCtl.clearValues();
			//ctlSigs.clearValues();
			//ctlSigs.clearErrorValues();
			//this.showValues();
		}
	}

	public SerialPort getPort()
	{
		return(this.open ? this.port : null);
	}

	private OutputStream getOutputStream()
	{
		return(this.out);
	}

	private void setOutputStream(OutputStream	newout)
	{
		this.outSave = getOutputStream();

		this.out = newout;
	}

	public void setLineMonitor(SerialPortObject	other, boolean value) {
		/*
		 *  To make a line monitor, we simply take two ports
		 *  and interchange their output streams!
		 */

		this.lineMonitor = value;
		other.lineMonitor = value;

		if (this.lineMonitor)
		{
			this.setOutputStream(other.getOutputStream());

			other.setOutputStream(this.outSave);
		}

		else
		{
			other.setOutputStream(this.getOutputStream());

			this.setOutputStream(this.outSave);
		}
	}
/*
	private void createPanel()
	{

		if (portName != null)
		{
			ctlSigs.setPort(this.port);

			flowCtl.setPort(this.port);

			receiver.setPort(this.port);

			transmitter.setPort(this.port);
		}

		else
		{
	
			display = new Panel();
			display.setLayout(new FlowLayout());
	
			portName = new Label(portID.getName());
	
			if (this.open)
			{
				portName.setForeground(Color.green);
			}
	
			else if (this.waiting)
			{
				portName.setForeground(Color.yellow);
			}
	
			else
			{
				portName.setForeground(Color.red);
			}
	
			portName.addMouseListener(this);
			display.add(portName);
	
			baudRate = new BaudRate(this);
			display.add(baudRate);
	
			dataBits = new DataBits(this);
			display.add(dataBits);
	
			stopBits = new StopBits(this);
			display.add(stopBits);
	
			parity = new Parity(this);
			display.add(parity);
	
			this.add("North", display);
	
	
			statePanel = new Panel();
			statePanel.setLayout(new BorderLayout());
	
			ctlSigs = new CtlSigDisplay(port);
			statePanel.add("North", ctlSigs);
	
			flowCtl = new FlowCtlDisplay(port);
			statePanel.add("South", flowCtl);
	
			this.add("South", statePanel);
	
	
			textPanel = new Panel();
			textPanel.setLayout(new BorderLayout());
	
			receiver = new Receiver(this, 6, 40, 
						this.rcvDelay, 
						this.silentReceive);
	
			textPanel.add("East", receiver);
	
			transmitter = new Transmitter(this, 6, 40,
						      this.modemMode);
	
			textPanel.add("West", transmitter);
	
			this.add("Center", textPanel);
	
			owner.addPanel(this);
		}
	}*/

	/*protected void showValues()
	{
		baudRate.showValue();
		dataBits.showValue();
		stopBits.showValue();
		parity.showValue();

		transmitter.setBitsPerCharacter(numDataBits);
		receiver.setBitsPerCharacter(numDataBits);

		transmitter.showValues();
		receiver.showValues();
		flowCtl.showValues();
		ctlSigs.showValues();
		ctlSigs.showErrorValues();
	}*/

	/*
	 *	Handler for all serial port events
	 */
	public void serialEvent(SerialPortEvent	ev) {
		if (this.port == null) {
                    System.out.println(port.getName() + "got serial event on a closed port");
                    this.intEvt.onMessage(port.getName() + " got serial event on a closed port");
                    return;
		}
		switch(ev.getEventType()) {
		case SerialPortEvent.BI:
			//this.ctlSigs.BILabel.setState(ev.getNewValue());
			System.out.println(port.getName()+ " SerialPortEvent.BI "  + SerialPortEvent.BI);
			break;
		case SerialPortEvent.OE:
			//this.ctlSigs.OELabel.setState(ev.getNewValue());
			System.out.println(port.getName()+ " SerialPortEvent.OE "  + SerialPortEvent.OE);
			break;
		case SerialPortEvent.FE:
			//this.ctlSigs.FELabel.setState(ev.getNewValue());
			System.out.println(port.getName()+ " SerialPortEvent.FE " + SerialPortEvent.FE);
			break;
		case SerialPortEvent.PE:
			//this.ctlSigs.PELabel.setState(ev.getNewValue());
			System.out.println(port.getName()+ " SerialPortEvent.PE " + SerialPortEvent.PE);
			break;
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
			//this.ctlSigs.showValues();
			System.out.println(port.getName()+ " SerialPortEvent.RI " + SerialPortEvent.RI);
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			//this.ctlSigs.DA = true;
			//this.ctlSigs.showErrorValues();
			System.out.println(port.getName() + " Incoming Data");
                        this.intEvt.onMessage(port.getName() + " Incoming Data");
			if (rcvThread != null) {
				synchronized (receiver) {
					receiver.notify();
				}
			} else if (threadRcv) {
				System.out.println(port.getName() + "Receive thread has died!");
                                this.intEvt.onMessage(port.getName() + " Receive thread has died!");
				rcvThread = new Thread(this.receiver, "Rcv " + port.getName());
				rcvThread.start();
			} else {
				this.receiver.readData();
			}
			break;
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			//this.ctlSigs.BE = true;
			//this.ctlSigs.showErrorValues();
			System.out.println(port.getName() + " OUTPUT_BUFFER_EMPTY");
                        this.intEvt.onMessage(port.getName() + " OUTPUT_BUFFER_EMPTY");
			break;
		}
	}
	/*
	 *	Handler to open/close port
	 */
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
//		this.showValues();
		if (this.open) {
			this.closeBBPort();
		} else {
			try {
				openBBPort();
			} catch (PortInUseException ex) {
				System.out.println(portID.getName() + " is in use by " + ex.currentOwner);
                                this.intEvt.onMessage(port.getName() + " is in use by " + ex.currentOwner); 
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	/*
	 *	Handler for port ownership events.
	 */
	public void ownershipChange(int type) {
		switch (type) {
		case CommPortOwnershipListener.PORT_UNOWNED:
			System.out.println(portID.getName() + ": PORT_UNOWNED");
                        this.intEvt.onMessage(portID.getName() + ": PORT_UNOWNED");
			if (this.waiting) {
				/*
				 *  Try to open the port
				 */
				try {
					openBBPort();
				} catch (PortInUseException e) {
					System.out.println(portID.getName() + " s/b free but is in use by " + e.currentOwner);
                                        this.intEvt.onMessage(portID.getName() + " s/b free but is in use by " + e.currentOwner);
				}
			}
			break;
		case CommPortOwnershipListener.PORT_OWNED:
			System.out.println(portID.getName() + ": PORT_OWNED");
                        this.intEvt.onMessage(portID.getName() + " Listening");
			break;
		case CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED:
			System.out.println(portID.getName() + ": PORT_OWNERSHIP_REQUESTED");
                        this.intEvt.onMessage(portID.getName() + " PORT_OWNERSHIP_REQUESTED");
			if (this.friendly && this.open) {
				/*
				 *  Give up the port
				 */
				this.closeBBPort();
			}
			break;
		}
	}

	public void onSend() {
		// TODO Auto-generated method stub
		
	}

	public void onReceive(String message) {
		this.owner.onMessage(message);
	}

	public void onError(Throwable t) {
		// TODO Auto-generated method stub
		
	}

	public void onMessage(String message) {
		// TODO Auto-generated method stub
		
	}
}

