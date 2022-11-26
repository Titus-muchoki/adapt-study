package models;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ViralTest {

    @Test
    public void newViralObjectGetsCorrectlyCreated_true() {
        Viral viral = setupNewViral();
        assertNotNull(viral);
    }

    @Test
    public void ViralInstantiatesWithDescription_true() {
        Viral viral = setupNewViral();
        assertEquals("infection", viral.getDescription());
    }

    @Test
    public void ViralInstantiatesWithName_true() {
        Viral viral = setupNewViral();
        assertEquals("infection type", viral.getName());
    }

    @Test
    public void isCompletedPropertyIsFalseAfterInstantiation() {
        Viral viral = setupNewViral();
        assertFalse(viral.isCompleted());
    }

    @Test
    public void getCreatedAtInstantiatesWithCurrentTimeToday() {
        Viral viral = setupNewViral();
        assertEquals(LocalDateTime.now().getDayOfWeek(),viral.getCreatedAt().getDayOfWeek());
    }

    @Test
    public   Viral setupNewViral() {
        return new Viral("infection", " ",1);
    }
}
