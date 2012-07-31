
package id.co.kmn.services.wsdl.server.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="branchId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="equipmentId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="imageId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="trxDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="timeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataLocation" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="dataOutput" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="xmlData" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="creatorId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "StoreResultsRequest", namespace = "http://localhost:9090/kmn/schemas/messages")
public class StoreResultsRequest {

    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages", required = true)
    protected String branchId;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages", required = true)
    protected String patientId;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages", required = true)
    protected String patientCode;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages", required = true)
    protected String patientName;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages", required = true)
    protected String remark;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages")
    protected int equipmentId;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages")
    protected int imageId;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages", required = true)
    protected XMLGregorianCalendar trxDate;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages", required = true)
    protected XMLGregorianCalendar timeStamp;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages", required = true)
    protected byte[] dataLocation;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages", required = true)
    protected String dataOutput;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages", required = true)
    protected String xmlData;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages", required = true)
    protected String creatorId;

    /**
     * Gets the value of the branchId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * Sets the value of the branchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchId(String value) {
        this.branchId = value;
    }

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
     * Gets the value of the patientCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientCode() {
        return patientCode;
    }

    /**
     * Sets the value of the patientCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientCode(String value) {
        this.patientCode = value;
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
     * Gets the value of the remark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the value of the remark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * Gets the value of the equipmentId property.
     * 
     */
    public int getEquipmentId() {
        return equipmentId;
    }

    /**
     * Sets the value of the equipmentId property.
     * 
     */
    public void setEquipmentId(int value) {
        this.equipmentId = value;
    }

    /**
     * Gets the value of the imageId property.
     * 
     */
    public int getImageId() {
        return imageId;
    }

    /**
     * Sets the value of the imageId property.
     * 
     */
    public void setImageId(int value) {
        this.imageId = value;
    }

    /**
     * Gets the value of the trxDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTrxDate() {
        return trxDate;
    }

    /**
     * Sets the value of the trxDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTrxDate(XMLGregorianCalendar value) {
        this.trxDate = value;
    }

    /**
     * Gets the value of the timeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

    /**
     * Gets the value of the dataLocation property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDataLocation() {
        return dataLocation;
    }

    /**
     * Sets the value of the dataLocation property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDataLocation(byte[] value) {
        this.dataLocation = ((byte[]) value);
    }

    /**
     * Gets the value of the dataOutput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataOutput() {
        return dataOutput;
    }

    /**
     * Sets the value of the dataOutput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataOutput(String value) {
        this.dataOutput = value;
    }

    /**
     * Gets the value of the xmlData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlData() {
        return xmlData;
    }

    /**
     * Sets the value of the xmlData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlData(String value) {
        this.xmlData = value;
    }

    /**
     * Gets the value of the creatorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * Sets the value of the creatorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatorId(String value) {
        this.creatorId = value;
    }

}
