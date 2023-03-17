package models;
import org.junit.Test;
import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class PatientTest {
    @Test
    public void getNameReturnNameCorrectly() throws Exception{
        Patient patient = setupNewPatient();
        assertEquals("tito", patient.getName());
    }
    @Test
    public void getNationalIdReturnsCorrectly() throws Exception{
        Patient patient = setupNewPatient();
        assertEquals("29", patient.getNationalId());
    }
    @Test
    public void getDateTreatedReturnsDateTreatedCorrectly() throws Exception{
        Patient patient = setupNewPatient();
        assertEquals("12", patient.getDateTreated());
    }
    @Test
    public  void  getInfectionReturnsInfectionCorrectly() throws Exception{
        Patient patient = setupNewPatient();
        assertEquals("homa", patient.getInfection());
    }
    @Test
    public void getTelReturnsTelCorrectly() throws Exception{
        Patient patient = setupNewPatient();
        assertEquals("17", patient.getTel());
    }
    public void getAmountReturnsCorrectly() throws Exception{
        Patient patient = setupNewPatient();
        assertEquals("20", patient.getAmount());
    }
    @Test
    public void getOfficerIdReturnsCorrectly() throws Exception{
        Patient patient = setupNewPatient();
        assertEquals(1, patient.getOfficerId());
    }

    //helper methods
    public Patient setupNewPatient(){
        return new Patient("tito","29","12","homa","17","20",1);
    }

}
