package my.app.complainregapp.model;

import my.app.complainregapp.ComplaintItemAdmin;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetComplaintAdmin {
    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("complaintsAdmin")
    ArrayList<ComplaintItemAdmin> complaintAdmin;

    public ArrayList<ComplaintItemAdmin> getComplaintAdmin() {
        return complaintAdmin;
    }
}