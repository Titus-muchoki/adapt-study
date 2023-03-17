package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OfficerTest {
    @Before
    public void setup() throws Exception{

    }
    @After
    public void tearDown() throws Exception{

    }
    @Test
    public void getNameReturnsCorrectly()throws Exception{
        Officer officer = setupOfficer();
        assertEquals("kajela", officer.getName());
    }
    @Test
    public void setOfficerSetsOfficerCorrectly()throws Exception{
        Officer officer = setupOfficer();
        officer.setName("joy");
        assertEquals("joy", officer.getName());
    }

    //HELPER
    public Officer setupOfficer(){
        return new Officer("kajela");
    }
}
