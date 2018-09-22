package com.gotomypub.networkutilities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BeerItemResponse extends ApiResponse {
    public ArrayList<BeerItem> getbeerItemArrayList() {
        return beerItemArrayList;
    }

    public void setbeerItemArrayList(ArrayList<BeerItem> pubFeatureItemArrayList) {
        this.beerItemArrayList = pubFeatureItemArrayList;
    }

    @SerializedName("Data")
    ArrayList<BeerItem> beerItemArrayList;
}
