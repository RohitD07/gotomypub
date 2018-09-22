package com.gotomypub.networkutilities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PubFeatureResponse extends ApiResponse {
    @SerializedName("Data")
    ArrayList<PubFeatureItem> pubFeatureItemArrayList;

    public ArrayList<PubFeatureItem> getPubFeatureItemArrayList() {
        return pubFeatureItemArrayList;
    }

    public void setPubFeatureItemArrayList(ArrayList<PubFeatureItem> pubFeatureItemArrayList) {
        this.pubFeatureItemArrayList = pubFeatureItemArrayList;
    }
}
