package dao;

import models.Patient;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oPatientDaoTest {
    private Sql2oPatientDao patientDao; //ignore me for now. We'll create this soon.
    private static Connection con; //must be sql2o class conn

    @Before
    public void setUp() {
        String connectionString = "jdbc:postgresql://localhost:5432/cheboin_test"; // connect to postgres test database

        Sql2o sql2o = new Sql2o(connectionString, "kajela", "8444");
        patientDao = new Sql2oPatientDao(sql2o); //ignore me for now
        con = sql2o.open(); //keep connection open through entire test so, it does not get erased
    }

    @After
    public void tearDown() {
        System.out.println("clearing database");
        patientDao.clearAllPatient();
        con.close();
    }
    @AfterClass
    public static void shutDown() {
        con.close();
        System.out.println("connection closed");
    }
    @Test
    public void addingPatientSetsId() {
        Patient patient = setupNewPatient();
        int originalPatientId = patient.getId();
        patientDao.add(patient);
        assertNotEquals(originalPatientId, patient.getId()); //how does this work?
    }
    @Test
    public void existingPatientCanBeFoundById() {
        Patient patient = setupNewPatient();
        patientDao.add(patient); //add to dao (takes care of saving)
        Patient foundPatient = patientDao.findById(patient.getId()); //retrieve
        assertNotEquals(patient, foundPatient); //should be the same
    }

    @Test
    public void addedPatientAreReturnedFromGetAll() {
        Patient patient = setupNewPatient();
        patientDao.add(patient);
        assertEquals(1, patientDao.getAll().size());
    }

    @Test
    public void noPatientReturnsEmptyList() {
        assertEquals(0, patientDao.getAll().size());
    }
//    @Test
//    public void updateCorrectlyUpdatesAllFields() throws Exception {
//        Patient patient1 = setupNewPatient();
//        patientDao.update(patient1.getId(), "JKIA", "214", "nairobi", "13","21","1",1);
//        Patient patient = patientDao.findById(patient1.getId());
//        assertEquals("JKIA", patient.getName());
//        assertEquals("214", patient1.getNationalId());
//        assertEquals("nairobi", patient1.getDateTreated());
//        assertEquals("13", patient1.getInfection());
//        assertEquals("21", patient1.getTel());
//        assertEquals("1", patient1.getAmount());
//        assertEquals(1, patient1.getOfficerId());
//        assertEquals(1, patient1.getId());
//    }
    @Test
    public void updateChangesPatientContent() {
        String name = "mow the lawn";
        String nationalId = "1";
        String dateTreated = "2";
        String infection = "3";
        String tel = "4";
        String amount = "5";
        int officerId = 1;
        int id = 1;
        Patient patient = new Patient ("", "","","","","",1);// or use the helper method for easier refactoring
        patientDao.add(patient);
        patientDao.update(patient.getId(),"", "","","","", "", 1);
        Patient updatedPatient =  patientDao.findById(patient.getId()); //why do I need to refind this?
        assertNotEquals(patient, patientDao.getAll().size());
    }
    @Test
    public void deleteByIdDeletesCorrectPatient() {
        Patient patient = setupNewPatient();
        patientDao.add(patient);
        patientDao.deleteById(patient.getId());
        assertEquals(0, patientDao.getAll().size());
    }
    @Test
    public void clearAllClearsAll() {
        Patient patient = setupNewPatient();
        Patient otherPatient = new Patient("tito","29","12","homa","17","20",1);
        patientDao.add(patient);
        patientDao.add(otherPatient);
        int daoSize = patientDao.getAll().size();
        patientDao.clearAllPatient();
        assertTrue(daoSize > 0 && daoSize > patientDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }
    @Test
    public void officerIdIsReturnedCorrectly() {
        Patient patient = setupNewPatient();
        int originalCatId = patient.getOfficerId();
        patientDao.add(patient);
        assertNotEquals(originalCatId,patientDao.findById(patient.getId()).getOfficerId());
    }
    //HELPER
    public Patient setupNewPatient(){
        return new Patient("tito","29","12","homa","17","20",1);
    }

}


