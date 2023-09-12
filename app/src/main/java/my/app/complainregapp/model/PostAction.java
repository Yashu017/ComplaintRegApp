package my.app.complainregapp.model;

public class PostAction {

    private String complID,action,from,to,message,remark;
    private String target;
    public PostAction(String complID, String action, String from, String to,String message,String target,String remark) {
        this.complID=complID;
        this.message=message;
        this.action = action;
        this.from = from;
        this.to = to;
        this.target=target;
        this.remark=remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComplID() {
        return complID;
    }

    public void setComplID(String complID) {
        this.complID = complID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
