package controller;

import dao.EventDao;
import dao.EventDaoSqlite;
import model.Event;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rafalstepien on 28/04/2017.
 */
public class EventController {
    public static ModelAndView renderProducts(Request req, Response res) {
        //Get events from database by Dao
        EventDao eventDao = new EventDaoSqlite();
        List<Event> events = eventDao.getAll();

        Map params = new HashMap<>();
        params.put("eventContainer", events);
        return new ModelAndView(params, "product/index");
    }
}
