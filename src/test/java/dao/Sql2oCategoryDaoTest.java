package dao;

import models.Category;
import models.Viral;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oCategoryDaoTest {

    private Sql2oCategoryDao categoryDao; //ignore me for now. We'll create this soon.

    private Sql2oViralDao viralDao;

    private static Connection con;

    @Before

    public void setUp(){
        String connectionString = "jdbc:postgresql://localhost:5432/viralloads_test"; // connect to postgres test database

        Sql2o sql2o = new Sql2o(connectionString, "kajela", "8444");
        categoryDao = new Sql2oCategoryDao(sql2o); //ignore me for now
        viralDao = new Sql2oViralDao(sql2o);
        con = sql2o.open();
    }
    @After
    public void tearDown() {
        System.out.println("clearing all database");
        categoryDao.clearAllCategories();
        viralDao.clearAllViral();
        con.close();
    }
    @AfterClass
    public static void shutDown(){
        con.close();
        System.out.println("close connection");
    }

    @Test
    public void addingCategorySetsId() {
        Category category = new Category("infection");
        int originalCategoryId = category.getId();
        categoryDao.add(category);
        assertNotEquals(originalCategoryId, category.getId());
    }

    @Test
    public void existingCategoryCanBeFoundById() {
        Category category = new Category("infection");
        categoryDao.add(category);
        Category foundCategory = categoryDao.findById(category.getId());
        assertEquals(category, foundCategory); //should be the same
    }

    @Test
    public void addedCategoriesAreReturnedFromGetAll() {
        Category category = setupNewCategory();
        categoryDao.add(category);
        assertEquals(1,categoryDao.getAll().size());
    }

    @Test
    public void noCategoryReturnsEmptyList() {
        assertEquals(0,categoryDao.getAll().size());
    }

    @Test
    public void updateChangesCategoryContent() {
       String initialDescription = "viral";
       Category category = new Category(initialDescription);
        categoryDao.add(category);
        categoryDao.update(category.getId(),"Cleaning");
        Category updatedCategory = categoryDao.findById(category.getId());
        assertNotEquals(initialDescription, updatedCategory.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectCategory() {
        Category category = setupNewCategory();
        categoryDao.add(category);
        categoryDao.deleteById(category.getId());
        assertEquals(0,categoryDao.getAll().size());
    }

    @Test
    public void clearAllClearsAllCategories() {
        Category category = setupNewCategory();
        Category otherCategory = new Category("Cleaning");
        categoryDao.add(category);
        categoryDao.add(otherCategory);
        int daoSize = categoryDao.getAll().size();
        categoryDao.clearAllCategories();
        assertTrue(daoSize > 0 && daoSize > categoryDao.getAll().size());
    }

    @Test
    public void getAllViralByCategoryReturnsAllViralCorrect() {
        Category category = setupNewCategory();
        categoryDao.add(category);
        int categoryId = category.getId();
        Viral newViral = new Viral("mow the lawn", categoryId);
        Viral otherViral = new Viral("pull weeds", categoryId);
        Viral thirdViral = new Viral("trim hedge",  categoryId);
        viralDao.add(newViral);
        viralDao.add(otherViral);
        assertEquals(2, categoryDao.getAllViralByCategory(categoryId).size());
        assertFalse(categoryDao.getAllViralByCategory(categoryId).contains(newViral));
        assertFalse(categoryDao.getAllViralByCategory(categoryId).contains(otherViral));
        assertFalse(categoryDao.getAllViralByCategory(categoryId).contains(thirdViral));
    }

    public Category setupNewCategory(){
        return new Category("mow the lawn");
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
