package dao;


import models.Patient;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;


public  class Sql2oPatientDao implements PatientDao {

    private final Sql2o sql2o;

    public Sql2oPatientDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    @Override
    public void add(Patient patient) {
        String sql = "INSERT INTO patients (name, nationalid, datetreated, infection, tel, amount, officerid) VALUES (:name, :nationalId, :dateTreated, :infection, :tel, :amount, :officerId)"; //raw sql
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(patient)
                    .executeUpdate()
                    .getKey();
            patient.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Patient> getAll() {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT from patients")
                    .executeAndFetch(Patient.class);

        }
    }

    @Override
    public Patient findById(int id) {
        try (Connection con = sql2o.open()){
            return con.createQuery("SELECT from patients WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Patient.class);

        }
    }

    @Override
    public void update(int id, String name, String nationalId, String dateTreated, String infection, String tel, String amount, int officerId) {
        String sql = "UPDATE patients SET (name, nationalId, dateTreated, infection, tel, amount, officerId) = (:name, :nationalId, :dateTreated, :infection, :tel, :amount, :officerId) WHERE id=:id";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("nationalId", nationalId)
                    .addParameter("dateTreated", dateTreated)
                    .addParameter("infection", infection)
                    .addParameter("tel", tel)
                    .addParameter("amount", amount)
                    .addParameter("officerId", officerId)
                    .addParameter("id", id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }

    }
    @Override
    public void deleteById(int id) {
    String sql = "DELETE from patients WHERE id = :id";
    try (Connection con = sql2o.open()){
        con.createQuery(sql)
                .addParameter("id", id)
                .executeUpdate();
    }catch (Sql2oException ex){
        System.out.println(ex);
    }
    }

    @Override
    public void clearAllPatient() {
        String sql = " DELETE from patients";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}
