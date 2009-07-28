/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.hhs.fha.nhinc.transform.subdisc;

import org.hl7.v3.*;
import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 *
 * @author mflynn02
 */
public class HL7PRPA201309Transforms {
    public static PRPAIN201309UV createPRPA201309 (String homeCommunityId, String patientId) {
        PRPAIN201309UV result = new PRPAIN201309UV();

        // For Audit, need ControlActProcess.queryParameters.ParameterList.paitentIdentified.root
        // and .extension.
        PRPAIN201309UVQUQIMT021001UV01ControlActProcess controlActProcess = new PRPAIN201309UVQUQIMT021001UV01ControlActProcess();
        PRPAMT201307UVQueryByParameter queryParameter = new PRPAMT201307UVQueryByParameter();
        PRPAMT201307UVParameterList parameterList = new  PRPAMT201307UVParameterList();
        PRPAMT201307UVPatientIdentifier patientIdentifier = new PRPAMT201307UVPatientIdentifier();
        patientIdentifier.getValue().add(0, HL7DataTransformHelper.IIFactory(homeCommunityId, patientId));
        parameterList.getPatientIdentifier().add(patientIdentifier);
        queryParameter.setParameterList(parameterList);
        
        org.hl7.v3.ObjectFactory factory = new org.hl7.v3.ObjectFactory();
        JAXBElement oJaxbElement = factory.createPRPAIN201309UVQUQIMT021001UV01ControlActProcessQueryByParameter(queryParameter);

        controlActProcess.setQueryByParameter(oJaxbElement);
        result.setControlActProcess(controlActProcess);
        
        return result;
    }
}
