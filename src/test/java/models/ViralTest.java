package models;
import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class ViralTest {
    @Test
    public void NewViralObjectGetsCorrectlyCreated_true() throws Exception {
        Viral viral = setupNewViral();
        assertEquals(true, viral instanceof Viral);
    }

    @Test
    public void ViralInstantiatesWithDescription_true() throws Exception {
        Viral viral = setupNewViral();
        assertEquals("Mow the lawn", viral.getDescription());
    }

    @Test
    public void isCompletedPropertyIsFalseAfterInstantiation() throws Exception {
        Viral viral = setupNewViral();
        assertEquals(false, viral.isCompleted()); //should never start as completed
    }

    @Test
    public void getCreatedAtInstantiatesWithCurrentTimeToday() throws Exception {
        Viral viral = setupNewViral();
        assertEquals(LocalDateTime.now().getDayOfWeek(), viral.getCreatedAt().getDayOfWeek());
    }

    //helper methods
    public Viral setupNewViral(){
        return new Viral("Mow the lawn", 1);
    }


}
