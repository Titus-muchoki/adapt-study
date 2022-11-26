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
private Sql2oViralDao viralDao;
private static Connection con;

@Before
    public void setUP(){
    String connectionString = "jdbc:postgresql://localhost:5432/viralloads_test"; // connect to postgres test database
    Sql2o sql2o = new Sql2o(connectionString, "kajela", "8444");
    viralDao = new Sql2oViralDao(sql2o);
    con = sql2o.open();
}
@After
    public void tearDown(){
    System.out.println("clearing database");
    viralDao.clearAllViral();
    con.close();
}
@AfterClass
    public static void shutDown(){
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
    viralDao.add(viral);
    Viral foundViral = viralDao.findById(viral.getId());
    assertNotEquals(viral, foundViral);
    }

    @Test
    public void addedViralAreReturnedByGetAll() {
    Viral viral = setupNewViral();
    viralDao.add(viral);
    assertEquals(1, viralDao.getAll().size());

    }

    @Test
    public void noViralReturnsEmptyList() {
    assertEquals(0,viralDao.getAll().size());
    }

    @Test
    public void updatesChangesViralContent() {
    String initialDescription = "infection";
    Viral viral = new Viral(initialDescription, "name",1);
    viralDao.add(viral);
    viralDao.update("infection type",viral.getId(),"new virus", 1);
    Viral updatedViral = viralDao.findById(viral.getId());
    assertNotEquals(initialDescription, updatedViral.getDescription());
    }

    @Test
    public void deleteByIdDeletesCorrectViral() {
    Viral viral = setupNewViral();
    viralDao.add(viral);
    viralDao.deleteById(viral.getId());
    assertEquals(0,viralDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() {
    Viral viral = setupNewViral();
    Viral otherViral = new Viral("blood viral","infection2",2);
    viralDao.add(viral);
    viralDao.add(otherViral);
    int daoSize = viralDao.getAll().size();
    viralDao.clearAllViral();
    assertTrue(daoSize > 0 && daoSize > viralDao.getAll().size());
    }

    @Test
    public void categoryIdIsReturnedCorrectly() {
    Viral viral = setupNewViral();
    int originalCatId = viral.getCategoryId();
    viralDao.add(viral);
    assertEquals(originalCatId, viralDao.findById(viral.getId()).getCategoryId());

    }

    @Test
    public  Viral setupNewViral() {
        return new Viral("infection", " ",1);
    }
}
