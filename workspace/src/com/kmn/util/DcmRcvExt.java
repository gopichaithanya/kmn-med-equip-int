/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kmn.util;

import com.kmn.controller.InterfaceEvent;
import java.io.File;
import java.io.IOException;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.net.Association;
import org.dcm4che2.net.PDVInputStream;
import org.dcm4che2.tool.dcmrcv.DcmRcv;

/**
 *
 * @author valeo
 */
public class DcmRcvExt extends DcmRcv {
    public static final String FS = "/";
    public static final String DIR_SUFFIX = ".out";
    public static final String FILE_XML = "/output.xml";
    public static final String FILE_JPG = "/output.jpg";
    public static final String MSG_XML_CONV = "Start XML Conversion";
    public static final String MSG_JPG_CONV = "Start Jpeg Conversion";
    public static final String MSG_NO_PIXEL = "Dicom file does not contain pixel data";
    
    private String destinationExt;
    private InterfaceEvent event;

    public String getDestinationExt() {
        return destinationExt;
    }

    public void setDestinationExt(String destinationExt) {
        this.destinationExt = destinationExt;
    }
    
    public DcmRcvExt(String name) {
        super(name);
    }
    
    public DcmRcvExt(String name, InterfaceEvent event) {
        super(name);
        this.event = event;
    }

    @Override
    public void onNActionRQ(Association as, DicomObject rq, DicomObject info) {
        super.onNActionRQ(as, rq, info);
    }

    @Override
    public void onCStoreRQ(Association as, int pcid, DicomObject rq, PDVInputStream dataStream, String tsuid, DicomObject rsp) throws IOException {
        //filename
        String iuid = rq.getString(Tag.AffectedSOPInstanceUID);
        super.onCStoreRQ(as, pcid, rq, dataStream, tsuid, rsp);
        //convert to xml
        String iPath = getDestinationExt() + FS + iuid;
        String subDir = getDestinationExt() + FS + iuid + DIR_SUFFIX;
        String oPath = subDir + FILE_XML;
        String jpgPath = subDir + FILE_JPG;
        System.out.println(MSG_XML_CONV);
        this.event.onMessage(MSG_XML_CONV);
        Dcm2XmlExt dcm2XmlExt = new Dcm2XmlExt(iPath, oPath);
        //convert to jpg
        try {
            System.out.println(MSG_JPG_CONV);
            this.event.onMessage(MSG_JPG_CONV);
            Dcm2JpgExt dcm2JpgExt = new Dcm2JpgExt(iPath, jpgPath);
        } catch (NullPointerException e) {
            System.out.println(MSG_NO_PIXEL);
            this.event.onMessage(MSG_NO_PIXEL);
        } finally {
            this.event.onReceive(getDestinationExt()+FS+iuid);
        }
    }
}
