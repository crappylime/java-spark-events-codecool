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
        Map params = new HashMap<>();
        params.put("eventContainer", events);
        return new ModelAndView(params, "event/index");
    }

    public static ModelAndView addEvent(Request req, Response res) {
        String name = req.queryParams("name");
        String description = req.queryParams("description");
        String stringDate = req.queryParams("date");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-mm-dd'T'HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(stringDate);
        }
        catch (ParseException ex) {
            System.out.println("Exception "+ex);
        }
        String category = req.queryParams("category");
        Event event = new Event(name, description, date, category);
        eventDao.add(event);
        res.redirect("/");
        return null;
    }
}
