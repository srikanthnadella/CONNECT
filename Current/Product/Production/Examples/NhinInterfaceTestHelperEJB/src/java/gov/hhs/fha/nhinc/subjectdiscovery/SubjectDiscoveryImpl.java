/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.subjectdiscovery;

import gov.hhs.fha.nhinc.nhinclib.NullChecker;
import gov.hhs.fha.nhinc.nhincsubjectdiscovery.NhincSubjectDiscoveryPortType;
import gov.hhs.fha.nhinc.nhincsubjectdiscovery.NhincSubjectDiscoveryService;
import gov.hhs.fha.nhinc.saml.extraction.SamlTokenExtractor;
import gov.hhs.fha.nhinc.saml.extraction.SamlTokenExtractorHelper;
import javax.xml.ws.WebServiceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.PIXConsumerPRPAIN201301UVRequestType;
import org.hl7.v3.PIXConsumerPRPAIN201302UVRequestType;
import org.hl7.v3.PIXConsumerPRPAIN201303UVRequestType;
import org.hl7.v3.PIXConsumerPRPAIN201309UVRequestType;
import org.hl7.v3.PRPAIN201301UV;
import org.hl7.v3.PRPAIN201302UV;
import org.hl7.v3.PRPAIN201303UV;
import org.hl7.v3.PRPAIN201304UV;
import org.hl7.v3.PRPAIN201309UV;
import org.hl7.v3.PRPAIN201310UV;

/**
 *
 * @author jhoppesc
 */
public class SubjectDiscoveryImpl {

    private static Log log = LogFactory.getLog(SubjectDiscoveryImpl.class);
    private static final String SERVICE_NAME = "mocksubjectdiscovery";

    public static MCCIIN000002UV01 pixConsumerPRPAIN201301UV(PRPAIN201301UV message, WebServiceContext context) {
        log.debug("Entering SubjectDiscoveryImpl.pixConsumerPRPAIN201301UV");

        MCCIIN000002UV01 ackMsg = new MCCIIN000002UV01();
        PIXConsumerPRPAIN201301UVRequestType pix201301Request = new PIXConsumerPRPAIN201301UVRequestType();

        // Determine the receiving home community id
        String homeCommunityId = null;
        if (message.getReceiver() != null &&
                message.getReceiver().size() > 0 &&
                message.getReceiver().get(0) != null &&
                message.getReceiver().get(0).getDevice() != null &&
                message.getReceiver().get(0).getDevice().getId() != null &&
                message.getReceiver().get(0).getDevice().getId().size() > 0 &&
                message.getReceiver().get(0).getDevice().getId().get(0) != null &&
                NullChecker.isNotNullish(message.getReceiver().get(0).getDevice().getId().get(0).getRoot())) {
            homeCommunityId = message.getReceiver().get(0).getDevice().getId().get(0).getRoot();
        }

        pix201301Request.setPRPAIN201301UV(message);
        pix201301Request.setAssertion(SamlTokenExtractor.GetAssertion(context));

        NhincSubjectDiscoveryService service = new NhincSubjectDiscoveryService();
        NhincSubjectDiscoveryPortType port = service.getNhincSubjectDiscoveryPortSoap11();
        ((javax.xml.ws.BindingProvider) port).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, SamlTokenExtractorHelper.getEndpointURL(homeCommunityId, SERVICE_NAME));

        ackMsg = port.pixConsumerPRPAIN201301UV(pix201301Request);

        log.debug("Exiting SubjectDiscoveryImpl.pixConsumerPRPAIN201301UV");
        return ackMsg;
    }

    public static MCCIIN000002UV01 pixConsumerPRPAIN201302UV(PRPAIN201302UV message, WebServiceContext context) {
        log.debug("Entering SubjectDiscoveryImpl.pixConsumerPRPAIN201302UV");

        MCCIIN000002UV01 ackMsg = new MCCIIN000002UV01();
        PIXConsumerPRPAIN201302UVRequestType pix201302Request = new PIXConsumerPRPAIN201302UVRequestType();

        pix201302Request.setPRPAIN201302UV(message);
        pix201302Request.setAssertion(SamlTokenExtractor.GetAssertion(context));

        // Determine the receiving home community id
        String homeCommunityId = null;
        if (message.getReceiver() != null &&
                message.getReceiver().size() > 0 &&
                message.getReceiver().get(0) != null &&
                message.getReceiver().get(0).getDevice() != null &&
                message.getReceiver().get(0).getDevice().getId() != null &&
                message.getReceiver().get(0).getDevice().getId().size() > 0 &&
                message.getReceiver().get(0).getDevice().getId().get(0) != null &&
                NullChecker.isNotNullish(message.getReceiver().get(0).getDevice().getId().get(0).getRoot())) {
            homeCommunityId = message.getReceiver().get(0).getDevice().getId().get(0).getRoot();
        }

        NhincSubjectDiscoveryService service = new NhincSubjectDiscoveryService();
        NhincSubjectDiscoveryPortType port = service.getNhincSubjectDiscoveryPortSoap11();
        ((javax.xml.ws.BindingProvider) port).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, SamlTokenExtractorHelper.getEndpointURL(homeCommunityId, SERVICE_NAME));

        ackMsg = port.pixConsumerPRPAIN201302UV(pix201302Request);

        log.debug("Exiting SubjectDiscoveryImpl.pixConsumerPRPAIN201302UV");
        return ackMsg;
    }

    public static MCCIIN000002UV01 pixConsumerPRPAIN201303UV(PRPAIN201303UV message, WebServiceContext context) {
        log.debug("Entering SubjectDiscoveryImpl.pixConsumerPRPAIN201303UV");

        MCCIIN000002UV01 ackMsg = new MCCIIN000002UV01();
        PIXConsumerPRPAIN201303UVRequestType pix201303Request = new PIXConsumerPRPAIN201303UVRequestType();

        pix201303Request.setPRPAIN201303UV(message);
        pix201303Request.setAssertion(SamlTokenExtractor.GetAssertion(context));

        // Determine the receiving home community id
        String homeCommunityId = null;
        if (message.getReceiver() != null &&
                message.getReceiver().size() > 0 &&
                message.getReceiver().get(0) != null &&
                message.getReceiver().get(0).getDevice() != null &&
                message.getReceiver().get(0).getDevice().getId() != null &&
                message.getReceiver().get(0).getDevice().getId().size() > 0 &&
                message.getReceiver().get(0).getDevice().getId().get(0) != null &&
                NullChecker.isNotNullish(message.getReceiver().get(0).getDevice().getId().get(0).getRoot())) {
            homeCommunityId = message.getReceiver().get(0).getDevice().getId().get(0).getRoot();
        }

        NhincSubjectDiscoveryService service = new NhincSubjectDiscoveryService();
        NhincSubjectDiscoveryPortType port = service.getNhincSubjectDiscoveryPortSoap11();
        ((javax.xml.ws.BindingProvider) port).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, SamlTokenExtractorHelper.getEndpointURL(homeCommunityId, SERVICE_NAME));

        ackMsg = port.pixConsumerPRPAIN201303UV(pix201303Request);

        log.debug("Exiting SubjectDiscoveryImpl.pixConsumerPRPAIN201303UV");
        return ackMsg;
    }

    public static MCCIIN000002UV01 pixConsumerPRPAIN201304UV(PRPAIN201304UV message, WebServiceContext context) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public static PRPAIN201310UV pixConsumerPRPAIN201309UV(PRPAIN201309UV message, WebServiceContext context) {
        log.debug("Entering SubjectDiscoveryImpl.pixConsumerPRPAIN201309UV");

        PRPAIN201310UV ret310 = new PRPAIN201310UV();
        PIXConsumerPRPAIN201309UVRequestType pix201309Request = new PIXConsumerPRPAIN201309UVRequestType();

        pix201309Request.setPRPAIN201309UV(message);
        pix201309Request.setAssertion(SamlTokenExtractor.GetAssertion(context));

        // Determine the receiving home community id
        String homeCommunityId = null;
        if (message.getReceiver() != null &&
                message.getReceiver().size() > 0 &&
                message.getReceiver().get(0) != null &&
                message.getReceiver().get(0).getDevice() != null &&
                message.getReceiver().get(0).getDevice().getId() != null &&
                message.getReceiver().get(0).getDevice().getId().size() > 0 &&
                message.getReceiver().get(0).getDevice().getId().get(0) != null &&
                NullChecker.isNotNullish(message.getReceiver().get(0).getDevice().getId().get(0).getRoot())) {
            homeCommunityId = message.getReceiver().get(0).getDevice().getId().get(0).getRoot();
        }

        NhincSubjectDiscoveryService service = new NhincSubjectDiscoveryService();
        NhincSubjectDiscoveryPortType port = service.getNhincSubjectDiscoveryPortSoap11();
        ((javax.xml.ws.BindingProvider) port).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, SamlTokenExtractorHelper.getEndpointURL(homeCommunityId, SERVICE_NAME));

        ret310 = port.pixConsumerPRPAIN201309UV(pix201309Request).getPRPAIN201310UV();

        log.debug("Exiting SubjectDiscoveryImpl.pixConsumerPRPAIN201309UV");
        return ret310;
    }
}
