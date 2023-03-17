package dao;

import models.Patient;


import java.util.List;

public interface PatientDao {
    //LIST

    List<Patient> getAll();

    //CREATE

    void add(Patient patient);

    //READ

    Patient findById(int id);

    //UPDATE

    void update( int id, String name, String nationalId, String dateTreated, String infection, String tel, String amount, int officerId);

    //DELETE

    void deleteById(int id);

//    void clearAllPatient(int id);

    void clearAllPatient();

}
