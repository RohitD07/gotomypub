package com.gotomypub;

import android.app.Application;

import com.gotomypub.networkutilities.BeerItem;
import com.gotomypub.networkutilities.PubFeatureItem;

import java.util.ArrayList;

public class MyPubApplication extends Application {
    ArrayList<BeerItem> beerItemArrayList;
    ArrayList<PubFeatureItem> pubFeatureItemArrayList;

    public ArrayList<BeerItem> getBeerItemArrayList() {
        return beerItemArrayList;
    }

    public void setBeerItemArrayList(ArrayList<BeerItem> beerItemArrayList) {
        this.beerItemArrayList = beerItemArrayList;
    }

    public ArrayList<PubFeatureItem> getPubFeatureItemArrayList() {
        return pubFeatureItemArrayList;
    }

    public void setPubFeatureItemArrayList(ArrayList<PubFeatureItem> pubFeatureItemArrayList) {
        this.pubFeatureItemArrayList = pubFeatureItemArrayList;
    }
}
