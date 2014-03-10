package id.co.kmn.services.wsdl.server.service;

import id.co.kmn.services.wsdl.client.ObjectFactory;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import static id.co.kmn.services.wsdl.WebServiceConstant.*;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/27/12
 * Time: 1:18 AM
 */
@WebService(name = QNAME_LOCAL_SERVER, targetNamespace = KMNSERVICE_TNS_URL)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface KmnService {
    /**
     *
     * @param reqPageNumber
     * @param resRowThisPage
     * @param reqClinicId
     * @param resRowTotal
     * @param reqKeyword
     * @param resRowPerPage
     * @param resPageNumber
     * @param reqRowPerPage
     * @param resRowsXML
     */
    @WebMethod(action = "http://localhost:9090/kmn/KmnService/getPatientList")
    @RequestWrapper(localName = "getPatientList", targetNamespace = KMNSERVICE_TNS_URL, className = "id.co.kmn.services.wsdl.client.GetPatientList")
    @ResponseWrapper(localName = "getPatientListResponse", targetNamespace = KMNSERVICE_TNS_URL, className = "id.co.kmn.services.wsdl.client.GetPatientListResponse")
    public void getPatientList(
        @WebParam(name = "reqKeyword", targetNamespace = "")
        String reqKeyword,
        @WebParam(name = "reqClinicId", targetNamespace = "")
        String reqClinicId,
        @WebParam(name = "reqPageNumber", targetNamespace = "")
        int reqPageNumber,
        @WebParam(name = "reqRowPerPage", targetNamespace = "")
        int reqRowPerPage,
        @WebParam(name = "resPageNumber", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<Integer> resPageNumber,
        @WebParam(name = "resRowThisPage", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<Integer> resRowThisPage,
        @WebParam(name = "resRowPerPage", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<Integer> resRowPerPage,
        @WebParam(name = "resRowTotal", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<Integer> resRowTotal,
        @WebParam(name = "resRowsXML", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<String> resRowsXML);

    /**
     *
     * @param reqPatientId
     * @param resStatusXML
     * @param resStatusNo
     * @param reqDataXML
     * @param reqDatetime
     * @param reqImageURL
     * @param reqDeviceId
     */
    @WebMethod(action = "http://localhost:9090/kmn/KmnService/putPatientData")
    @RequestWrapper(localName = "putPatientData", targetNamespace = KMNSERVICE_TNS_URL, className = "id.co.kmn.services.wsdl.client.PutPatientData")
    @ResponseWrapper(localName = "putPatientDataResponse", targetNamespace = KMNSERVICE_TNS_URL, className = "id.co.kmn.services.wsdl.client.PutPatientDataResponse")
    public void putPatientData(
        @WebParam(name = "reqPatientId", targetNamespace = "")
        String reqPatientId,
        @WebParam(name = "reqDeviceId", targetNamespace = "")
        int reqDeviceId,
        @WebParam(name = "reqImageURL", targetNamespace = "")
        String reqImageURL,
        @WebParam(name = "reqDataXML", targetNamespace = "")
        String reqDataXML,
        @WebParam(name = "reqDatetime", targetNamespace = "")
        XMLGregorianCalendar reqDatetime,
        @WebParam(name = "resStatusNo", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<String> resStatusNo,
        @WebParam(name = "resStatusXML", targetNamespace = "", mode = WebParam.Mode.OUT)
        Holder<String> resStatusXML);
}
