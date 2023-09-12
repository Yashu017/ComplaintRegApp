package my.app.complainregapp;

import java.io.Serializable;

public class Complain implements Serializable {


    private String time, time1;

    private String status;
    private String title;
    private String description;
    private String location;
    private String to, from;
    private String complaint;
    private String type;


    private String _id;

    public Complain(String title,String status, String _id, String time,String time1,String description,String location,String to,String from,String complaint,String type) {
        this.time = time;
        this.time1 = time1;
        this.status = status;
        this.title = title;
        this.description = description;
        this.location = location;
        this.to = to;
        this.type=type;
        this.from = from;
        this.complaint = complaint;
        this._id = _id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
