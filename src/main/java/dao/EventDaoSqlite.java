package dao;

import model.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by lama on 17.05.17, 00:37.
 */
public class EventDaoSqlite extends BaseDao implements EventDao {
//    List<Event> events;
//
//    public EventDaoSqlite() {
//        events = new ArrayList<>();
//        Event hackathon = new Event(0, "hackathon", "24h of work", new Date(), "game");
//        Event checkpoint = new Event(1, "checkpoint", "scary day", new Date(), "exam");
//        events.add(hackathon);
//        events.add(checkpoint);
//    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();

        try {
            PreparedStatement statement = this.getConnection().prepareStatement("SELECT * FROM events");
            events = this.getEvents(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

    private List<Event> getEvents(PreparedStatement statement) throws SQLException {
        List<Event> events = new ArrayList<Event>();

        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            Event event = new Event(
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("date"),
                    rs.getString("category")
            );
            event.setId(rs.getInt("id"));
            events.add(event);
        }

        return events;
    }
}

