package com.gotomypub.networkutilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PubApiDetailsResponse extends ApiResponse {
    @SerializedName("Data")
    @Expose
    private Datum data = null;

    public Datum getData() {
        return data;
    }

    public void setData(Datum data) {
        this.data = data;
    }
}
