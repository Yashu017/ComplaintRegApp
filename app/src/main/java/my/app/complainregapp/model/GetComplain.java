package my.app.complainregapp.model;

import my.app.complainregapp.ComplainItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetComplain {
    @SerializedName("errorCode")
private int errorCode;

    @SerializedName("complaints")
    private
    ArrayList<ComplainItem> complain;

    public ArrayList<ComplainItem> getComplain() {
        return complain;
    }
}