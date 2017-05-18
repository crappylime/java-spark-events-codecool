package model;

import java.util.Date;

/**
 * Created by crappy on 12.05.17.
 */
public class Event {
    private Integer id;
    private String name;
    private String description;
    private Date date;
    private String category;

    public Event(String name, String description, Date date, String category) {
        this.setName(name);
        this.setDescription(description);
        this.setDate(date);
        this.setCategory(category);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
