package dao;

import model.Event;

import java.util.List;

/**
 * Created by rafalstepien on 28/04/2017.
 */
public interface EventDao {
    void add(Event event);

    Event find(int id);

    boolean delete(int id);

    List<Event> getAll();

    boolean update(Event event);
}
