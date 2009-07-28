/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.adaptercomponentmpi;

import java.util.List;
import javax.xml.bind.JAXBElement;
import org.hl7.v3.*;
import gov.hhs.fha.nhinc.mpilib.*;
import java.io.Serializable;
import java.util.Iterator;
import static org.junit.Assert.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Jon Hoppesch
 */
public class TestHelper {
    
    private static Log log = LogFactory.getLog(TestHelper.class);

    public static void AssertPatientIdsAreSame(PRPAIN201306UV expected, PRPAIN201306UV result) {
        AssertPatientNotNull(expected);
        AssertPatientNotNull(result);

        AssertPatientIdsEqual(expected.getControlActProcess().getSubject().get(0).getRegistrationEvent().getSubject1().getPatient(),
                result.getControlActProcess().getSubject().get(0).getRegistrationEvent().getSubject1().getPatient());
    }

    public static void AssertPatientNamesAreSame(PRPAIN201306UV expected, PRPAIN201306UV result) {
        AssertPatientPersonNotNull(expected);
        AssertPatientPersonNotNull(result);

        AssertPatientNamesEqual(expected.getControlActProcess().getSubject().get(0).getRegistrationEvent().getSubject1().getPatient().getPatientPerson().getValue(),
                result.getControlActProcess().getSubject().get(0).getRegistrationEvent().getSubject1().getPatient().getPatientPerson().getValue());
    }

    public static void AssertPatientGendersAreSame(PRPAIN201306UV expected, PRPAIN201306UV result) {
        AssertPatientPersonNotNull(expected);
        AssertPatientPersonNotNull(result);

        AssertPatientGendersEqual(expected.getControlActProcess().getSubject().get(0).getRegistrationEvent().getSubject1().getPatient().getPatientPerson().getValue(),
                result.getControlActProcess().getSubject().get(0).getRegistrationEvent().getSubject1().getPatient().getPatientPerson().getValue());
    }

    public static void AssertPatientBdaysAreSame(PRPAIN201306UV expected, PRPAIN201306UV result) {
        AssertPatientPersonNotNull(expected);
        AssertPatientPersonNotNull(result);

        AssertPatientBdaysEqual(expected.getControlActProcess().getSubject().get(0).getRegistrationEvent().getSubject1().getPatient().getPatientPerson().getValue(),
                result.getControlActProcess().getSubject().get(0).getRegistrationEvent().getSubject1().getPatient().getPatientPerson().getValue());
    }

    public static void AssertPatientNamesEqual(PRPAMT201310UVPerson patient1, PRPAMT201310UVPerson patient2) {
        assertNotNull(patient1.getName());
        assertNotNull(patient1.getName().get(0));
        assertNotNull(patient1.getName().get(0).getContent());
        assertNotNull(patient2.getName());
        assertNotNull(patient2.getName().get(0));
        assertNotNull(patient2.getName().get(0).getContent());

        String pat1Name = extractName (patient1.getName().get(0));
        log.info("Patient 1 name:" + pat1Name);
        String pat2Name = extractName (patient2.getName().get(0));
        log.info("Patient 2 name:" + pat2Name);
        assertEquals(pat1Name, pat2Name);
    }
    
    private static String extractName (PNExplicit name) {
        String nameString = "";
        Boolean hasName = false;
        List<Serializable> choice = name.getContent();
        Iterator<Serializable> iterSerialObjects = choice.iterator();

        EnExplicitFamily familyName = new EnExplicitFamily();
        EnExplicitGiven givenName = new EnExplicitGiven();

        while (iterSerialObjects.hasNext()) {
            Serializable contentItem = iterSerialObjects.next();

            if (contentItem instanceof JAXBElement) {
                JAXBElement oJAXBElement = (JAXBElement) contentItem;
                if (oJAXBElement.getValue() instanceof EnExplicitFamily) {
                    familyName = (EnExplicitFamily) oJAXBElement.getValue();
                    hasName = true;
                } else if (oJAXBElement.getValue() instanceof EnExplicitGiven) {
                    givenName = (EnExplicitGiven) oJAXBElement.getValue();
                    hasName = true;
                }
            }
        }
        
        if (hasName == true) {
            nameString = familyName.getContent() + " " + givenName.getContent();
            System.out.println(nameString);
        }
        
        return nameString;
    }

    public static void AssertPatientGendersEqual(PRPAMT201310UVPerson patient1, PRPAMT201310UVPerson patient2) {
        assertNotNull(patient1.getAdministrativeGenderCode());
        assertNotNull(patient1.getAdministrativeGenderCode().getCode());
        assertNotNull(patient2.getAdministrativeGenderCode());
        assertNotNull(patient1.getAdministrativeGenderCode().getCode());

        assertEquals(patient1.getAdministrativeGenderCode().getCode(), patient1.getAdministrativeGenderCode().getCode());
    }

    public static void AssertPatientBdaysEqual(PRPAMT201310UVPerson patient1, PRPAMT201310UVPerson patient2) {
        assertNotNull(patient1.getBirthTime());
        assertNotNull(patient1.getBirthTime().getValue());
        assertNotNull(patient2.getBirthTime());
        assertNotNull(patient2.getBirthTime().getValue());

        assertEquals(patient1.getBirthTime().getValue(), patient2.getBirthTime().getValue());
    }

    public static void AssertPatientIdsEqual(PRPAMT201310UVPatient patient1, PRPAMT201310UVPatient patient2) {
        AssertPatientIdNotNull(patient1);
        AssertPatientIdNotNull(patient2);

        assertEquals(patient1.getId().get(0).getRoot(), patient1.getId().get(0).getRoot());
        assertEquals(patient1.getId().get(0).getExtension(), patient1.getId().get(0).getExtension());
    }

    public static void AssertPatientIdNotNull(PRPAMT201310UVPatient patient) {
        assertNotNull(patient.getId());
        assertNotNull(patient.getId().get(0));
        assertNotNull(patient.getId().get(0).getRoot());
        assertNotNull(patient.getId().get(0).getExtension());
    }

    public static void AssertPatientPersonNotNull(PRPAIN201306UV queryResp) {
        AssertPatientNotNull(queryResp);
        assertNotNull(queryResp.getControlActProcess().getSubject().get(0).getRegistrationEvent().getSubject1().getPatient().getPatientPerson());
        assertNotNull(queryResp.getControlActProcess().getSubject().get(0).getRegistrationEvent().getSubject1().getPatient().getPatientPerson().getValue());
    }

    public static void AssertPatientNotNull(PRPAIN201306UV queryResp) {
        assertNotNull(queryResp);
        assertNotNull(queryResp.getControlActProcess());
        assertNotNull(queryResp.getControlActProcess().getSubject());
        assertNotNull(queryResp.getControlActProcess().getSubject().get(0));
        assertNotNull(queryResp.getControlActProcess().getSubject().get(0).getRegistrationEvent());
        assertNotNull(queryResp.getControlActProcess().getSubject().get(0).getRegistrationEvent().getSubject1());
        assertNotNull(queryResp.getControlActProcess().getSubject().get(0).getRegistrationEvent().getSubject1().getPatient());
    }

    public static PRPAIN201305UV build201305(String firstName, String lastName, String gender, String birthTime, II subjectId) {
        PRPAIN201305UV msg = new PRPAIN201305UV();

        // Set up message header fields
        msg.setITSVersion("XML_1.0");

        II id = new II();
        id.setRoot("1.1");
        msg.setId(id);

        TSExplicit creationTime = new TSExplicit();
        creationTime.setValue("20090202000000");
        msg.setCreationTime(creationTime);

        II interactionId = new II();
        interactionId.setRoot("2.16.840.1.113883.1.6");
        interactionId.setExtension("PRPA_IN201305UV");
        msg.setInteractionId(id);

        CS processingCode = new CS();
        processingCode.setCode("P");
        msg.setProcessingCode(processingCode);

        CS processingModeCode = new CS();
        processingModeCode.setCode("R");
        msg.setProcessingModeCode(processingModeCode);

        CS ackCode = new CS();
        ackCode.setCode("AL");
        msg.setAcceptAckCode(ackCode);

        // Set the receiver and sender
        msg.getReceiver().add(createReceiver());
        msg.setSender(createSender());

        msg.setControlActProcess(createControlActProcess(firstName, lastName, gender, birthTime, subjectId));

        return msg;
    }

    private static PRPAIN201305UVQUQIMT021001UV01ControlActProcess createControlActProcess(String firstName, String lastName, String gender, String birthTime, II subjectId) {
        PRPAIN201305UVQUQIMT021001UV01ControlActProcess controlActProcess = new PRPAIN201305UVQUQIMT021001UV01ControlActProcess();

        controlActProcess.setMoodCode("EVN");

        CD code = new CD();
        code.setCode("PRPA_TE201305UV");
        code.setCodeSystem("2.16.840.1.113883.1.6");
        controlActProcess.setCode(code);

        controlActProcess.setQueryByParameter(createQueryParams(firstName, lastName, gender, birthTime, subjectId));

        return controlActProcess;
    }

    private static JAXBElement<PRPAMT201306UVQueryByParameter> createQueryParams(String firstName, String lastName, String gender, String birthTime, II subjectId) {
        PRPAMT201306UVQueryByParameter params = new PRPAMT201306UVQueryByParameter();

        II id = new II();
        id.setRoot("12345");
        params.setQueryId(id);

        CS statusCode = new CS();
        statusCode.setCode("new");
        params.setStatusCode(statusCode);

        params.setParameterList(createParamList(firstName, lastName, gender, birthTime, subjectId));

        javax.xml.namespace.QName xmlqname = new javax.xml.namespace.QName("urn:hl7-org:v3", "queryByParameter");
        JAXBElement<PRPAMT201306UVQueryByParameter> queryParams = new JAXBElement<PRPAMT201306UVQueryByParameter>(xmlqname, PRPAMT201306UVQueryByParameter.class, params);

        return queryParams;
    }

    private static PRPAMT201306UVParameterList createParamList(String firstName, String lastName, String gender, String birthTime, II subjectId) {
        PRPAMT201306UVParameterList paramList = new PRPAMT201306UVParameterList();

        // Set the Subject Gender Code  
        paramList.getLivingSubjectAdministrativeGender().add(createGender(gender));

        // Set the Subject Birth Time
        paramList.getLivingSubjectBirthTime().add(createBirthTime(birthTime));

        // Set the Subject Name
        paramList.getLivingSubjectName().add(createName(firstName, lastName));

        // Set the subject Id
        paramList.getLivingSubjectId().add(createSubjectId(subjectId));

        return paramList;
    }

    private static PRPAMT201306UVLivingSubjectId createSubjectId(II subjectId) {
        PRPAMT201306UVLivingSubjectId id = new PRPAMT201306UVLivingSubjectId();
        if (subjectId != null) {
            id.getValue().add(subjectId);
        }

        return id;
    }

    private static PRPAMT201306UVLivingSubjectName createName(String firstName, String lastName) {
        PRPAMT201306UVLivingSubjectName subjectName = new PRPAMT201306UVLivingSubjectName();
        org.hl7.v3.ObjectFactory factory = new org.hl7.v3.ObjectFactory();
        ENExplicit name = (ENExplicit) (factory.createENExplicit());
        List namelist = name.getContent();

        if (lastName != null &&
                lastName.length() > 0) {
            EnExplicitFamily familyName = new EnExplicitFamily();
            familyName.setPartType("FAM");
            familyName.setContent(lastName);

            namelist.add(factory.createENExplicitFamily(familyName));
        }

        if (firstName != null &&
                firstName.length() > 0) {
            EnExplicitGiven givenName = new EnExplicitGiven();
            givenName.setPartType("GIV");
            givenName.setContent(firstName);

            namelist.add(factory.createENExplicitGiven(givenName));
        }

        subjectName.getValue().add(name);

        return subjectName;
    }

    private static PRPAMT201306UVLivingSubjectBirthTime createBirthTime(String birthTime) {
        PRPAMT201306UVLivingSubjectBirthTime subjectBirthTime = new PRPAMT201306UVLivingSubjectBirthTime();
        IVLTSExplicit bday = new IVLTSExplicit();

        if (birthTime != null &&
                birthTime.length() > 0) {
            bday.setValue(birthTime);
            subjectBirthTime.getValue().add(bday);
        }

        return subjectBirthTime;
    }

    private static PRPAMT201306UVLivingSubjectAdministrativeGender createGender(String gender) {
        PRPAMT201306UVLivingSubjectAdministrativeGender adminGender = new PRPAMT201306UVLivingSubjectAdministrativeGender();
        CE genderCode = new CE();

        if (gender != null &&
                gender.length() > 0) {
            genderCode.setCode(gender);
            adminGender.getValue().add(genderCode);
        }

        return adminGender;
    }

    private static MCCIMT000100UV01Receiver createReceiver() {
        MCCIMT000100UV01Receiver receiver = new MCCIMT000100UV01Receiver();

        receiver.setTypeCode(CommunicationFunctionType.RCV);

        MCCIMT000100UV01Device device = new MCCIMT000100UV01Device();
        device.setDeterminerCode("INSTANCE");

        II id = new II();
        id.setRoot("2.16.840.1.113883.3.200");
        device.getId().add(id);

        TELExplicit url = new TELExplicit();
        url.setValue("http://localhost:9080/NhinConnect/AdapterComponentMpiService");
        device.getTelecom().add(url);

        receiver.setDevice(device);

        return receiver;
    }

    private static MCCIMT000100UV01Sender createSender() {
        MCCIMT000100UV01Sender sender = new MCCIMT000100UV01Sender();

        sender.setTypeCode(CommunicationFunctionType.SND);

        MCCIMT000100UV01Device device = new MCCIMT000100UV01Device();
        device.setDeterminerCode("INSTANCE");

        II id = new II();
        id.setRoot("2.16.840.1.113883.3.200");
        device.getId().add(id);

        sender.setDevice(device);

        return sender;
    }

    public static Patient createMpiPatient(String firstName, String lastName, String gender, String birthTime, Identifier subjectId) {
        Patient result = new Patient();

        // Set the patient name
        PersonName name = new PersonName();
        name.setFirstName(firstName);
        name.setLastName(lastName);
        result.setName(name);

        // Set the patient gender
        result.setGender(gender);

        // Set the patient birth time
        result.setDateOfBirth(birthTime);

        // Set the patient Id
        Identifiers ids = new Identifiers();
        ids.add(subjectId);
        result.setIdentifiers(ids);

        return result;
    }
}
