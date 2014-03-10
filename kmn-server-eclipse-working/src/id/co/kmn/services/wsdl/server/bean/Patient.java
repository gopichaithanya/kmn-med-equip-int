package id.co.kmn.services.wsdl.server.bean;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/26/12
 * Time: 11:53 AM
 */
public class Patient {
    public static final String TAG_PATIENTINFO = "PATIENTINFO";
    public static final String TAG_PATIENTID = "PATIENTID";
    public static final String TAG_SINGLEID = "SINGLEID";
    public static final String TAG_PATIENTNAME = "PATIENTNAME";
    public static final String TAG_PATIENTBRM = "PATIENTBRM";
    public static final String TAG_DOCID = "DOCID";
    public static final String TAG_DOCNAME = "DOCNAME";
    private String patientId;

    private String singleId;
    private String patientName;
    private String patientBrm;
    private String docId;
    private String docName;

    public Patient() {}

    public Patient(String patientId, String patientName, String patientBrm, String docId, String docName) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientBrm = patientBrm;
        this.docId = docId;
        this.docName = docName;
    }

    public Patient(String patientId, String singleId, String patientName, String patientBrm, String docId, String docName) {
        this.patientId = patientId;
        this.singleId = singleId;
        this.patientName = patientName;
        this.patientBrm = patientBrm;
        this.docId = docId;
        this.docName = docName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSingleId() {
        return singleId;
    }

    public void setSingleId(String singleId) {
        this.singleId = singleId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientBrm() {
        return patientBrm;
    }

    public void setPatientBrm(String patientBrm) {
        this.patientBrm = patientBrm;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}
