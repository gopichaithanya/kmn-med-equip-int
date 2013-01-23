/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.gui.workspace;

import com.kmn.MainApps;
import com.kmn.controller.InterfaceEvent;
import com.kmn.controller.UserSession;
import com.kmn.controller.props.EquipmentDetailProperties;
import com.kmn.util.CommInterface;
import com.kmn.util.DicomInterface;
import com.kmn.ws.ClientService;
import com.kmn.ws.bean.StoreResultsResponse;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.awt.Image;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private ClientService cs;

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
        this.setName(equip.getCode());
        receiveEquipmentData();
    }   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

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
        jButton4.setText("Add Manual");
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
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
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

        jScrollPane2.setMinimumSize(new java.awt.Dimension(200, 200));
        jScrollPane2.setName(""); // NOI18N
        jScrollPane2.setPreferredSize(new java.awt.Dimension(400, 400));

        jScrollPane1.setViewportView(outputLabel);
        outputLabel.getAccessibleContext().setAccessibleName("outputLabel");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        );

        jPanel1.setBounds(0, 0, 590, 210);
        jLayeredPane1.add(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBounds(0, 260, 590, 200);
        jLayeredPane1.add(jPanel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 111, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel3);

        jSplitPane1.setRightComponent(jScrollPane2);

        jScrollPane3.setMinimumSize(new java.awt.Dimension(200, 200));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(400, 400));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Single ID", "Patient Name", "MR Number", "Timestamp", "Doctor ID", "Data Output"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setColumnSelectionAllowed(true);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onSelect(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);
        jTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(300);

        jSplitPane1.setLeftComponent(jScrollPane3);

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
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void onAddManual(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onAddManual
        //JOptionPane.showMessageDialog(this, "Not implemented yet!");
        JFileChooser chooser = new JFileChooser();

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

    private void onSave(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onSave
        int row = jTable2.getSelectedRow();
        if (row > -1) {
            String patientId = (String) jTable2.getValueAt(row, 0);
            if (patientId == null || patientId.isEmpty()){
                JOptionPane.showMessageDialog(this, MSG_INPUT_PATIENT);
            } else {
                this.cs = new ClientService();
                try {
                    String filePath = (String) jTable2.getValueAt(row, 5);
                    String branchId = "CIS_CLINIC_ID";
                    String patientCode = (String) jTable2.getValueAt(row, 2); 
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
                        //dataOutput = new File(filePath);
                        //xmlData = cs.getStringFromXmlFile(filePath);
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
                        }
                        //xmlData = cs.getStringFromXmlFile(filePath+OUTPUT_XML);
                        xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><dicom><attr>EMPTY</attr></dicom>";
                        //xmlData = "0";
                    }
                    StoreResultsResponse srr = cs.storeResults(branchId, patientId, patientCode, patientName, remark, equipmentId, imageId, trxDate, timeStamp
                            , dataLocation, dataOutput, xmlData, creatorId);
                    if(srr.isSuccess()) {
                        JOptionPane.showMessageDialog(this, MSG_SAVE_SUCCESS + srr.getResult());
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        model.removeRow(row);
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

    private void onClose(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onClose
        int result = JOptionPane.showConfirmDialog(this, MSG_ON_CLOSE);
        // Yes = 0; No = 1; Cancel = 2
        if (result == 0) {
            //if (this.modelinterface != null) {this.modelinterface.close();}
            if (this.dicomInterface != null) {this.dicomInterface.close();}
            if (this.commInterface != null) {this.commInterface.close();}
            int index = this.owner.getSelectedIndex();
            this.owner.remove(index);
        }
    }//GEN-LAST:event_onClose

    private void onSelect(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onSelect
        int row = jTable2.getSelectedRow();
        JFrame mainFrame = MainApps.getApplication().getMainFrame();
        if (row > -1) {
            String filePath = (String) jTable2.getValueAt(row, 5);
            try {
                file = new File(filePath+OUTPUT_PDF);
                if(!file.exists()) {
                    file = new File(filePath+OUTPUT_JPG);
                }
                if(!file.exists()) {
                    file = new File(filePath);
                    if(!file.exists()) {
                        JOptionPane.showMessageDialog(this, MSG_NO_FILE);
                    } else {
//                        XmlViewer xv = new XmlViewer(this, file);
//                        xv.setLocationRelativeTo(mainFrame);
                        renderXml(file);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    protected javax.swing.JTable jTable2;
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
            DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
            if(cs.convertMessageToXml(MSG_EMPTY, message, path, equip.getCode())!=null) {
                //cs.convertMessageToXml(MSG_EMPTY, message, path);
                if(message.contains(TEMP_DIR)) {
                    model.addRow(new Object[]{null,null,null,trxDate,null,message});
                } else {
                    model.addRow(new Object[]{null,null,null,trxDate,null,path});
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
                this.modelinterface = new DicomInterface(this, LOCAL_IP, Integer.valueOf(equip.getPort()), AP_NAME, TEMP_DIR, 4);
             } else {
                this.modelinterface = new CommInterface(this, equip.getCom(), Integer.valueOf(equip.getRate())
                        , Integer.valueOf(equip.getDataBit()), getStopBitInt(equip.getStopBit())
                        , getParityInt(equip.getParity()),
                        getFlowInt(equip.getFlow()));
//                this.modelinterface = new NRinterface(this, equip.getCom(), Integer.valueOf(equip.getRate()));
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
    
    private void renderPdf(File file) {
        try {
            renderPdfAsImage(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
