
package id.co.kmn.services.wsdl.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *       &lt;sequence>
 *         &lt;element name="reqPatientId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reqDeviceId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reqImageURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reqDataXML" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reqDatetime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "reqPatientId",
    "reqDeviceId",
    "reqImageURL",
    "reqDataXML",
    "reqDatetime"
})
@XmlRootElement(name = "putPatientData")
public class PutPatientData {

    @XmlElement(required = true)
    protected String reqPatientId;
    protected int reqDeviceId;
    @XmlElement(required = true)
    protected String reqImageURL;
    @XmlElement(required = true)
    protected String reqDataXML;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar reqDatetime;

    /**
     * Gets the value of the reqPatientId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqPatientId() {
        return reqPatientId;
    }

    /**
     * Sets the value of the reqPatientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqPatientId(String value) {
        this.reqPatientId = value;
    }

    /**
     * Gets the value of the reqDeviceId property.
     * 
     */
    public int getReqDeviceId() {
        return reqDeviceId;
    }

    /**
     * Sets the value of the reqDeviceId property.
     * 
     */
    public void setReqDeviceId(int value) {
        this.reqDeviceId = value;
    }

    /**
     * Gets the value of the reqImageURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqImageURL() {
        return reqImageURL;
    }

    /**
     * Sets the value of the reqImageURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqImageURL(String value) {
        this.reqImageURL = value;
    }

    /**
     * Gets the value of the reqDataXML property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqDataXML() {
        return reqDataXML;
    }

    /**
     * Sets the value of the reqDataXML property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqDataXML(String value) {
        this.reqDataXML = value;
    }

    /**
     * Gets the value of the reqDatetime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReqDatetime() {
        return reqDatetime;
    }

    /**
     * Sets the value of the reqDatetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReqDatetime(XMLGregorianCalendar value) {
        this.reqDatetime = value;
    }

}
