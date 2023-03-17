package dao;

import models.Officer;
import models.Patient;


import java.util.List;

public interface OfficerDao {
    //LIST
    //CREATE
    void add(Officer officer);

    //READ
    List<Officer>getAll();
    List<Patient> getAllPatientByOfficer(int officerId);

    Officer findById(int id);

    //UPDATE

    void update( int id, String name);

    //DELETE
    void deleteById(int id);
    void clearAllOfficers();
}
