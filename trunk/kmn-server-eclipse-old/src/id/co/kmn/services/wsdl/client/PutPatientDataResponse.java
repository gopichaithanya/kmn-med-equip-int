
package id.co.kmn.services.wsdl.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="resStatusNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resStatusXML" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "resStatusNo",
    "resStatusXML"
})
@XmlRootElement(name = "putPatientDataResponse")
public class PutPatientDataResponse {

    @XmlElement(required = true)
    protected String resStatusNo;
    @XmlElement(required = true)
    protected String resStatusXML;

    /**
     * Gets the value of the resStatusNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResStatusNo() {
        return resStatusNo;
    }

    /**
     * Sets the value of the resStatusNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResStatusNo(String value) {
        this.resStatusNo = value;
    }

    /**
     * Gets the value of the resStatusXML property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResStatusXML() {
        return resStatusXML;
    }

    /**
     * Sets the value of the resStatusXML property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResStatusXML(String value) {
        this.resStatusXML = value;
    }

}
