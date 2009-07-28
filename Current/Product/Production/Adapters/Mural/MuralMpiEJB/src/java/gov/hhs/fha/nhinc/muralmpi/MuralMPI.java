/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.muralmpi;

import gov.hhs.fha.nhinc.adaptercomponentmpi.AdapterComponentMpiPortType;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author dunnek
 */
@WebService(serviceName = "AdapterComponentMpiService", portName = "AdapterComponentMpiPort", endpointInterface = "gov.hhs.fha.nhinc.adaptercomponentmpi.AdapterComponentMpiPortType", targetNamespace = "urn:gov:hhs:fha:nhinc:adaptercomponentmpi", wsdlLocation = "META-INF/wsdl/MuralMPI/AdapterComponentMpi.wsdl")
@Stateless

public class MuralMPI implements AdapterComponentMpiPortType {

    public org.hl7.v3.PRPAIN201306UV findCandidates(org.hl7.v3.PRPAIN201305UV findCandidatesRequest)
    {
        //TODO implement this method
        return MuralMPIQuery.query(findCandidatesRequest);
    }

}
