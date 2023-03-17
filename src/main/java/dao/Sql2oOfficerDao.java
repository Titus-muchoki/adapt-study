package dao;

import models.Officer;
import models.Patient;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oOfficerDao implements OfficerDao {
    private final Sql2o sql2o;

    public Sql2oOfficerDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public void add(Officer category) {
        String sql = "INSERT INTO officers (name) VALUES (:name)"; //raw sql
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(category)
                    .executeUpdate()
                    .getKey();
            category.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Patient> getAllPatientByOfficer(int officerId) {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT from patients WHERE officerId = :officerId")
                    .addParameter("officerId", officerId)
                    .executeAndFetch(Patient.class);

        }
    }

    @Override
    public List<Officer> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM officers")
                    .executeAndFetch(Officer.class);
        }
    }

    @Override
    public Officer findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM officers WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Officer.class);
        }
    }


    @Override
    public void update(int id, String newName) {
        String sql = "UPDATE officers SET name = :name WHERE id = :id";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("id", id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }


    }

    @Override
    public void deleteById(int id) {
     String sql = "DELETE from officers WHERE id = :id";
     try(Connection con = sql2o.open()) {
         con.createQuery(sql)
                 .addParameter("id", id)
                 .executeUpdate();
     }catch (Sql2oException ex){
         System.out.println(ex);
     }
    }

    @Override
    public void clearAllOfficers() {
        String sql = " DELETE from officers";
    try (Connection con = sql2o.open()){
        con.createQuery(sql)
                .executeUpdate();
    }catch (Sql2oException ex){
        System.out.println(ex);
    }
    }
}
