package dao;

import model.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lama on 17.05.17, 00:37.
 */
public class EventDaoSqlite implements EventDao {
    private final Event hackathon;
    List<Event> events;

    public EventDaoSqlite() {
        events = new ArrayList<>();
        hackathon = new Event(1, "hackathon", "24h of work", new Date(), "game");
        Event checkpoint = new Event(2, "checkpoint", "scary day", new Date(), "exam");
        events.add(hackathon);
        events.add(checkpoint);
    }

    @Override
    public boolean add(Event event) {
        return events.add(event);
    }

    @Override
    public Event find(int id) {
        return hackathon;
    }

    @Override
    public List<Event> getAll() {
        return events;
    }
}
