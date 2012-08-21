
package id.co.kmn.services.wsdl.server.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Patient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Patient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patientId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="singleId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientBrm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="docName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Patient", namespace = "http://localhost:9090/kmn/schemas/types", propOrder = {
    "patientId",
    "singleId",
    "patientName",
    "patientBrm",
    "docId",
    "docName"
})
public class Patient {

    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/types", required = true)
    protected String patientId;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/types", required = true)
    protected String singleId;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/types", required = true)
    protected String patientName;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/types", required = true)
    protected String patientBrm;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/types", required = true)
    protected String docId;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/types", required = true)
    protected String docName;

    /**
     * Gets the value of the patientId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Sets the value of the patientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientId(String value) {
        this.patientId = value;
    }

    /**
     * Gets the value of the singleId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSingleId() {
        return singleId;
    }

    /**
     * Sets the value of the singleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSingleId(String value) {
        this.singleId = value;
    }

    /**
     * Gets the value of the patientName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * Sets the value of the patientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientName(String value) {
        this.patientName = value;
    }

    /**
     * Gets the value of the patientBrm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientBrm() {
        return patientBrm;
    }

    /**
     * Sets the value of the patientBrm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientBrm(String value) {
        this.patientBrm = value;
    }

    /**
     * Gets the value of the docId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocId() {
        return docId;
    }

    /**
     * Sets the value of the docId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocId(String value) {
        this.docId = value;
    }

    /**
     * Gets the value of the docName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocName() {
        return docName;
    }

    /**
     * Sets the value of the docName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocName(String value) {
        this.docName = value;
    }

}
