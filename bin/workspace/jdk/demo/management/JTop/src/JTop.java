/*
 * @(#)JTop.java	1.6 10/01/12
 *
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

/*
 * @(#)JTop.java	1.6 10/01/12
 *
 * Example of using the java.lang.management API to sort threads
 * by CPU usage.
 *
 * JTop class can be run as a standalone application.
 * It first establishs a connection to a target VM specified
 * by the given hostname and port number where the JMX agent
 * to be connected.  It then polls for the thread information
 * and the CPU consumption of each thread to display every 2 
 * seconds.
 *
 * It is also used by JTopPlugin which is a JConsolePlugin
 * that can be used with JConsole (see README.txt). The JTop
 * GUI will be added as a JConsole tab by the JTop plugin.
 *
 * @see com.sun.tools.jconsole.JConsolePlugin
 *
 * @author Mandy Chung
 */
import java.lang.management.*;
import javax.management.*;
import javax.management.remote.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.text.NumberFormat;
import java.net.MalformedURLException;
import static java.lang.management.ManagementFactory.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * JTop is a JPanel to display thread's name, CPU time, and its state
 * in a table.
 */
public class JTop extends JPanel {
    private MBeanServerConnection server;
    private ThreadMXBean tmbean;
    private MyTableModel tmodel;
    public JTop() {
        super(new GridLayout(1,0));

        tmodel = new MyTableModel(); 
        JTable table = new JTable(tmodel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));

        // Set the renderer to format Double
        table.setDefaultRenderer(Double.class, new DoubleRenderer());
        // Add some space
        table.setIntercellSpacing(new Dimension(6,3));
        table.setRowHeight(table.getRowHeight() + 4);

        // Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to this panel.
        add(scrollPane);
    }

    // Set the MBeanServerConnection object for communicating
    // with the target VM
    public void setMBeanServerConnection(MBeanServerConnection mbs) {
        this.server = mbs;
        try {
            this.tmbean = newPlatformMXBeanProxy(server,
                                                 THREAD_MXBEAN_NAME,
                                                 ThreadMXBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!tmbean.isThreadCpuTimeSupported()) {
            System.err.println("This VM does not support thread CPU time monitoring");
        } else {
            tmbean.setThreadCpuTimeEnabled(true);
        }
    }

    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"ThreadName",
                                        "CPU(sec)",
                                        "State"};
        // List of all threads. The key of each entry is the CPU time
        // and its value is the ThreadInfo object with no stack trace.
        private List<Map.Entry<Long, ThreadInfo>> threadList = 
            Collections.EMPTY_LIST;

        public MyTableModel() {
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return threadList.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            Map.Entry<Long, ThreadInfo> me = threadList.get(row);
            switch (col) {
                case 0 : 
                    // Column 0 shows the thread name
                    return me.getValue().getThreadName();
                case 1 : 
                    // Column 1 shows the CPU usage
                    long ns = me.getKey().longValue();
                    double sec = ns / 1000000000;
                    return new Double(sec);
                case 2 : 
                    // Column 2 shows the thread state
                    return me.getValue().getThreadState();
                default: 
                    return null;
            }
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        void setThreadList(List<Map.Entry<Long, ThreadInfo>> list) {
            threadList = list;
        }
    }

    /**
     * Get the thread list with CPU consumption and the ThreadInfo
     * for each thread sorted by the CPU time.
     */
    private List<Map.Entry<Long, ThreadInfo>> getThreadList() {
        // Get all threads and their ThreadInfo objects 
        // with no stack trace
        long[] tids = tmbean.getAllThreadIds();
        ThreadInfo[] tinfos = tmbean.getThreadInfo(tids);

        // build a map with key = CPU time and value = ThreadInfo
        SortedMap<Long, ThreadInfo> map = new TreeMap<Long, ThreadInfo>();
        for (int i = 0; i < tids.length; i++) { 
            long cpuTime = tmbean.getThreadCpuTime(tids[i]);
            // filter out threads that have been terminated
            if (cpuTime != -1 && tinfos[i] != null) {
                map.put(new Long(cpuTime), tinfos[i]);
            }
        }

        // build the thread list and sort it with CPU time
        // in decreasing order
        Set<Map.Entry<Long, ThreadInfo>> set = map.entrySet();
        List<Map.Entry<Long, ThreadInfo>> list = 
            new ArrayList<Map.Entry<Long, ThreadInfo>>(set);
        Collections.reverse(list);
        return list;
    }


    /**
     * Format Double with 4 fraction digits
     */ 
    class DoubleRenderer extends DefaultTableCellRenderer {
        NumberFormat formatter;
        public DoubleRenderer() { 
            super();
            setHorizontalAlignment(JLabel.RIGHT);
        }
    
        public void setValue(Object value) {
            if (formatter==null) {
                formatter = NumberFormat.getInstance();
                formatter.setMinimumFractionDigits(4);
            }
            setText((value == null) ? "" : formatter.format(value));
        }
    }

    // SwingWorker responsible for updating the GUI
    // 
    // It first gets the thread and CPU usage information as a 
    // background task done by a worker thread so that
    // it will not block the event dispatcher thread.
    //
    // When the worker thread finishes, the event dispatcher
    // thread will invoke the done() method which will update
    // the UI.
    class Worker extends SwingWorker<List<Map.Entry<Long, ThreadInfo>>,Object> {
        private MyTableModel tmodel;
        Worker(MyTableModel tmodel) {
            this.tmodel = tmodel;
        }

        // Get the current thread info and CPU time
        public List<Map.Entry<Long, ThreadInfo>> doInBackground() {
            return getThreadList();
        }
                                                                                
        // fire table data changed to trigger GUI update
        // when doInBackground() is finished
        protected void done() {
            try {
                // Set table model with the new thread list 
                tmodel.setThreadList(get());
                // refresh the table model 
                tmodel.fireTableDataChanged();
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
            }
        }
    }

    // Return a new SwingWorker for UI update
    public SwingWorker<?,?> newSwingWorker() {
        return new Worker(tmodel);
    }

    public static void main(String[] args) throws Exception {
        // Validate the input arguments
        if (args.length != 1) {
            usage();
        }

        String[] arg2 = args[0].split(":");
        if (arg2.length != 2) {
            usage();
        }
        String hostname = arg2[0];
        int port = -1;
        try {
            port = Integer.parseInt(arg2[1]);
        } catch (NumberFormatException x) {
            usage();
        }
        if (port < 0) {
            usage();
        }

        // Create the JTop Panel
        final JTop jtop = new JTop();
        // Set up the MBeanServerConnection to the target VM
        MBeanServerConnection server = connect(hostname, port);
        jtop.setMBeanServerConnection(server);

        // A timer task to update GUI per each interval
	TimerTask timerTask = new TimerTask() {
            public void run() {
                // Schedule the SwingWorker to update the GUI
		jtop.newSwingWorker().execute();
            }
	};

        // Create the standalone window with JTop panel
        // by the event dispatcher thread
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                createAndShowGUI(jtop);
            }
        });

        // refresh every 2 seconds
	Timer timer = new Timer("JTop Sampling thread");
	timer.schedule(timerTask, 0, 2000);

    }

    // Establish a connection with the remote application
    //
    // You can modify the urlPath to the address of the JMX agent
    // of your application if it has a different URL.
    // 
    // You can also modify the following code to take 
    // username and password for client authentication.
    private static MBeanServerConnection connect(String hostname, int port) {
        // Create an RMI connector client and connect it to
        // the RMI connector server
        String urlPath = "/jndi/rmi://" + hostname + ":" + port + "/jmxrmi";
        MBeanServerConnection server = null;        
        try {
            JMXServiceURL url = new JMXServiceURL("rmi", "", 0, urlPath);
            JMXConnector jmxc = JMXConnectorFactory.connect(url);
            server = jmxc.getMBeanServerConnection();
        } catch (MalformedURLException e) {
            // should not reach here
        } catch (IOException e) {
            System.err.println("\nCommunication error: " + e.getMessage());
            System.exit(1);
        }
        return server;
    }

    private static void usage() {
        System.out.println("Usage: java JTop <hostname>:<port>");
        System.exit(1);
    }
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI(JPanel jtop) {
        // Create and set up the window.
        JFrame frame = new JFrame("JTop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        JComponent contentPane = (JComponent) frame.getContentPane();
        contentPane.add(jtop, BorderLayout.CENTER);
        contentPane.setOpaque(true); //content panes must be opaque
        contentPane.setBorder(new EmptyBorder(12, 12, 12, 12));
        frame.setContentPane(contentPane);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
