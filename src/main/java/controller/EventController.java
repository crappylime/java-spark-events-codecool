package controller;

import dao.EventDao;
import dao.EventDaoSqlite;
import model.Event;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventController {
    static EventDao eventDao = new EventDaoSqlite();

    public static ModelAndView renderEvents(Request req, Response res) {
        List<Event> events = eventDao.getAll();
        List<String> categories = eventDao.getCategories();
        Map params = new HashMap<>();

        if (req.queryParams("eventName") != null) {
            System.out.println("Filter by name");
            events = eventDao.getByEventName(req.queryParams("eventName"));
        }
        if (req.queryParams("category") != null) {
            System.out.println("Filter by category");
            events = eventDao.getByCategory(req.queryParams("category"));
        }
        params.put("events", events);
        params.put("categories", categories);
        return new ModelAndView(params, "event/index");
    }

    public static ModelAndView renderEvent(Request req, Response res) {
        Map params = new HashMap<>();
        if (req.params(":id") != null) {
            Integer eventId = Integer.valueOf(req.params(":id"));
            Event event = eventDao.find(eventId);
            params.put("event", event);
        }
        return new ModelAndView(params, "event/form");
    }

    public static ModelAndView addEvent(Request req, Response res) {
        String name = req.queryParams("name");
        String description = req.queryParams("description");
        String stringDate = req.queryParams("date");
        System.out.println(stringDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(stringDate);
        } catch (ParseException ex) {
            System.out.println("Exception " + ex);
        }
        String category = req.queryParams("category");
        Event event = new Event(name, description, date, category);
        eventDao.add(event);
        res.redirect("/events");
        return null;
    }

    public static ModelAndView editEvent(Request req, Response res) {
        Integer eventId = Integer.valueOf(req.params(":id"));
        Event event = eventDao.find(eventId);
        event.setName(req.queryParams("name"));
        event.setDescription(req.queryParams("description"));
        String stringDate = req.queryParams("date");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(stringDate);
        } catch (ParseException ex) {
            System.out.println("Exception " + ex);
        }
        event.setDate(date);
        event.setCategory(req.queryParams("category"));
        eventDao.update(event);
        res.redirect("/events");
        return null;
    }

    public static ModelAndView showEvent(Request req, Response res) {
        Integer eventId = Integer.valueOf(req.params(":id"));
        Event event = eventDao.find(eventId);
        Map params = new HashMap<>();
        params.put("event", event);
        return new ModelAndView(params, "event/show");
    }

    public static ModelAndView renderConfirmationWindow(Request req, Response res) {
        Integer eventId = Integer.valueOf(req.params(":id"));
        Event event = eventDao.find(eventId);
        Map params = new HashMap<>();
        params.put("event", event);
        return new ModelAndView(params, "event/delete");
    }

    public static ModelAndView deleteEvent(Request req, Response res) {
        Integer eventId = Integer.valueOf(req.params(":id"));
        eventDao.delete(eventId);
        res.redirect("/events");
        return null;
    }
}
