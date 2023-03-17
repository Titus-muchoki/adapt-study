//import dao.Sql2oOfficerDao;
//import dao.Sql2oPatientDao;
//import models.Officer;
//import models.Viral;
//import org.sql2o.Sql2o;
//import spark.ModelAndView;
//import spark.template.handlebars.HandlebarsTemplateEngine;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static spark.Spark.*;
//
//public class App {
//    public static void main(String[] args) { //type “psvm + tab” to autocreate this
////        port(8090);
//        staticFileLocation("/public");
//        String connectionString = "jdbc:postgresql://localhost:5432/viralloads";      //connect to todolist, not todolist_test!
//
//        Sql2o sql2o = new Sql2o(connectionString, "kajela", "8444");
//        Sql2oPatientDao viralDao = new Sql2oPatientDao(sql2o);
//        Sql2oOfficerDao categoryDao = new Sql2oOfficerDao(sql2o);
//
//        //get: show all infections in all categories and show all categories
//
//        get("/", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Officer> allCategories = categoryDao.getAll();
//            model.put("categories", allCategories);
//            List<Viral> infections = viralDao.getAll();
//            model.put("infections", infections);
//            return new ModelAndView(model, "index.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //get: show a form to create a new category
//
//        get("/categories/new", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Officer> categories = categoryDao.getAll(); //refresh list of links for navbar
//            model.put("categories", categories);
//            return new ModelAndView(model, "category-form.hbs"); //new layout
//        }, new HandlebarsTemplateEngine());
//
//      //post: process a form to create a new category
//
//        post("/categories", (req, res) -> { //new
//            Map<String, Object> model = new HashMap<>();
//            String name = req.queryParams("name");
//            Officer newCategory = new Officer(name);
//            categoryDao.add(newCategory);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
//        //get: delete all categories and all tests
//
//        get("/categories/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            categoryDao.clearAllCategories();
//            viralDao.clearAllViral();
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
////        //get: delete all infections
//
//        get("/infections/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            viralDao.clearAllViral();
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
////        //get a specific category (and the viral tests it contains)
//        get("/categories/:id", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfCategoryToFind = Integer.parseInt(req.params("id")); //new
//            Officer foundCategory = categoryDao.findById(idOfCategoryToFind);
//            model.put("category", foundCategory);
//            List<Viral> allViralByCategory = categoryDao.getAllViralByCategory(idOfCategoryToFind);
//            model.put("infections", allViralByCategory);
//            model.put("categories", categoryDao.getAll()); //refresh list of links for navbar
//            return new ModelAndView(model, "category-detail.hbs"); //new
//        }, new HandlebarsTemplateEngine());
//
////        //get: show a form to update a category
//
//        get("/categories/:id/edit", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            model.put("editCategory", true);
//            Officer category = categoryDao.findById(Integer.parseInt(req.params("id")));
//            model.put("category", category);
//            model.put("categories", categoryDao.getAll()); //refresh list of links for navbar
//            return new ModelAndView(model, "category-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
////        //post: process a form to update a category
//
//        post("/categories/:id", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfCategoryToEdit = Integer.parseInt(req.params("id"));
//            String newName = req.queryParams("newCategoryName");
//            categoryDao.update(idOfCategoryToEdit, newName);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
////        //get: delete an individual viral test
//
//        get("/categories/:category_id/infections/:viral_id/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfViralToDelete = Integer.parseInt(req.params("viral_id"));
//            viralDao.deleteById(idOfViralToDelete);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
//        //get: show new viral form
//        get("/infections/new", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Officer> categories = categoryDao.getAll();
//            model.put("categories", categories);
//            return new ModelAndView(model, "viral-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
////        //task: process new viral form
//        post("/infections", (req, res) -> { //URL to make new task on POST route
//            Map<String, Object> model = new HashMap<>();
//            List<Officer> allCategories = categoryDao.getAll();
//            model.put("categories", allCategories);
//            String description = req.queryParams("description");
//            int categoryId = Integer.parseInt(req.queryParams("categoryId"));
//            Viral newViral = new Viral(description, categoryId);        //See what we did with the hard coded categoryId?
//            viralDao.add(newViral);
////            List<Task> tasksSoFar = taskDao.getAll();
////            for (Task taskItem: tasksSoFar
////                 ) {
////                System.out.println(taskItem);
////            }
////            System.out.println(tasksSoFar);
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//
////        //get: show an individual viral test that is nested in a category
//
//        get("/categories/:category_id/infections/:viral_id", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfViralToFind = Integer.parseInt(req.params("viral_id")); //pull id - must match route segment
//            Viral foundViral = viralDao.findById(idOfViralToFind); //use it to find task
//            int idOfCategoryToFind = Integer.parseInt(req.params("category_id"));
//            Officer foundCategory = categoryDao.findById(idOfCategoryToFind);
//            model.put("category", foundCategory);
//            model.put("viral", foundViral); //add it to model for template to display
//            model.put("categories", categoryDao.getAll()); //refresh list of links for navbar
//            return new ModelAndView(model, "viral-detail.hbs"); //individual task page.
//        }, new HandlebarsTemplateEngine());
//
//        //get: show a form to update a viral test
//
//        get("/infections/:id/edit", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            List<Officer> allCategories = categoryDao.getAll();
//            model.put("categories", allCategories);
//            Viral viral = viralDao.findById(Integer.parseInt(req.params("id")));
//            model.put("viral", viral);
//            model.put("editViral", true);
//            return new ModelAndView(model, "viral-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        //task: process a form to update a test
//        post("/infections/:id", (req, res) -> { //URL to update task on POST route
//            Map<String, Object> model = new HashMap<>();
//            int viralToEditId = Integer.parseInt(req.params("id"));
//            String newContent = req.queryParams("description");
//            int newCategoryId = Integer.parseInt(req.queryParams("categoryId"));
//            viralDao.update(viralToEditId, newContent, newCategoryId);  // remember the hardcoded categoryId we placed? See what we've done to/with it?
//            res.redirect("/");
//            return null;
//        }, new HandlebarsTemplateEngine());
//    }
//}
