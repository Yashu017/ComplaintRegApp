package my.app.complainregapp;

class ComplainHistoryItem {

    private String _id;
    private long time, time1;
    private Integer action;
    private String to, from;

    public ComplainHistoryItem(String _id, long time, long time1, Integer action, String to, String from) {
        this._id = _id;
        this.time = time;
        this.time1 = time1;
        this.action = action;
        this.to = to;
        this.from = from;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime1() {
        return time1;
    }

    public void setTime1(long time1) {
        this.time1 = time1;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
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
