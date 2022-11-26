package dao;

import models.Viral;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oViralDaoTest {
    private Sql2oViralDao viralDao; //ignore me for now. We'll create this soon.
    private static Connection con; //must be sql2o class conn

    @Before
    public void setUp() {
        String connectionString = "jdbc:postgresql://localhost:5432/viralloads_test"; // connect to postgres test database

        Sql2o sql2o = new Sql2o(connectionString, "kajela", "8444");
        viralDao = new Sql2oViralDao(sql2o); //ignore me for now
        con = sql2o.open(); //keep connection open through entire test so, it does not get erased
    }

    @After
    public void tearDown() {
        System.out.println("clearing database");
        viralDao.clearAllViral();
        con.close();
    }
    @AfterClass
    public static void shutDown() {
        con.close();
        System.out.println("connection closed");
    }
    @Test
    public void addingViralSetsId() {
        Viral viral = setupNewViral();
        int originalViralId = viral.getId();
        viralDao.add(viral);
        assertNotEquals(originalViralId, viral.getId()); //how does this work?
    }
    @Test
    public void existingViralCanBeFoundById() {
        Viral viral = setupNewViral();
        viralDao.add(viral); //add to dao (takes care of saving)
        Viral foundViral = viralDao.findById(viral.getId()); //retrieve
        assertNotEquals(viral, foundViral); //should be the same
    }

    @Test
    public void addedViralAreReturnedFromGetAll() {
        Viral viral = setupNewViral();
        viralDao.add(viral);
        assertEquals(1, viralDao.getAll().size());
    }

    @Test
    public void noViralReturnsEmptyList() {
        assertEquals(0, viralDao.getAll().size());
    }


    @Test
    public void updateChangesViralContent() {
        String initialDescription = "mow the lawn";
        Viral viral = new Viral (initialDescription, 1);// or use the helper method for easier refactoring
        viralDao.add(viral);
        viralDao.update(viral.getId(),"brush the cat", 1);
        Viral updatedViral = viralDao.findById(viral.getId()); //why do I need to refind this?
        assertNotEquals(initialDescription, updatedViral.getDescription());
    }
    @Test
    public void deleteByIdDeletesCorrectViral() {
        Viral viral = setupNewViral();
        viralDao.add(viral);
        viralDao.deleteById(viral.getId());
        assertEquals(0, viralDao.getAll().size());
    }
    @Test
    public void clearAllClearsAll() {
        Viral viral = setupNewViral();
        Viral otherViral = new Viral("brush the cat", 2);
        viralDao.add(viral);
        viralDao.add(otherViral);
        int daoSize = viralDao.getAll().size();
        viralDao.clearAllViral();
        assertTrue(daoSize > 0 && daoSize > viralDao.getAll().size()); //this is a little overcomplicated, but illustrates well how we might use `assertTrue` in a different way.
    }
    @Test
    public void categoryIdIsReturnedCorrectly() {
        Viral viral = setupNewViral();
        int originalCatId = viral.getCategoryId();
        viralDao.add(viral);
        assertNotEquals(originalCatId,viralDao.findById(viral.getId()).getCategoryId());
    }
    //define the following once and then call it as above in your tests.
    public Viral setupNewViral(){
        return new Viral("Mow the lawn", 1);
    }

}


