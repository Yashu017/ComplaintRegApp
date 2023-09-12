package my.app.complainregapp.model;

public class UserComplaint {

    private int id;
    private String description, location,branchForward,branch,type;
    private String complaint_id;
    private String token;
    private String ErrorCode;

    public UserComplaint(int id, String description, String location, String branchForward, String branch, String complaint_id, String token, String errorCode,String type) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.branchForward = branchForward;
        this.branch = branch;
        this.complaint_id = complaint_id;
        this.token = token;
       this.ErrorCode = errorCode;
       this.type=type;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBranchForward() {
        return branchForward;
    }

    public void setBranchForward(String branchForward) {
        this.branchForward = branchForward;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }
}





