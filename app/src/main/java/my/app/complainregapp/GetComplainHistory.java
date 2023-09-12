package my.app.complainregapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

class GetComplainHistory {

    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("complaintHistory")
    private
    ArrayList<ComplainHistoryItem> complainHistory;

    public ArrayList<ComplainHistoryItem> getComplainHistory() {
        return complainHistory;
    }

}
