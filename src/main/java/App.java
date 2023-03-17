import dao.Sql2oOfficerDao;
import dao.Sql2oPatientDao;
import models.Officer;
import models.Patient;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) { //type “psvm + tab” to autocreate this
//        port(8090);
        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/cheboin";      //connect to todolist, not todolist_test!

        Sql2o sql2o = new Sql2o(connectionString, "kajela", "8444");
        Sql2oPatientDao patientDao = new Sql2oPatientDao(sql2o);
        Sql2oOfficerDao officerDao = new Sql2oOfficerDao(sql2o);

        //get: show all infections in all categories and show all categories

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Officer> allOfficers = officerDao.getAll();
            model.put("officers", allOfficers);
            List<Patient> patients = patientDao.getAll();
            model.put("patients", patients);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to create a new category

        get("/officers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Officer> officers = officerDao.getAll(); //refresh list of links for navbar
            model.put("officers", officers);
            return new ModelAndView(model, "officer-form.hbs"); //new layout
        }, new HandlebarsTemplateEngine());

      //post: process a form to create a new category

        post("/officers", (req, res) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = req.queryParams("name");
            Officer newOfficer = new Officer(name);
            officerDao.add(newOfficer);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: delete all categories and all tests

        get("/officers/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            officerDao.clearAllOfficers();
            patientDao.clearAllPatient();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

//        //get: delete all infections

        get("/patients/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            patientDao.clearAllPatient();
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

//        //get a specific category (and the viral tests it contains)
        get("/officers/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfOfficerToFind = Integer.parseInt(req.params("id")); //new
            Officer foundOfficer = officerDao.findById(idOfOfficerToFind);
            model.put("officer", foundOfficer);
            List<Patient> allPatientByOfficers = officerDao.getAllPatientByOfficer(idOfOfficerToFind);
            model.put("patients", allPatientByOfficers);
            model.put("officers", officerDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "officer-detail.hbs"); //new
        }, new HandlebarsTemplateEngine());

//        //get: show a form to update a category

        get("/officers/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editOfficer", true);
            Officer officer = officerDao.findById(Integer.parseInt(req.params("id")));
            model.put("officer", officer);
            model.put("officers", officerDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "officer-form.hbs");
        }, new HandlebarsTemplateEngine());

//        //post: process a form to update a category

        post("/officers/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfOfficerToEdit = Integer.parseInt(req.params("id"));
            String newName = req.queryParams("newOfficerName");
            officerDao.update(idOfOfficerToEdit, newName);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

//        //get: delete an individual viral test

        get("/officers/:officer_id/patients/:patient_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPatientToDelete = Integer.parseInt(req.params("patient_id"));
            patientDao.deleteById(idOfPatientToDelete);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

        //get: show new viral form
        get("/patients/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Officer> officers = officerDao.getAll();
            model.put("officers", officers);
            return new ModelAndView(model, "patient-form.hbs");
        }, new HandlebarsTemplateEngine());

//        //task: process new viral form
        post("/patients", (req, res) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            List<Officer> allOfficers = officerDao.getAll();
            model.put("officers", allOfficers);
            String name = req.queryParams("name");
            String nationalId = req.queryParams("nationalId");
            String dateTreated = req.queryParams("dateTreated");
            String infection = req.queryParams("infection");
            String tel = req.queryParams("tel");
            String amount = req.queryParams("amount");
            int officerId = Integer.parseInt(req.queryParams("officerId"));
            Patient newPatient = new Patient(name,nationalId,dateTreated,infection,tel,amount,officerId);        //See what we did with the hard coded categoryId?
            patientDao.add(newPatient);
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());

//        //get: show an individual viral test that is nested in a category

        get("/officers/:officer_id/patients/:patient_id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPatientToFind = Integer.parseInt(req.params("patient_id")); //pull id - must match route segment
            Patient foundPatient = patientDao.findById(idOfPatientToFind); //use it to find task
            int idOfOfficerToFind = Integer.parseInt(req.params("officer_id"));
            Officer foundOfficer = officerDao.findById(idOfOfficerToFind);
            model.put("officer", foundOfficer);
            model.put("patient", foundPatient); //add it to model for template to display
            model.put("officers", officerDao.getAll()); //refresh list of links for navbar
            return new ModelAndView(model, "patient-detail.hbs"); //individual task page.
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a viral test

        get("/patients/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Officer> allOfficers = officerDao.getAll();
            model.put("officers", allOfficers);
            Patient patient = patientDao.findById(Integer.parseInt(req.params("id")));
            model.put("patient", patient);
            model.put("editPatient", true);
            return new ModelAndView(model, "patient-form.hbs");
        }, new HandlebarsTemplateEngine());

        //task: process a form to update a test
        post("/patients/:id", (req, res) -> { //URL to update task on POST route
            Map<String, Object> model = new HashMap<>();
            int patientToEditId = Integer.parseInt(req.params("id"));
            String name = req.queryParams("name");
            String nationalId = req.queryParams("nationalId");
            String dateTreated = req.queryParams("dateTreated");
            String infection = req.queryParams("infection");
            String tel = req.queryParams("tel");
            String amount = req.queryParams("amount");
            int newOfficerId = Integer.parseInt(req.queryParams("officerId"));
            patientDao.update(patientToEditId,name,nationalId,dateTreated,infection,tel,amount,newOfficerId);  // remember the hardcoded categoryId we placed? See what we've done to/with it?
            res.redirect("/");
            return null;
        }, new HandlebarsTemplateEngine());
        get("/patients", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Patient> allPatients = patientDao.getAll();
            model.put("patients", allPatients);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
