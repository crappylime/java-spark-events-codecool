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
        port(8888);
        enableDebugScreen();

        // routes
        get("/", (Request req, Response res) -> {
            res.redirect("/events");
            return null;
        });
        get("/events", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.renderEvents(req, res));
        });
        post("/events", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.addEvent(req, res));
        });
        get("/events/:id/show", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render(EventController.showEvent(req, res));
        });
    }


}
