package com.gotomypub.networkutilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by rohitd061 on 22/01/2018.
 */

public class ApiResponse{

    @SerializedName("IsStatus")
    @Expose
    public Boolean isStatus;
    @SerializedName("StatusMessage")
    @Expose
    public String statusMessage;
    public Boolean getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Boolean isStatus) {
        this.isStatus = isStatus;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }


}
