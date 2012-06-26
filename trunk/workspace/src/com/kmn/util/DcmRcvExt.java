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
        String iPath = getDestinationExt()+"\\"+iuid;
        String oPath = getDestinationExt()+"\\"+iuid+".xml";
        String jpgPath = getDestinationExt()+"\\"+iuid+".jpg";
        System.out.println("Start Xml Conversion");
        Dcm2XmlExt dcm2XmlExt = new Dcm2XmlExt(iPath, oPath);
        //convert to jpg
        System.out.println("Start Jpeg Conversion");
        Dcm2JpgExt dcm2JpgExt = new Dcm2JpgExt(iPath, jpgPath);
        this.event.onReceive(getDestinationExt()+"\\"+iuid);
    }
}
