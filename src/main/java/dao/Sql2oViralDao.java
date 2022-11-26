package dao;

import models.Viral;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;


public  class Sql2oViralDao implements ViralDao {

    private final Sql2o sql2o;

    public Sql2oViralDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public void add(Viral viral) {
        String sql = "INSERT INTO infections (description, categoryId) VALUES (:description,:categoryId)"; //raw sql
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(viral)
                    .executeUpdate()
                    .getKey();
            viral.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public List<Viral> getAll() {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT from infections")
                    .executeAndFetch(Viral.class);

        }
    }

    @Override
    public Viral findById(int id) {
        try (Connection con = sql2o.open()){
            return con.createQuery("SELECT from infections WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Viral.class);

        }
    }

    @Override
    public void update( int id, String newDescription, int newCategoryId) {
        String sql = "UPDATE infections SET (description, categoryId) = (:description, :categoryId) WHERE id=:id";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("description", newDescription)
                    .addParameter("categoryId", newCategoryId)
                    .addParameter("id", id);
        }catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int id) {
    String sql = "DELETE from infections WHERE id = :id";
    try (Connection con = sql2o.open()){
        con.createQuery(sql)
                .addParameter("id", id)
                .executeUpdate();
    }catch (Sql2oException ex){
        System.out.println(ex);
    }
    }

    @Override
    public void clearAllViral() {
    String sql = " DELETE from infections";
    try (Connection con = sql2o.open()){
        con.createQuery(sql)
                .executeUpdate();
    }catch (Sql2oException ex){
        System.out.println(ex);
    }
    }

}
