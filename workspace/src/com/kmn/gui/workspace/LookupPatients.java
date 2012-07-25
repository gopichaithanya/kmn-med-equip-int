/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.gui.workspace;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author valeo
 */
public class LookupPatients extends javax.swing.JFrame {
    public static String RESPONSE = "<SOAP-ENV:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" ><SOAP-ENV:Body><getPatientListResponse xmlns=\"http://192.168.13.10:2221/apps/kmn/IntegrasiAlat/\">"
    +"<resPageNumber>1</resPageNumber>"
    +"<resRowThisPage>2</resRowThisPage>"
    +"<resRowPerPage>2</resRowPerPage>"
    +"<resRowTotal>13</resRowTotal>"
    +"<resRowsXML>&lt;PATIENTINFO&gt;&lt;PATIENTID&gt;500488231&lt;/PATIENTID&gt;&lt;PATIENTNAME&gt;STEPHANUS BUDI RAHARDJO&lt;/PATIENTNAME&gt;&lt;PATIENTBRM&gt;&lt;/PATIENTBRM&gt;&lt;DOCID&gt;100094871&lt;/DOCID&gt;&lt;DOCNAME&gt;dr. HADI PRAKOSO, SpM&lt;/DOCNAME&gt;&lt;/PATIENTINFO&gt;&lt;PATIENTINFO&gt;&lt;PATIENTID&gt;500490641&lt;/PATIENTID&gt;&lt;PATIENTNAME&gt;BUDIYANTI BENYAMIN&lt;/PATIENTNAME&gt;&lt;PATIENTBRM&gt;&lt;/PATIENTBRM&gt;&lt;DOCID&gt;140&lt;/DOCID&gt;&lt;DOCNAME&gt;dr. HENRY WAROUW, SpM&lt;/DOCNAME&gt;&lt;/PATIENTINFO&gt;</resRowsXML>"
    +"</getPatientListResponse>"
    +"</SOAP-ENV:Body>"
    +"</SOAP-ENV:Envelope>";
    public static String RES_ROWS_XML = "&lt;PATIENTINFO&gt;&lt;PATIENTID&gt;500488231&lt;/PATIENTID&gt;&lt;PATIENTNAME&gt;STEPHANUS BUDI RAHARDJO&lt;/PATIENTNAME&gt;&lt;PATIENTBRM&gt;&lt;/PATIENTBRM&gt;&lt;DOCID&gt;100094871&lt;/DOCID&gt;&lt;DOCNAME&gt;dr. HADI PRAKOSO, SpM&lt;/DOCNAME&gt;&lt;/PATIENTINFO&gt;&lt;PATIENTINFO&gt;&lt;PATIENTID&gt;500490641&lt;/PATIENTID&gt;&lt;PATIENTNAME&gt;BUDIYANTI BENYAMIN&lt;/PATIENTNAME&gt;&lt;PATIENTBRM&gt;&lt;/PATIENTBRM&gt;&lt;DOCID&gt;140&lt;/DOCID&gt;&lt;DOCNAME&gt;dr. HENRY WAROUW, SpM&lt;/DOCNAME&gt;&lt;/PATIENTINFO&gt;";
    public static String FORMATED_RES_ROWS_XML = "<PATIENTINFO><PATIENTID>500488231</PATIENTID><PATIENTNAME>STEPHANUS BUDI RAHARDJO</PATIENTNAME><PATIENTBRM></PATIENTBRM><DOCID>100094871</DOCID><DOCNAME>dr. HADI PRAKOSO, SpM</DOCNAME></PATIENTINFO><PATIENTINFO><PATIENTID>500490641</PATIENTID><PATIENTNAME>BUDIYANTI BENYAMIN</PATIENTNAME><PATIENTBRM></PATIENTBRM><DOCID>140</DOCID><DOCNAME>dr. HENRY WAROUW, SpM</DOCNAME></PATIENTINFO>";
    
    public static String TAG_RESROWSXML = "resRowsXML";
    public static String TAG_PATIENTINFO = "PATIENTINFO";
    public static String TAG_PATIENTID = "PATIENTID";
    public static String TAG_PATIENTNAME = "PATIENTNAME";
    public static String TAG_PATIENTBRM = "PATIENTBRM";
    public static String TAG_DOCID = "DOCID";
    public static String TAG_DOCNAME = "DOCNAME";
    public static String TEMP_XML_PATH = "C:\\kmntmp\\tempXml.xml";
    public static String TEMP_XML_DATA_PATH = "C:\\kmntmp\\tempDataXml.xml";
    
    private WorkspaceModel wm;
    /**
     * Creates new form LookupPatients
     */
    public LookupPatients() {
        initComponents();
        retrievePatients();
    }
    
    public LookupPatients(WorkspaceModel wm) {
        this.wm = wm;
        this.setTitle(wm.owner.getTitleAt(wm.owner.getSelectedIndex()));
        initComponents();
        retrievePatients();
    }
    
    private void retrievePatients() {
        //fetch from web service
        //handle response  
        try {
            //Create temp XML
            createXml(RESPONSE, TEMP_XML_PATH); 
            //Read temp XML
            Document tempDoc = readXml(TEMP_XML_PATH);
            String resRowsXML = tempDoc.getElementsByTagName(TAG_RESROWSXML).item(0).getFirstChild().getNodeValue();
            //Create temp data XML
            StringBuilder sb = new StringBuilder("<root>");
            sb.append(resRowsXML);
            sb.append("</root>");
            createXml(sb.toString(), TEMP_XML_DATA_PATH);
            //Read temp data XML
            Document doc = readXml(TEMP_XML_DATA_PATH);
            NodeList patientInfo = doc.getElementsByTagName(TAG_PATIENTINFO);
            NodeList n1 = doc.getElementsByTagName(TAG_PATIENTID);
            NodeList n2 = doc.getElementsByTagName(TAG_PATIENTNAME);
            NodeList n3 = doc.getElementsByTagName(TAG_PATIENTBRM);
            NodeList n4 = doc.getElementsByTagName(TAG_DOCID);
            NodeList n5 = doc.getElementsByTagName(TAG_DOCNAME);
            String data1 = "", data2 = "", data3 = "", data4 = "", data5 = "";
//            Vector data = new Vector();
//            Vector columns = new Vector();
            for (int i = 0; i < patientInfo.getLength(); i++) {
                data1 = getStringNodeValue(n1.item(i));
                data2 = getStringNodeValue(n2.item(i));
                data3 = getStringNodeValue(n3.item(i));
                data4 = getStringNodeValue(n4.item(i));
                data5 = getStringNodeValue(n5.item(i));
                String line = data1 + " " + data2 + " " + data3+ " " + data4+ " " + data5;
                System.out.println(line);
                DefaultTableModel model= (DefaultTableModel) jTable1.getModel();
                model.addRow(new Object[]{data1,data2,data3,data4,data5});
//                StringTokenizer st2 = new StringTokenizer(line, " ");
//                while (st2.hasMoreTokens()) {
//                        data.addElement(st2.nextToken());
//                }
            }
//            columns.add("");
//            columns.add("");
//            columns.add("");
//            columns.add("");
//            columns.add("");
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
    
    public String getStringNodeValue(Node node) {
        String result = "";
        if(node.hasChildNodes())
            result = node.getFirstChild().getNodeValue();
        return result;
    }
    
    public void createXml(String xmlString, String absolutePath) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlString)));  
        TransformerFactory tranFactory = TransformerFactory.newInstance();  
        Transformer aTransformer = tranFactory.newTransformer();
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Source src = new DOMSource( doc );  
        Result dest = new StreamResult( new File(absolutePath));  
        aTransformer.transform( src, dest ); 
    }
    
    public Document readXml(String absolutePath) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();  
        DocumentBuilder db =dbf.newDocumentBuilder();  
        return (Document) db.parse(absolutePath);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 350));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient ID", "Patient Name", "Patient BRM", "Doc ID", "Doc Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setPreferredSize(new java.awt.Dimension(800, 200));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jLabel1.setText("Patient Name");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/menu/find.png"))); // NOI18N
        jButton1.setText("Search");
        jButton1.setPreferredSize(new java.awt.Dimension(93, 23));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/cancel.png"))); // NOI18N
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/kmn/resources/images/ok.png"))); // NOI18N
        jButton3.setText("Select");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 103, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Search Patient
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Cancel Button
        this.dispose();
        //this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Select Patient
        int row = jTable1.getSelectedRow();
        HashMap hm = new HashMap();
        hm.put(TAG_PATIENTID, (String) jTable1.getValueAt(row, 0));
        hm.put(TAG_PATIENTNAME, (String) jTable1.getValueAt(row, 1));
        hm.put(TAG_PATIENTBRM, (String) jTable1.getValueAt(row, 2));
        hm.put(TAG_DOCID, (String) jTable1.getValueAt(row, 3));
        hm.put(TAG_DOCNAME, (String) jTable1.getValueAt(row, 4));
        int rowWm = this.wm.jTable1.getSelectedRow();
        this.wm.jTable1.setValueAt(hm.get(TAG_PATIENTID), rowWm, 0);
        this.wm.jTable1.setValueAt(hm.get(TAG_PATIENTNAME), rowWm, 1);
        this.wm.jTable1.setValueAt(hm.get(TAG_PATIENTBRM), rowWm, 2);
        this.wm.jTable1.setValueAt(hm.get(TAG_DOCID), rowWm, 4);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
