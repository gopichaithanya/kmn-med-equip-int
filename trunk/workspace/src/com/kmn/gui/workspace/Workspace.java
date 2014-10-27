/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.gui.workspace;

import com.kmn.MainApps;
import com.kmn.controller.InterfaceEvent;
import com.kmn.controller.UserSession;
import com.kmn.controller.props.EquipmentDetailProperties;
import com.kmn.controller.props.ServerProperties;
import com.kmn.util.CommInterface;
import com.kmn.util.DicomInterface;
import com.kmn.ws.ClientService;
import com.kmn.ws.bean.Patient;
import com.kmn.ws.bean.PatientInfo;
import com.kmn.ws.bean.StoreResultsResponse;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.awt.Image;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.SerialPort;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.joda.time.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author valeo
 */
public class Workspace extends javax.swing.JPanel implements InterfaceEvent {
    /*Message Strings*/
    public static final String MSG_LOADING_PATIENTS = "Loading Patients...";
    public static final String MSG_EMPTY = "";
    public static final String MSG_NOT_SUPPORTED = "Not supported yet.";
    public static final String MSG_ERROR = "[ERROR]: ";
    public static final String MSG_CAUSE = "\n[CAUSE]: ";
    public static final String MSG_SELECT_ROW = "Please select a data row.";
    public static final String MSG_INPUT_PATIENT = "Please input patient data.";
    public static final String MSG_NO_FILE = "Output result file does not exist.";
    public static final String MSG_SAVE_SUCCESS = "Save successful.\nResult: ";
    public static final String MSG_SAVE_FAILED = "Save failed.\nResult: ";
    public static final String MSG_ON_CLOSE = "Apakah anda yakin akan menutup proses penerimaan data?";
    /* Connection String Constants */
    public static final String TEMP_DIR = System.getProperty("user.dir") + System.getProperty("file.separator") + "tmp";
    public static final String OUTPUT_PDF = ".out/output.pdf";
    public static final String OUTPUT_JPG = ".out/output.jpg";
    public static final String OUTPUT_XML = ".out/output.xml";
    public static final String DICOM = "DICOM";
    public static final String LOCAL_IP = "127.0.0.1";
    public static final String AP_NAME = "DCMRCV";
    public static final String SDF = "E, dd MMM yyyy HH:mm:ss Z";
    public static final String SDF_DIR = "ddMMMyyyyHHmmss";
    /* Equipment Properties File Mapping - Flow Control */
    public static final String FLOW_NONE = "None";
    public static final String FLOW_XONXOFF = "Xon / Xoff";
    public static final String FLOW_XONXOFFOUT = "Xon / Xoff out";
    public static final String FLOW_HARDWARE = "Hardware";
    public static final String FLOW_RTSCTS_IN = "RTCS In";
    public static final String FLOW_RTSCTS_OUT = "RTCS Out";
    /* Equipment Properties File Mapping - Parity */
    public static final String PARITY_NONE = "None";
    public static final String PARITY_ODD = "Odd";
    public static final String PARITY_EVEN = "Even";
    public static final String PARITY_MARK = "Mark";
    public static final String PARITY_SPACE = "Space";
    /* Equipment Properties File Mapping - Stop Bits */
    public static final String STOPBITS_1 = "1";
    public static final String STOPBITS_1_5 = "1.5";
    public static final String STOPBITS_2 = "2";
    /* Filename extensions */
    public static String PDF_FILE_EXT = ".pdf";
    public static String JPG_FILE_EXT = ".jpg";
    public static String XML_FILE_EXT = ".xml";
    
    private Status statusBox;
    protected JTabbedPane owner;
    private EquipmentDetailProperties equip;
    private ModelInterface modelinterface;
    private DicomInterface dicomInterface;
    private CommInterface commInterface;
    private ClientService cs = new ClientService();
    private ServerProperties sp = new ServerProperties(null);

    private LookupPatients lookupPatients;                      
    //To select the path of image file
    private File file;
    
    /**
     * Creates new form Workspace
     */
    public Workspace() {
        initComponents();
    }
    public Workspace(JTabbedPane owner, EquipmentDetailProperties equip) {
        this.owner = owner;
        this.equip = equip;
        initComponents();
        jTable2.setFillsViewportHeight(true);
        this.setName(equip.getCode());
        receiveEquipmentData();
        this.jButton3.setVisible(false); //Lookup Patient button
        this.jButton4.setVisible(true); //Add File button
        this.jLabel2.setVisible(false); //Patient Name
        this.jTextField1.setVisible(false); //Patient Name
        this.jXDatePicker1.setFormats("dd/MM/yyyy");
        this.jXDatePicker1.setDate(new Date());
        this.sp.load();
        this.jComboBox1.setSelectedIndex(Integer.valueOf(this.sp.getDefaultClinic()));
        
    }   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();

        setPreferredSize(new java.awt.Dimension(800, 500));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/save.png"))); // NOI18N
        jButton1.setText("Save");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onSave(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/cancel.png"))); // NOI18N
        jButton2.setText("Close");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onClose(evt);
            }
        });

        jLabel1.setText("Loading...");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/login_user.png"))); // NOI18N
        jButton3.setText("Lookup Patient");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onLookupPatient(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/file.png"))); // NOI18N
        jButton4.setText("Add File");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onAddManual(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel1)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        jSplitPane1.setMinimumSize(new java.awt.Dimension(206, 100));
        jSplitPane1.setName(""); // NOI18N

        jScrollPane2.setName(""); // NOI18N

        jPanel3.setMinimumSize(new java.awt.Dimension(410, 450));
        jPanel3.setName(""); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(2, 2));
        jPanel3.setLayout(new java.awt.GridLayout(2, 1));

        jPanel1.setMinimumSize(new java.awt.Dimension(422, 225));

        jScrollPane1.setViewportView(outputLabel);
        outputLabel.getAccessibleContext().setAccessibleName("outputLabel");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel1);

        jScrollPane4.setPreferredSize(new java.awt.Dimension(452, 200));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Selected", "Value"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel4);

        jScrollPane2.setViewportView(jPanel3);

        jSplitPane1.setRightComponent(jScrollPane2);

        jPanel5.setPreferredSize(new java.awt.Dimension(370, 400));
        jPanel5.setRequestFocusEnabled(false);

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setAutoscrolls(true);
        jScrollPane3.setMaximumSize(new java.awt.Dimension(400, 150));
        jScrollPane3.setMinimumSize(new java.awt.Dimension(200, 150));
        jScrollPane3.setName(""); // NOI18N
        jScrollPane3.setPreferredSize(new java.awt.Dimension(400, 150));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Single ID", "Patient Name", "MR Number", "Timestamp", "Doctor ID", "Data Output", "PID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setMaximumSize(new java.awt.Dimension(400, 150));
        jTable2.setMinimumSize(new java.awt.Dimension(200, 150));
        jTable2.setPreferredSize(null);
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onSelect(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);
        jTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(200);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable2.getColumnModel().getColumn(4).setPreferredWidth(100);
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(300);
        }

        jScrollPane5.setMinimumSize(new java.awt.Dimension(200, 50));
        jScrollPane5.setName(""); // NOI18N
        jScrollPane5.setPreferredSize(new java.awt.Dimension(400, 50));
        jScrollPane5.setRequestFocusEnabled(false);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient ID", "Single ID", "Patient Name", "Patient BRM", "Doc ID", "Doc Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setMinimumSize(new java.awt.Dimension(200, 50));
        jTable3.setPreferredSize(new java.awt.Dimension(400, 50));
        jTable3.setRequestFocusEnabled(false);
        jTable3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(jTable3);

        jLabel2.setText("Name");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/menu/find.png"))); // NOI18N
        jButton5.setText("Search");
        jButton5.setPreferredSize(new java.awt.Dimension(93, 23));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/cancel.png"))); // NOI18N
        jButton6.setText("Cancel");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/ok.png"))); // NOI18N
        jButton7.setText("Select");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel3.setText("BRM");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Single ID");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel5.setText("Date");

        jLabel6.setText("Clinic");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "KMN", "KDN", "SMG" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(4, 4, 4)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 24, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7)))
        );

        jSplitPane1.setLeftComponent(jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void onSelect(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onSelect
        int row = jTable2.getSelectedRow();
        //JFrame mainFrame = MainApps.getApplication().getMainFrame();
        if (row > -1) {
            String filePath = (String) jTable2.getValueAt(row, 5);
            try {
                file = new File(filePath+OUTPUT_PDF);
                if(!file.exists()) {
                    file = new File(filePath+OUTPUT_JPG);
                }
                if(!file.exists()) {
                    file = new File(filePath+OUTPUT_XML);
                }
                if(!file.exists()) {
                    file = new File(filePath);
                    if(!file.exists()) {
                        JOptionPane.showMessageDialog(this, MSG_NO_FILE);
                    } else {
                        if(file.getName().contains(PDF_FILE_EXT)) {
                            renderPdf(file);
                        } else if (file.getName().contains(XML_FILE_EXT)) {
                            renderXml(file);
                        } else {
                            renderImage(file);
                        } 
                    }
                } else {
//                    ViewOutput vo = new ViewOutput(this, file);
//                    vo.setLocationRelativeTo(mainFrame);
                    if(file == null) {
                        //renderXml((String) wm.jTable1.getValueAt(wm.jTable1.getSelectedRow(), 5));
                    } else if(file.getName().contains(PDF_FILE_EXT)) {
                        renderPdf(file);
                    } else if (file.getName().contains(JPG_FILE_EXT)) {
                        renderImage(file);
                    } else if (file.getName().contains(XML_FILE_EXT)) {
                        renderXml(file);
                    }
                }
            } catch (NullPointerException ex) {   
                Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
            }catch (ParserConfigurationException ex) {
                Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, MSG_SELECT_ROW);
        }
    }//GEN-LAST:event_onSelect

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private PatientInfo patientInfo;
    private static final String MSG_MANDATORY = "Harap masukkan BRM, Single ID atau Tanggal.";
    private static final String DEFAULT_CLINIC_ID = "8";
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_ROW_PER_PAGE = 10;
    private String clinicId; 
    private int pageNumber; 
    private int rowPerPage;
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            // Search Patient
            DefaultTableModel model= (DefaultTableModel) jTable3.getModel();
            while(model.getRowCount()>0){
                model.removeRow(0);
            }
            //ClientService cs = new ClientService();
            if(jTextField2.getText().isEmpty() && jTextField3.getText().isEmpty() && jXDatePicker1.getDate()==null){
                JOptionPane.showMessageDialog(this, MSG_MANDATORY);
            } else {
                //String keyword = "%"+jTextField1.getText()+"%"+"#"+jTextField3.getText()+"#"+jTextField2.getText();SimpleDateFormat formatter;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String date = "";
                if (jXDatePicker1.getDate()!=null) { date = formatter.format(jXDatePicker1.getDate());}
                String keyword = "%"+jTextField1.getText()+"%"+"#"+jTextField3.getText()+"#"+jTextField2.getText()+"#"+date;
                //this.sp.load();
                //patientInfo = this.cs.retrievePatients(keyword, this.sp.getKdn(), this.pageNumber, this.rowPerPage);
                patientInfo = this.cs.retrievePatients(keyword, String.valueOf(jComboBox1.getSelectedIndex()), this.pageNumber, this.rowPerPage);
                for (Patient p : patientInfo.getPatients()) {
                    model.addRow(new Object[]{p.getPatientId(),p.getSingleId(), p.getPatientName(),
                        p.getPatientBrm(), p.getDocId(), p.getDocName()});
                }
            }
        } catch (SOAPException ex) {
            Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // Cancel Button
        jTextField2.setText("");
        jTextField3.setText("");
        jXDatePicker1.setDate(new Date());
        jComboBox1.setSelectedIndex(0);
        
        DefaultTableModel model= (DefaultTableModel) jTable3.getModel();
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        //this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Select Patient
        int row = jTable3.getSelectedRow();
        HashMap hm = new HashMap();
        hm.put(ClientService.TAG_PATIENTID, (String) jTable3.getValueAt(row, 0));
        hm.put(ClientService.TAG_SINGLEID, (String) jTable3.getValueAt(row, 1));
        hm.put(ClientService.TAG_PATIENTNAME, (String) jTable3.getValueAt(row, 2));
        hm.put(ClientService.TAG_PATIENTBRM, (String) jTable3.getValueAt(row, 3));
        hm.put(ClientService.TAG_DOCID, (String) jTable3.getValueAt(row, 4));
        hm.put(ClientService.TAG_DOCNAME, (String) jTable3.getValueAt(row, 5));
        
        int rowWm = this.jTable2.getSelectedRow();
        this.jTable2.setValueAt(hm.get(ClientService.TAG_PATIENTID), rowWm, 6);
        this.jTable2.setValueAt(hm.get(ClientService.TAG_SINGLEID), rowWm, 0);
        this.jTable2.setValueAt(hm.get(ClientService.TAG_PATIENTNAME), rowWm, 1);
        this.jTable2.setValueAt(hm.get(ClientService.TAG_PATIENTBRM), rowWm, 2);
        this.jTable2.setValueAt(hm.get(ClientService.TAG_DOCID), rowWm, 4);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        fileChooserActionPerformed(evt);
    }//GEN-LAST:event_jButton4ActionPerformed
    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {                                         
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileSelected = fileChooser.getSelectedFile();
            System.out.println("fileChooserActionPerformed: "+fileSelected.getAbsolutePath());
            if(fileSelected.exists()) {
                System.out.println("fileSelected.getName(): "+fileSelected.getName());
                if (statusBox == null) {
                    JFrame mainFrame = MainApps.getApplication().getMainFrame();
                    statusBox = new Status(this, true);
                    statusBox.setLocationRelativeTo(mainFrame);
                }
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat(SDF_DIR);
                DateTime trxDate = new DateTime(now);
                DatatypeFactory factory = null;
                try {
                    factory = DatatypeFactory.newInstance();
                } catch (DatatypeConfigurationException ex) {
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                String path = TEMP_DIR+"/"+sdf.format(now)+".out/"+fileSelected.getName();
                System.out.println("path: "+path);
                DefaultTableModel model= (DefaultTableModel) jTable2.getModel();
                try {
                    copyFile(fileSelected, new File(path));
                    model.addRow(new Object[]{null,null,null,trxDate,null,path,null});
                    System.out.println("File saved to: "+path);
                } catch (IOException ex) {
                    Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
                }
                while(!jLabel1.getText().isEmpty()) {
                    try {
                        synchronized (this) {
                            wait(1000);
                        }
                        jLabel1.setText(MSG_EMPTY);
                    } catch (InterruptedException e) {
                    }
                }
            }
        } else {
            System.out.println("File access cancelled by user.");
        }
    } 
    
    public static void copyFile(File sourceFile, File destFile) throws IOException {
//        if(!destFile.exists()) {
//            destFile.createNewFile();
//        }
        if(!destFile.exists()){
            destFile.getParentFile().mkdirs();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        }
        finally {
            if(source != null) {
                source.close();
            }
            if(destination != null) {
                destination.close();
            }
        }
    }
    private void onAddManual(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onAddManual
        //JOptionPane.showMessageDialog(this, "Not implemented yet!");
        JFileChooser chooser = new JFileChooser();
        chooser.setVisible(true);
        // Add listener on chooser to detect changes to selected file
        chooser.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY
                    .equals(evt.getPropertyName())) {
                    JFileChooser chooser = (JFileChooser)evt.getSource();
                    File oldFile = (File)evt.getOldValue();
                    File newFile = (File)evt.getNewValue();
                    // The selected file should always be the same as newFile
                    File curFile = chooser.getSelectedFile();
                } else if (JFileChooser.SELECTED_FILES_CHANGED_PROPERTY.equals(
                    evt.getPropertyName())) {
                        JFileChooser chooser = (JFileChooser)evt.getSource();
                        File[] oldFiles = (File[])evt.getOldValue();
                        File[] newFiles = (File[])evt.getNewValue();
                        // Get list of selected files
                        // The selected files should always be the same as newFiles
                        File[] files = chooser.getSelectedFiles();
            }
        }

    //            public void propertyChange(PropertyChangeEvent evt) {
    //                throw new UnsupportedOperationException("Not supported yet.");
    //            }
        }) ;
    }//GEN-LAST:event_onAddManual

    private void onLookupPatient(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onLookupPatient
        // Lookup Patient
        int row = jTable2.getSelectedRow();
        if (row > -1) {
            try {
                if(lookupPatients == null) {
                    JFrame mainFrame = MainApps.getApplication().getMainFrame();
                    this.onMessage(MSG_LOADING_PATIENTS);
                    lookupPatients = new LookupPatients(this);
                    lookupPatients.setLocationRelativeTo(mainFrame);
                    lookupPatients.setResizable(false);
                }
                MainApps.getApplication().show(lookupPatients);
                //lookupPatients.setVisible(true);
            } catch (SOAPException ex) {
                String msg = MSG_ERROR + ex.getMessage() +
                MSG_CAUSE + ex.getCause().getCause().getCause().getMessage();
                JOptionPane.showMessageDialog(this, msg);
                Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                Logger.getLogger(LookupPatients.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                this.onMessage(MSG_EMPTY);
            }
        } else {
            JOptionPane.showMessageDialog(this, MSG_SELECT_ROW);
        }
    }//GEN-LAST:event_onLookupPatient

    private void onClose(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onClose
        int result = JOptionPane.showConfirmDialog(this, MSG_ON_CLOSE);
        // Yes = 0; No = 1; Cancel = 2
        if (result == 0) {
            //if (this.modelinterface != null) {this.modelinterface.close();}
            if (this.dicomInterface != null) {this.dicomInterface.close();}
            //if (this.commInterface != null) {this.commInterface.close();}
            if (this.modelinterface != null) {this.modelinterface.close();}
            int index = this.owner.getSelectedIndex();
            this.owner.remove(index);
        }
    }//GEN-LAST:event_onClose

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void onSave(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onSave
        int row = jTable2.getSelectedRow();
        if (row > -1) {
            //patient id used for transaction key for web service between webadmin and cis.
            String patientId = (String) jTable2.getValueAt(row, 6);
            if (patientId == null || patientId.isEmpty()){
                JOptionPane.showMessageDialog(this, MSG_INPUT_PATIENT);
            } else {
                this.cs = new ClientService();
                try {
                    String filePath = (String) jTable2.getValueAt(row, 5);
                    String branchId = "CIS_CLINIC_ID";
                    //Single id used for storing directory structure in web admin
                    String patientCode = (String) jTable2.getValueAt(row, 0);
                    String patientName = (String) jTable2.getValueAt(row, 1);
                    DateTime dt = (DateTime) jTable2.getValueAt(row, 3);
                    String remark = cs.convertDateTimeToString(dt);
                    int equipmentId = Integer.valueOf(this.equip.getCode());
                    int imageId = 0;
                    DateTime trxDate = dt;
                    DateTime timeStamp = trxDate;
                    String dataLocation = filePath;
                    String creatorId = "";
                    if (UserSession.getInstance().getSecuser() != null) {
                        creatorId = UserSession.getInstance().getSecuser().getUsrLoginname();
                    }
                    File dataOutput = null;
                    String xmlData;
                    if (filePath.contains(OUTPUT_XML)) {
                        //autoreff
                        xmlData = cs.getParsedStringFromXmlFile(filePath);
                        cs.createXml(xmlData, filePath);
                        dataOutput = new File(filePath);
                    } else {
                        //dicom File
                        dataOutput = new File(filePath+OUTPUT_PDF);
                        dataLocation = filePath+OUTPUT_PDF;
                        if(!dataOutput.exists()) {
                            dataOutput = new File(filePath+OUTPUT_JPG);
                            dataLocation = filePath+OUTPUT_JPG;
                            if(!dataOutput.exists()) {
                                dataOutput = new File(filePath);
                                dataLocation = filePath;
                            }
                        }
                        //xmlData = cs.getStringFromXmlFile(filePath+OUTPUT_XML);
                        xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><dicom><attr>EMPTY</attr></dicom>";
                        //xmlData = "0";
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String date = "";
                    if (jXDatePicker1.getDate()!=null) { date = formatter.format(jXDatePicker1.getDate());}
                    StoreResultsResponse srr = cs.storeResults(branchId, patientId+"#"+date, patientCode, patientName, remark, equipmentId, imageId, trxDate, timeStamp
                        , dataLocation, dataOutput, xmlData, creatorId);
                    if(srr.isSuccess()) {
                        JOptionPane.showMessageDialog(this, MSG_SAVE_SUCCESS + srr.getResult());
                        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                        model.removeRow(row);
                        jButton6ActionPerformed(null);
                        removeImage();
                    } else {
                        JOptionPane.showMessageDialog(this, MSG_SAVE_FAILED + srr.getResult());
                    }
                } catch (SOAPException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DatatypeConfigurationException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParserConfigurationException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TransformerConfigurationException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TransformerException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                    Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, MSG_SELECT_ROW);
        }
    }//GEN-LAST:event_onSave

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jXDatePicker1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    protected javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    protected javax.swing.JTextField jTextField1;
    protected javax.swing.JTextField jTextField2;
    protected javax.swing.JTextField jTextField3;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JLabel outputLabel;
    // End of variables declaration//GEN-END:variables
    //@Override
    public void onSend() {
        throw new UnsupportedOperationException(MSG_NOT_SUPPORTED);
    }

    //@Override
    public void onReceive(String message) {
        if(!message.isEmpty()) {
            if (statusBox == null) {
                JFrame mainFrame = MainApps.getApplication().getMainFrame();
                statusBox = new Status(this, true);
                statusBox.setLocationRelativeTo(mainFrame);
            }
            //MainApps.getApplication().show(statusBox);
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(SDF_DIR);
            DateTime trxDate = new DateTime(now);
            DatatypeFactory factory = null;
            try {
                factory = DatatypeFactory.newInstance();
            } catch (DatatypeConfigurationException ex) {
                Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            String trxDateStr = factory.newXMLGregorianCalendar(trxDate.toGregorianCalendar()).toXMLFormat();
            String path = TEMP_DIR+"/"+sdf.format(now)+OUTPUT_XML;
            ClientService cs = new ClientService();
            DefaultTableModel model= (DefaultTableModel) jTable2.getModel();
            if(cs.convertMessageToXml(MSG_EMPTY, message, path, equip.getCode())!=null) {
                if(message.contains(TEMP_DIR)) {
                    model.addRow(new Object[]{null,null,null,trxDate,null,message,null});
                } else {
                    model.addRow(new Object[]{null,null,null,trxDate,null,path,null});
                }
                while(!jLabel1.getText().isEmpty()) {
                    try {
                        synchronized (this) {
                            wait(1000);
                        }
                        jLabel1.setText(MSG_EMPTY);
                    } catch (InterruptedException e) {
                    }
                }
                //statusBox.setVisible(false);
            }
        }
    }

    //@Override
    public void onError(Throwable t) {
        throw new UnsupportedOperationException(MSG_NOT_SUPPORTED);
    }

    //@Override
    public void onMessage(String message) {
        while(jLabel1 == null) {
            try {
                synchronized (this) {
                    wait(1000);
                }
            } catch (InterruptedException e) {
            }
        }
        jLabel1.setText(message);
    }

    private void receiveEquipmentData() {
        try {
            if (equip.getInterfaceType().equalsIgnoreCase(DICOM)) {
                this.modelinterface = new DicomInterface(this, LOCAL_IP, Integer.valueOf(equip.getPort()), AP_NAME, TEMP_DIR, 0);
             } else {
                this.modelinterface = new CommInterface(this, equip.getCom(), Integer.valueOf(equip.getRate())
                        , Integer.valueOf(equip.getDataBit()), getStopBitInt(equip.getStopBit())
                        , getParityInt(equip.getParity()),
                        getFlowInt(equip.getFlow()));
//                this.modelinterface = new NRinterface(this, equip.getCom(), Integer.valueOf(equip.getRate()));
//                this.modelinterface.getOutputString();
            }
            this.modelinterface.connect();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Failed to connect on port: " + equip.getCom());
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }
    
    public Integer getStopBitInt(String string) {
        int result = 1;
        if(string.equalsIgnoreCase(STOPBITS_2)) {
            result = SerialPort.STOPBITS_2;
        } else if(string.equalsIgnoreCase(STOPBITS_1_5)) {
            result = SerialPort.STOPBITS_1_5;
        } else if(string.equalsIgnoreCase(STOPBITS_1)) {
            result = SerialPort.STOPBITS_1;
        }
        return result;
    }
    
    public Integer getFlowInt(String string) {
        int result = SerialPort.FLOWCONTROL_NONE;
        if(string.equalsIgnoreCase(FLOW_NONE)) {
            result = SerialPort.FLOWCONTROL_NONE;
        } else if(string.equalsIgnoreCase(FLOW_XONXOFF)) {
            result = SerialPort.FLOWCONTROL_XONXOFF_IN;
        } else if(string.equalsIgnoreCase(FLOW_HARDWARE)) {
            result = SerialPort.FLOWCONTROL_RTSCTS_IN;
        } else if(string.equalsIgnoreCase(FLOW_RTSCTS_IN)) { //not yet implemented
            result = SerialPort.FLOWCONTROL_RTSCTS_IN;
        } else if(string.equalsIgnoreCase(FLOW_RTSCTS_OUT)) { //not yet implemented
            result = SerialPort.FLOWCONTROL_RTSCTS_OUT;
        } else if(string.equalsIgnoreCase(FLOW_XONXOFFOUT)) { //not yet implemented
            result = SerialPort.FLOWCONTROL_XONXOFF_OUT;
        }
        return result;
    }
    
    public Integer getParityInt(String string) {
        int result = SerialPort.PARITY_NONE;
        if(string.equalsIgnoreCase(PARITY_NONE)) {
            result = SerialPort.PARITY_NONE;
        } else if(string.equalsIgnoreCase(PARITY_ODD)) {
            result = SerialPort.PARITY_ODD;
        } else if(string.equalsIgnoreCase(PARITY_EVEN)) {
            result = SerialPort.PARITY_EVEN;
        } else if(string.equalsIgnoreCase(PARITY_MARK)) {
            result = SerialPort.PARITY_MARK;
        } else if(string.equalsIgnoreCase(PARITY_SPACE)) {
            result = SerialPort.PARITY_SPACE;
        } 
        return result;
    }
    
    private void renderXml(File file) throws ParserConfigurationException, SAXException, IOException {
        ClientService cs = new ClientService();
        Document doc= cs.readXml(file.getAbsolutePath());
        List<Object[]> tableContents = new ArrayList<Object[]>();
        DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
        while(model.getRowCount()>0){
            model.removeRow(0);
        }
        //NodeList rootNode = this.doc.getElementsByTagName(ClientService.TAG_COMM);
        jTable1.setDefaultRenderer(Object.class, new MyTableCellRenderer());  
        NodeList n1 = doc.getElementsByTagName(ClientService.TAG_ATTR);
        String data1, data2, data3;
        for (int i = 0; i < n1.getLength(); i++) {
            data1 = cs.getStringNodeValue(n1.item(i).getAttributes().getNamedItem(ClientService.ATTR_NAME));
            data2 = cs.getStringNodeValue(n1.item(i).getAttributes().getNamedItem(ClientService.ATTR_SELECTED));
            data3 = cs.getStringNodeValue(n1.item(i));
            model.addRow(new Object[]{data1,data2,data3});
            tableContents.add(new Object[]{data1,data2,data3});
        }
        this.setVisible(true);
    }
    private void renderImage(File file) {
        ImageIcon gbr = new ImageIcon(file.getAbsolutePath());
        outputLabel.setIcon(gbr);
        this.setVisible(true);
    }
    
    private void removeImage() {
        outputLabel.setIcon(null);
        this.setVisible(false);
    }
    private void renderPdf(File file) {
        try {
            renderPdfAsImage(file);
        } catch (FileNotFoundException e) {
            Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(WorkspaceModel.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void renderPdfAsImage(File file) throws FileNotFoundException, IOException {
        //load a pdf from a byte buffer
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdffile = new PDFFile(buf);
        // draw the first page to an image
        PDFPage page = pdffile.getPage(0);
        //get the width and height for the doc at the default zoom 
        Rectangle rect = new Rectangle(0,0,
                (int)page.getBBox().getWidth(),
                (int)page.getBBox().getHeight());
        
        //generate the image
        Image img = page.getImage(
                rect.width, rect.height, //width & height
                rect, // clip rect
                null, // null for the ImageObserver
                true, // fill background with white
                true  // block until drawing is done
                );
        outputLabel.setIcon(new ImageIcon(img));
        //this.pack();
        this.setVisible(true);
    }
}
