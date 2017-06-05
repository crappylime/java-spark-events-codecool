import controller.EventController;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;


public class Main {

    public static void main(String[] args) {
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);
        enableDebugScreen();

//        try {
//            Connection connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database");
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(
//                    "CREATE TABLE IF NOT EXISTS events\n" +
//                            "(\n" +
//                            "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//                            "    name VARCHAR(46) NOT NULL,\n" +
//                            "    description VARCHAR(46),\n" +
//                            "    date DATE NOT NULL,\n" +
//                            "    category VARCHAR(46) NOT NULL\n" +
//                            ")"
//            );
//        } catch (SQLException e) {
//            System.out.println("Connect to DB failed");
//            System.out.println(e.getMessage());
//        }

        // routes
        get("/", (Request req, Response res) -> {
            res.redirect("/events");
            return null;
        });
        get("/events", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.renderEvents(req, res));
        });
        get("/events/new", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.renderEvent(req, res));
        });
        post("/events/new", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.addEvent(req, res));
        });
        get("/events/:id/show", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.showEvent(req, res));
        });
        get("/events/:id/edit", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.renderEvent(req, res));
        });
        post("/events/:id/edit", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.editEvent(req, res));
        });
        get("/events/:id/delete", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.renderConfirmationWindow(req, res));
        });
        post("/events/:id/delete", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.deleteEvent(req, res));
        });
    }


}
