
package id.co.kmn.services.wsdl.server.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PatientInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PatientInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="resPageNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resRowThisPage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resRowPerPage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resRowTotal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="patients">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="patient" type="{http://localhost:9090/kmn/schemas/types}Patient" maxOccurs="9"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientInfo", namespace = "http://localhost:9090/kmn/schemas/types", propOrder = {

})
public class PatientInfo {

    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/types")
    protected int resPageNumber;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/types")
    protected int resRowThisPage;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/types")
    protected int resRowPerPage;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/types")
    protected int resRowTotal;
    @XmlElement(namespace = "http://localhost:9090/kmn/schemas/types", required = true)
    protected PatientInfo.Patients patients;

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
     * Gets the value of the patients property.
     * 
     * @return
     *     possible object is
     *     {@link PatientInfo.Patients }
     *     
     */
    public PatientInfo.Patients getPatients() {
        return patients;
    }

    /**
     * Sets the value of the patients property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatientInfo.Patients }
     *     
     */
    public void setPatients(PatientInfo.Patients value) {
        this.patients = value;
    }


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
     *         &lt;element name="patient" type="{http://localhost:9090/kmn/schemas/types}Patient" maxOccurs="9"/>
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
        "patient"
    })
    public static class Patients {

        @XmlElement(namespace = "http://localhost:9090/kmn/schemas/types", required = true)
        protected List<Patient> patient;

        /**
         * Gets the value of the patient property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the patient property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPatient().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Patient }
         * 
         * 
         */
        public List<Patient> getPatient() {
            if (patient == null) {
                patient = new ArrayList<Patient>();
            }
            return this.patient;
        }

    }

}
