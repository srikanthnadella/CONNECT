/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.hhs.fha.nhinc.mpilib;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author rayj
 */
public class MiniMpi implements IMPI {

    private static Log log = LogFactory.getLog(MiniMpi.class);
    private Patients _patients = null;
    private String customFileName = "";

    private MiniMpi() {
        LoadData();
    }

    private MiniMpi(String fileName) {
        LoadData(fileName);
    }

    public static MiniMpi GetMpiInstance() {
        return new MiniMpi();
    }

    public static MiniMpi GetMpiInstance(String fileName) {
        return new MiniMpi(fileName);
    }

    public void Reset() {
        _patients = null;
        SaveData();
    }

    private Patients SearchByDemographics(Patient searchParams, boolean includeOptOutPatient) {
        Patients results = new Patients();
        log.info("performing a demograpics search");
        if (includeOptOutPatient) {
            for (Patient patient : this.getPatients()) {
                if (PatientMatcher.IsSearchMatchByDemographics(patient, searchParams)) {
                    results.add(patient);
                }
            }
        } else {
            for (Patient patient : this.getPatients()) {
                if (PatientMatcher.IsSearchMatchByDemographics(patient, searchParams)) {
                    results.add(patient);
                }
            }
        }
        return results;
    }

    private Patients SearchById(Patient searchParams, boolean includeOptOutPatient) {
        Patients results = new Patients();
        //System.out.println("performing an id search");
        log.info("performing an id search");
        if (includeOptOutPatient) {
            for (Patient patient : this.getPatients()) {
                if (PatientMatcher.IsSearchMatchByIds(patient, searchParams)) {
                    results.add(patient);
                }
            }
        } else {
            for (Patient patient : this.getPatients()) {
                if (PatientMatcher.IsSearchMatchByIds(patient, searchParams)) {
                    results.add(patient);
                }
            }
        }
        return results;
    }

    private void ValidateNewPatient(Patient patient) {
        if (patient.getName().getLastName().contentEquals("")) {
            throw new IllegalArgumentException("Must supply a patient name");
        }
    }

    public Patient AddUpdate(Patient newPatient) {
        Patient resultPatient = null;
        ValidateNewPatient(newPatient);

        Patients existingPatients = Search(newPatient, true, true);

        if (existingPatients.size() == 2) {
            //throw exception
        } else if (existingPatients.size() == 1) {
            resultPatient = existingPatients.get(0);
            resultPatient.getIdentifiers().add(newPatient.getIdentifiers());
            resultPatient.setName(newPatient.getName());
            resultPatient.setGender(newPatient.getGender());
            resultPatient.setOptedIn(newPatient.isOptedIn());
        } else {
            getPatients().add(newPatient);
            resultPatient = newPatient;
        }

        SaveData();

        return resultPatient;
    }

    public void Delete(Patient patient, String homeCommunityId) {
        log.info("Attemping to Delete identifiers for community: " + homeCommunityId +
                " for patient: " + patient.getName().getFirstName() + " " + patient.getName().getLastName());
        Patients existingPatients = Search(patient, true, true);
        int patIdx = 0;
        int idIdx = 0;

        if (existingPatients.size() > 1) {
            log.info("ERROR: Multiple instances of the patient were found");
        } else if (existingPatients.size() == 1) {
            log.info("Found 1 entry in MPI for the patient");
            for (Patient tmpPatient : getPatients()) {
                if (existingPatients.get(0) == tmpPatient) {
                    log.info("Found a match in index " + patIdx);

                    for (Identifier id : getPatients().get(patIdx).getIdentifiers()) {
                        if (homeCommunityId.contentEquals(id.getOrganizationId())) {
                            log.info("Found a match for identifiers in index" + idIdx);
                            Identifier result = getPatients().get(patIdx).getIdentifiers().remove(idIdx);
                            break;
                        }
                        idIdx++;
                    }
                    break;
                }
                patIdx++;
            }
        } else {
            //System.out.println("ERROR: Patient not found in MPI");
            log.info("ERROR: Patient not found in MPI");
        }

        SaveData();
    }

    public Patients Search(Patient searchParams, boolean AllowSearchByDemographics) {
        return Search(searchParams, AllowSearchByDemographics, false);
    }

    public Patients Search(Patient searchParams, boolean AllowSearchByDemographics, boolean includeOptOutPatient) {
        Patients results = new Patients();

        //if not results, try a demographics search
        log.info("should we perform an demographic search?");
        if ((AllowSearchByDemographics) && (!(searchParams.getName().getFirstName().contentEquals("") && searchParams.getName().getLastName().contentEquals("")))) {
            log.info("attempt demographic search");
            results = SearchByDemographics(searchParams, includeOptOutPatient);
            if (results == null) {
                log.info("results==null");
            } else {
                log.info("results.size()=" + results.size());
            }
        } else {
            log.info("no attempt on demographic search");
        }

        //perform a id search
        log.info("should we perform an id search?");
        if ((results.size() == 0) && (searchParams.getIdentifiers().size() > 0)) {
            log.info("attempt id search");
            results = SearchById(searchParams, includeOptOutPatient);
            if (results == null) {
                log.info("results==null");
            } else {
                log.info("results.size()=" + results.size());
            }
        } else {
            log.info("no attempt on id search");
        }

        log.info("result size=" + results.size());
        return results;
    }

    public Patients getPatients() {
        if (_patients == null) {
            _patients = new Patients();
        }
        return _patients;
    }

    private void setPatients(Patients value) {
        _patients = value;
    }

    private void LoadData() {
        this.setPatients(MpiDataSaver.LoadMpi());
    }

    private void LoadData(String fileName) {
        customFileName = fileName;
        this.setPatients(MpiDataSaver.LoadMpi(fileName));
    }

    private void SaveData() {
        if (customFileName == "") {
            MpiDataSaver.SaveMpi(this.getPatients());
        } else {
            MpiDataSaver.SaveMpi(this.getPatients(), customFileName);
        }
    }

    public Patients Search(Patient searchParams) {
        return Search(searchParams, true, false);
    }
}
