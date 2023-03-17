package dao;

import models.Officer;
import models.Patient;


import java.util.List;

public interface OfficerDao {
    //LIST
    List<Patient> getAllPatientByOfficer(int officerId);

    List<Officer>getAll();

    //CREATE

    void add(Officer category);

    //READ

    Officer findById(int id);

    //UPDATE

    void update( int id, String name);

    //DELETE
    void deleteById(int id);
    void clearAllOfficers();
}
