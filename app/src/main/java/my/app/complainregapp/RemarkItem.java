package my.app.complainregapp;

class RemarkItem {
    private String _id,remark;
    private long time, time1;
    private Integer action;
    private String to, from,target;


    public RemarkItem(String _id, String remark, long time, long time1, Integer action, String to, String from,String target
    ) {
        this._id = _id;
        this.remark = remark;
        this.time = time;
        this.target=target;
        this.time1 = time1;
        this.action = action;
        this.to = to;
        this.from = from;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
