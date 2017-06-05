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
    List<Event> events;

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

    public EventDaoSqlite() {
//        events = new ArrayList<>();
//        hackathon = new Event(1, "hackathon", "24h of work", new Date(), "game");
//        Event checkpoint = new Event(2, "checkpoint", "scary day", new Date(), "exam");
//        events.add(hackathon);
//        events.add(checkpoint);
        this.connection = connectToDb();
    }

        @Override
        public List<Event> getAll() {
        List<Event> events = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM events");
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
}
