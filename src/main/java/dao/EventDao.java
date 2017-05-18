package dao;

import model.Event;

import java.util.List;

/**
 * Created by rafalstepien on 28/04/2017.
 */
public interface EventDao {
    boolean add(Event event);

    List<Event> getAll();
}
