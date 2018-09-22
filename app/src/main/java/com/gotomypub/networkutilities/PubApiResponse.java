
package com.gotomypub.networkutilities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PubApiResponse extends ApiResponse{

    @SerializedName("Data")
    @Expose
    private List<Datum> data = null;


    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
