import controller.EventController;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static spark.Spark.*;
import static spark.Spark.get;

/**
 * Created by lama on 17.05.17, 17:15.
 */
public class Application {
    private static Application app;
    private Connection connection;

    public Application() {
        System.out.println("Initializing application...");

        try {
            this.connectToDb();
            this.createTables();
            exception(Exception.class, (e, req, res) -> e.printStackTrace());
            staticFileLocation("/public");
            port(8888);
            this.routes();
        } catch (SQLException e) {
            System.out.println("Application initialization failed...");
            e.printStackTrace();
        }
        app = this;
    }

    public Connection getConnection() {
        return connection;
    }

    public static Application getApp() {
        return app;
    }

    private void createTables() throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS events\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR NOT NULL,\n" +
                "    description TEXT,\n" +
                "    date DATE NOT NULL\n" +
                ")");
    }

    private void connectToDb() throws SQLException {
        System.out.println("Connection to DB...");
        this.connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database.db");
    }

    private void routes() {
        // Always start with more specific routes
        get("/hello", (req, res) -> "Hello World");

        // Always add generic routes to the end
        get("/", EventController::renderProducts, new ThymeleafTemplateEngine());

        // Equivalent with above
        get("/index", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( EventController.renderProducts(req, res) );
        });
    }
}
