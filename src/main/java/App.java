import dao.Sql2oCategoryDao;
import dao.Sql2oViralDao;
import org.sql2o.Sql2o;

import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

public class App {
    public static void main(String[] args) { //type “psvm + tab” to autocreate this
        port(8090);
        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/viralloads";      //connect to todolist, not todolist_test!

        Sql2o sql2o = new Sql2o(connectionString, "kajela", "8444");
        Sql2oViralDao viralDao = new Sql2oViralDao(sql2o);
        Sql2oCategoryDao categoryDao = new Sql2oCategoryDao(sql2o);
    }
    }
