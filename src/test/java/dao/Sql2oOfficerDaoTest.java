package dao;

import models.Officer;

import models.Patient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oOfficerDaoTest {

    private Sql2oOfficerDao officerDao; //ignore me for now. We'll create this soon.

    private Sql2oPatientDao patientDao;

    private static Connection con;

    @Before

    public void setUp(){
        String connectionString = "jdbc:postgresql://localhost:5432/cheboin_test"; // connect to postgres test database

        Sql2o sql2o = new Sql2o(connectionString, "kajela", "8444");
        officerDao = new Sql2oOfficerDao(sql2o); //ignore me for now
        patientDao = new Sql2oPatientDao(sql2o);
        con = sql2o.open();
    }
    @After
    public void tearDown() {
        System.out.println("clearing all database");
        officerDao.clearAllOfficers();
        patientDao.clearAllPatient();
        con.close();
    }
    @AfterClass
    public static void shutDown(){
        con.close();
        System.out.println("close connection");
    }

    @Test
    public void addingOfficerSetsId() {
        Officer officer = new Officer("infection");
        int originalOfficerId = officer.getId();
        officerDao.add(officer);
        assertNotEquals(originalOfficerId, officer.getId());
    }

    @Test
    public void existingOfficerCanBeFoundById() {
        Officer officer = new Officer("infection");
        officerDao.add(officer);
        Officer foundOfficer = officerDao.findById(officer.getId());
        assertEquals(officer, foundOfficer); //should be the same
    }

    @Test
    public void addedOfficersAreReturnedFromGetAll() {
        Officer officer = setupNewOfficer();
        officerDao.add(officer);
        assertEquals(1,officerDao.getAll().size());
    }

    @Test
    public void noOfficerReturnsEmptyList() {
        assertEquals(0,officerDao.getAll().size());
    }

    @Test
    public void updateChangesOfficerContent() {
       String initialDescription = "viral";
       Officer officer = new Officer(initialDescription);
        officerDao.add(officer);
        officerDao.update(officer.getId(),"Cleaning");
        Officer updatedCategory = officerDao.findById(officer.getId());
        assertNotEquals(initialDescription, updatedCategory.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectOfficer() {
        Officer officer = setupNewOfficer();
        officerDao.add(officer);
        officerDao.deleteById(officer.getId());
        assertEquals(0,officerDao.getAll().size());
    }

    @Test
    public void clearAllClearsAllOfficers() {
        Officer officer = setupNewOfficer();
        Officer otherOfficer = new Officer("Cleaning");
        officerDao.add(officer);
        officerDao.add(otherOfficer);
        int daoSize = officerDao.getAll().size();
        officerDao.clearAllOfficers();
        assertTrue(daoSize > 0 && daoSize > officerDao.getAll().size());
    }

    @Test
    public void getAllPatientByOfficerReturnsAllPatientCorrect() {
        Officer officer = setupNewOfficer();
        officerDao.add(officer);
        int officerId = officer.getId();
        Patient newPatient = new Patient("mow the lawn","1","2","3","4","5",officerId);
        Patient otherPatient = new Patient("pull weeds", "mow the lawn","1","2","3","4",officerId);
        Patient thirdPatient = new Patient("trim hedge", "mow the lawn","1","2","3","4",officerId);
        patientDao.add(newPatient);
        patientDao.add(otherPatient);
        assertEquals(2, officerDao.getAllPatientByOfficer(officerId).size());
        assertFalse(officerDao.getAllPatientByOfficer(officerId).contains(newPatient));
        assertFalse(officerDao.getAllPatientByOfficer(officerId).contains(otherPatient));
        assertFalse(officerDao.getAllPatientByOfficer(officerId).contains(thirdPatient));
    }

    public Officer setupNewOfficer(){
        return new Officer("mow the lawn");
    }

    @Test
    public void testEquals() {
    }

    @Test
    public void testHashCode() {
    }

    @Test
    public void getName() {
    }

    @Test
    public void setName() {
    }

    @Test
    public void getId() {
    }

    @Test
    public void setId() {
    }
}
