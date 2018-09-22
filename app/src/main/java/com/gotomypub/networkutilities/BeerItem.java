package com.gotomypub.networkutilities;

import com.google.gson.annotations.SerializedName;

public class BeerItem {

    @SerializedName("item_id")
    String itemID;
    @SerializedName("url")
    String url;
    @SerializedName("name")
    String name;

    boolean isSelected;

    public BeerItem getNewCopy(BeerItem beerItem){
        BeerItem newItem=new BeerItem();
        newItem.setItemID(beerItem.getItemID());
        newItem.setUrl(beerItem.getUrl());
        newItem.setSelected(beerItem.isSelected);
        newItem.setName(beerItem.name);
        return newItem;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
