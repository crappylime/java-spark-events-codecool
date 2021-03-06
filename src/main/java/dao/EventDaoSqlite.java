package dao;

import model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lama on 17.05.17, 00:37.
 */
public class EventDaoSqlite implements EventDao {
    private Connection connection;

    public EventDaoSqlite() {
        this.connection = connectToDb();
        createTable();
    }

    private Connection connectToDb() {
        System.out.println("Connection to Db...");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database");
        } catch (SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }
        return connection;
    }

    private void createTable() {
        System.out.println("Checking if table events was created...");
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS events\n" +
                            "(\n" +
                            "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "    name VARCHAR(46) NOT NULL,\n" +
                            "    description VARCHAR(46),\n" +
                            "    date DATE NOT NULL,\n" +
                            "    category VARCHAR(46) NOT NULL\n" +
                            ")"
            );
        } catch (SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM events");
            events = getEvents(statement);
        } catch (SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }
        return events;
    }

    @Override
    public boolean update(Event event) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE events SET name=?, description=?, date=?, category=? WHERE id=?");
            statement.setString(1, event.getName());
            statement.setString(2, event.getDescription());
            statement.setDate(3, new java.sql.Date(event.getDate().getTime()));
            statement.setString(4, event.getCategory());
            statement.setInt(5, event.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public void add(Event event) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO events (name, description, date, category) VALUES (?, ?, ?, ?)");
            statement.setString(1, event.getName());
            statement.setString(2, event.getDescription());
            statement.setDate(3, new java.sql.Date(event.getDate().getTime()));
            statement.setString(4, event.getCategory());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Event find(int id) {
        Event event = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM events WHERE id=(?)");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                event = new Event(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("date"),
                        rs.getString("category")
                );
                event.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }
        return event;
    }

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM events WHERE id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<Event> getByEventName(String name) {
        List<Event> events = new ArrayList<>();

        try {
            PreparedStatement statement = connection.
                    prepareStatement("SELECT * FROM events WHERE name LIKE (?)");
            statement.setString(1, "%" + name + "%");
            events = getEvents(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

    @Override
    public List<Event> getByCategory(String category) {
        List<Event> events = new ArrayList<>();

        try {
            PreparedStatement statement = connection.
                    prepareStatement("SELECT * FROM events WHERE category LIKE (?)");
            statement.setString(1, "%" + category + "%");
            events = getEvents(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public List<String> getCategories() {
        List<Event> events = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT category FROM events ORDER BY category");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String category = rs.getString("category");
                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println("Connect to DB failed");
            System.out.println(e.getMessage());
        }
        return categories;
    }

    private List<Event> getEvents(PreparedStatement statement) {
        List<Event> events = new ArrayList<>();

        try {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Event event = new Event(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("date"),
                        rs.getString("category")
                );
                event.setId(rs.getInt("id"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}
