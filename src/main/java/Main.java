import controller.EventController;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;


public class Main {

    public static void main(String[] args) {
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(getHerokuAssignedPort());
        enableDebugScreen();

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

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
