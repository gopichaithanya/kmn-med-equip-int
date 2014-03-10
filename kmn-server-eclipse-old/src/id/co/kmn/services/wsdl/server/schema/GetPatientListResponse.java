
package id.co.kmn.services.wsdl.server.schema;

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
 *       &lt;all>
 *         &lt;element name="resPageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resRowThisPage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resRowPerPage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resRowTotal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resRowsXML" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlRootElement(name = "getPatientListResponse", namespace = "http://localhost:9090/kmn/schemas/messages")
public class GetPatientListResponse {

    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages")
    protected int resPageNumber;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages")
    protected int resRowThisPage;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages")
    protected int resRowPerPage;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages")
    protected int resRowTotal;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/messages", required = true)
    protected String resRowsXML;

    /**
     * Gets the value of the resPageNumber property.
     * 
     */
    public int getResPageNumber() {
        return resPageNumber;
    }

    /**
     * Sets the value of the resPageNumber property.
     * 
     */
    public void setResPageNumber(int value) {
        this.resPageNumber = value;
    }

    /**
     * Gets the value of the resRowThisPage property.
     * 
     */
    public int getResRowThisPage() {
        return resRowThisPage;
    }

    /**
     * Sets the value of the resRowThisPage property.
     * 
     */
    public void setResRowThisPage(int value) {
        this.resRowThisPage = value;
    }

    /**
     * Gets the value of the resRowPerPage property.
     * 
     */
    public int getResRowPerPage() {
        return resRowPerPage;
    }

    /**
     * Sets the value of the resRowPerPage property.
     * 
     */
    public void setResRowPerPage(int value) {
        this.resRowPerPage = value;
    }

    /**
     * Gets the value of the resRowTotal property.
     * 
     */
    public int getResRowTotal() {
        return resRowTotal;
    }

    /**
     * Sets the value of the resRowTotal property.
     * 
     */
    public void setResRowTotal(int value) {
        this.resRowTotal = value;
    }

    /**
     * Gets the value of the resRowsXML property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResRowsXML() {
        return resRowsXML;
    }

    /**
     * Sets the value of the resRowsXML property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResRowsXML(String value) {
        this.resRowsXML = value;
    }

}
