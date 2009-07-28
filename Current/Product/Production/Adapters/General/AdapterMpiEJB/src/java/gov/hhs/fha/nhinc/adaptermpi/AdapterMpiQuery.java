/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.adaptermpi;

import gov.hhs.fha.nhinc.adaptercomponentmpi.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hl7.v3.PIXConsumerPRPAIN201305UVRequestType;
import org.hl7.v3.PRPAIN201306UV;
import gov.hhs.fha.nhinc.properties.PropertyAccessor;
import gov.hhs.fha.nhinc.connectmgr.ConnectionManagerCache;


/**
 *
 * @author Jon Hoppesch
 */

public class AdapterMpiQuery {
   private static Log log = LogFactory.getLog(AdapterMpiQuery.class);
   
   private static final String GATEWAY_PROPERTY_FILE = "gateway";
   private static final String HOME_COMMUNITY_ID_PROPERTY = "localHomeCommunityId";
   private static final String SERVICE_NAME_ADAPTER_COMPONENT_MPI_SERVICE = "adaptercomponentmpiservice";
  
   /**
    * Send the patient query request to the actual MPI that is implemented
    * 
    * @param 
    * @return
    */
   public static PRPAIN201306UV query(PIXConsumerPRPAIN201305UVRequestType findCandidatesRequest) {
       log.debug("Entering AdapterMpiQuery.query method...");
       PRPAIN201306UV  queryResponse = null;
       AdapterComponentMpiService mpiService = new AdapterComponentMpiService ();
       AdapterComponentMpiPortType mpiPort = mpiService.getAdapterComponentMpiPort(); 
       
       // Get the Home community ID for this box...
       //------------------------------------------
       String sHomeCommunityId = "";
       String sEndpointURL = "";
       try {
           sHomeCommunityId = PropertyAccessor.getProperty(GATEWAY_PROPERTY_FILE, HOME_COMMUNITY_ID_PROPERTY);
       }
       catch (Exception e) {
           log.error("Failed to read " + HOME_COMMUNITY_ID_PROPERTY + 
                     " property from the " + GATEWAY_PROPERTY_FILE + ".properties  file.  Error: " + 
                     e.getMessage(), e);
       }

       if ((sHomeCommunityId != null) && (sHomeCommunityId.length() > 0)) {
           try {
               sEndpointURL = ConnectionManagerCache.getEndpointURLByServiceName(sHomeCommunityId, SERVICE_NAME_ADAPTER_COMPONENT_MPI_SERVICE);
           }
           catch (Exception e) {
               log.error("Failed to retrieve endpoint URL for service:" + SERVICE_NAME_ADAPTER_COMPONENT_MPI_SERVICE + 
                         " from connection manager.  Error: " + e.getMessage(), e);
           }
       }
       
       if ((sEndpointURL != null) &&
           (sEndpointURL.length() > 0)) {
           ((javax.xml.ws.BindingProvider) mpiPort).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, sEndpointURL);
       }
       else {
           // Just a way to cover ourselves for the time being...  - assume port 8080
           //-------------------------------------------------------------------------
           ((javax.xml.ws.BindingProvider) mpiPort).getRequestContext().put(javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/NhinConnect/AdapterComponentMpiService");

           log.warn("Did not find endpoint URL for service: " + SERVICE_NAME_ADAPTER_COMPONENT_MPI_SERVICE + " and " +
                    "Home Community: " + sHomeCommunityId + ".  Using default URL: " +
                    "'http://localhost:8080/NhinConnect/AdapterComponentMpiService'");
       }
       
       if (findCandidatesRequest.getPRPAIN201305UV() != null) {
           queryResponse = mpiPort.findCandidates(findCandidatesRequest.getPRPAIN201305UV());
       }
       else {
           queryResponse = null;
       }
       
       log.debug("Exiting AdapterMpiQuery.query method...");
       return queryResponse;
   }
}
