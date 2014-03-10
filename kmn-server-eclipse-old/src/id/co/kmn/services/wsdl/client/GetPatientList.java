
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
 *         &lt;element name="reqKeyword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reqClinicId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reqPageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reqRowPerPage" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "reqKeyword",
    "reqClinicId",
    "reqPageNumber",
    "reqRowPerPage"
})
@XmlRootElement(name = "getPatientList")
public class GetPatientList {

    @XmlElement(required = true)
    protected String reqKeyword;
    @XmlElement(required = true)
    protected String reqClinicId;
    protected int reqPageNumber;
    protected int reqRowPerPage;

    /**
     * Gets the value of the reqKeyword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqKeyword() {
        return reqKeyword;
    }

    /**
     * Sets the value of the reqKeyword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqKeyword(String value) {
        this.reqKeyword = value;
    }

    /**
     * Gets the value of the reqClinicId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqClinicId() {
        return reqClinicId;
    }

    /**
     * Sets the value of the reqClinicId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqClinicId(String value) {
        this.reqClinicId = value;
    }

    /**
     * Gets the value of the reqPageNumber property.
     * 
     */
    public int getReqPageNumber() {
        return reqPageNumber;
    }

    /**
     * Sets the value of the reqPageNumber property.
     * 
     */
    public void setReqPageNumber(int value) {
        this.reqPageNumber = value;
    }

    /**
     * Gets the value of the reqRowPerPage property.
     * 
     */
    public int getReqRowPerPage() {
        return reqRowPerPage;
    }

    /**
     * Sets the value of the reqRowPerPage property.
     * 
     */
    public void setReqRowPerPage(int value) {
        this.reqRowPerPage = value;
    }

}
