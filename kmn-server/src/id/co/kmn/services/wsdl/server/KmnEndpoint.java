package id.co.kmn.services.wsdl.server;

import id.co.kmn.services.wsdl.client.GetPatientListResponse;
import id.co.kmn.services.wsdl.client.ObjectFactory;
import id.co.kmn.services.wsdl.server.bean.Patient;
import id.co.kmn.services.wsdl.server.service.KmnService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

import static id.co.kmn.services.wsdl.server.KmnEndPointContants.GET_PATIENTS_REQUEST;
import static id.co.kmn.services.wsdl.server.KmnEndPointContants.MESSAGES_NAMESPACE;

/**
 * Endpoint that handles the KMN Web Service messages using a combination of JAXB2 marshalling and XPath
 * expressions.
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/26/12
 * Time: 11:44 AM
 */
@Endpoint
public class KmnEndpoint {
    private static final Log logger = LogFactory.getLog(KmnEndpoint.class);

    private final ObjectFactory objectFactory = new ObjectFactory();

    private final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    private final KmnService kmnService;

    @Autowired
    public KmnEndpoint(KmnService kmnService) {
        this.kmnService = kmnService;
    }

    /**
     * This endpoint method uses a combination of XPath expressions and marshalling to handle message with a
     * <code>&lt;GetPatientsRequest&gt;</code> payload.
     *
     * @param reqKeyword
     * @param reqClinicId
     * @param reqPageNumber
     * @param reqRowPerPage
     * @return the JAXB2 representation of a <code>&lt;GetPatientsResponse&gt;</code>
     */
    @PayloadRoot(localPart = GET_PATIENTS_REQUEST, namespace = MESSAGES_NAMESPACE)
    @Namespace(prefix = "m", uri = MESSAGES_NAMESPACE)
    @ResponsePayload
    public GetPatientListResponse getPatients(@XPathParam("//m:reqKeyword") String reqKeyword,
                                         @XPathParam("//m:reqClinicId") String reqClinicId,
                                         @XPathParam("//m:reqPageNumber") int reqPageNumber,
                                         @XPathParam("//m:reqRowPerPage") int reqRowPerPage)
            throws DatatypeConfigurationException, ParserConfigurationException {
        if (logger.isDebugEnabled()) {
            logger.debug("Received GetPatientsRequest '" + reqKeyword + "' id: '" + reqClinicId + "' page: "
                    + reqPageNumber + "reqRowPerPage: " + reqRowPerPage);
        }
        List<Patient> patients = kmnService.getPatients(reqKeyword, reqClinicId, reqPageNumber, reqRowPerPage);

        GetPatientListResponse response = objectFactory.createGetPatientListResponse();
        response.setResPageNumber(1);
        response.setResRowPerPage(10);
        response.setResRowsXML("Test Result");
        response.setResRowThisPage(2);
        response.setResRowTotal(2);

//        for (id.co.kmn.services.wsdl.server.bean.Patient patient : patients) {
//            response.getResRowsXML().add(SchemaConversionUtils.toSchemaType(patient));
//        }

        return response;
    }
//
//    /**
//     * This endpoint method uses marshalling to handle message with a <code>&lt;BookFlightRequest&gt;</code> payload.
//     *
//     * @param request the JAXB2 representation of a <code>&lt;BookFlightRequest&gt;</code>
//     * @return the JAXB2 representation of a <code>&lt;BookFlightResponse&gt;</code>
//     */
//    @PayloadRoot(localPart = BOOK_FLIGHT_REQUEST, namespace = MESSAGES_NAMESPACE)
//    @ResponsePayload
//    public JAXBElement<Ticket> bookFlight(@RequestPayload BookFlightRequest request)
//            throws NoSeatAvailableException, DatatypeConfigurationException, NoSuchFlightException,
//            NoSuchFrequentFlyerException {
//        if (logger.isDebugEnabled()) {
//            logger.debug("Received BookingFlightRequest '" + request.getFlightNumber() + "' on '" +
//                    request.getDepartureTime() + "' for " + request.getPassengers().getPassengerOrUsername());
//        }
//        Ticket ticket = bookSchemaFlight(request.getFlightNumber(), request.getDepartureTime(),
//                request.getPassengers().getPassengerOrUsername());
//        return objectFactory.createBookFlightResponse(ticket);
//    }
//
//    /**
//     * Converts between the domain and schema types.
//     */
//    private Ticket bookSchemaFlight(String flightNumber,
//                                    XMLGregorianCalendar xmlDepartureTime,
//                                    List<Object> passengerOrUsernameList)
//            throws NoSeatAvailableException, NoSuchFlightException, NoSuchFrequentFlyerException,
//            DatatypeConfigurationException {
//        DateTime departureTime = SchemaConversionUtils.toDateTime(xmlDepartureTime);
//        List<Passenger> passengers = new ArrayList<Passenger>(passengerOrUsernameList.size());
//        for (Iterator<Object> iterator = passengerOrUsernameList.iterator(); iterator.hasNext();) {
//            Object passengerOrUsername = iterator.next();
//            if (passengerOrUsername instanceof Name) {
//                Name passengerName = (Name) passengerOrUsername;
//                Passenger passenger = new Passenger(passengerName.getFirst(), passengerName.getLast());
//                passengers.add(passenger);
//            }
//            else if (passengerOrUsername instanceof String) {
//                String frequentFlyerUsername = (String) passengerOrUsername;
//                FrequentFlyer frequentFlyer = new FrequentFlyer(frequentFlyerUsername);
//                passengers.add(frequentFlyer);
//            }
//        }
//        org.springframework.ws.samples.airline.domain.Ticket domainTicket =
//                airlineService.bookFlight(flightNumber, departureTime, passengers);
//        return SchemaConversionUtils.toSchemaType(domainTicket);
//    }
//
//    @PayloadRoot(localPart = GET_FREQUENT_FLYER_MILEAGE_REQUEST, namespace = MESSAGES_NAMESPACE)
//    @ResponsePayload
//    public Element getFrequentFlyerMileage() throws Exception {
//        if (logger.isDebugEnabled()) {
//            logger.debug("Received GetFrequentFlyerMileageRequest");
//        }
//        int mileage = airlineService.getFrequentFlyerMileage();
//        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//        Document document = documentBuilder.newDocument();
//        Element response = document.createElementNS(MESSAGES_NAMESPACE, GET_FREQUENT_FLYER_MILEAGE_RESPONSE);
//        response.setTextContent(Integer.toString(mileage));
//        return response;
//    }
}
