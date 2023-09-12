package my.app.complainregapp;

class ComplainHistory {

    private String time, time1;
    private String _id;
    private String action;
    private String to,from;

    public ComplainHistory(String time, String time1, String _id, String action, String to, String from) {
        this.time = time;
        this.time1 = time1;
        this._id = _id;
        this.action = action;
        this.to = to;
        this.from = from;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
}
