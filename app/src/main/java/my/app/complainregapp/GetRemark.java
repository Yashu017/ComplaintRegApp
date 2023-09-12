package my.app.complainregapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

class GetRemark {

    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("lastAction")
    private
    ArrayList<RemarkItem> remark;

    public ArrayList<RemarkItem> getRemark() {
        return remark;
    }

}
