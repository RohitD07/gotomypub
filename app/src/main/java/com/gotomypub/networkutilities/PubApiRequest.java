package com.gotomypub.networkutilities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PubApiRequest extends ApiRequest {
    @SerializedName("searchstring")
    String searchString;
    @SerializedName("id")
    String id;
    @SerializedName("lat")
    String lat;
    @SerializedName("lng")
    String lng;
     @SerializedName("distance")
     String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @SerializedName("LstBeer")
    ArrayList<String> lstBeer;

    @SerializedName("skytv")
    String skytv;
    @SerializedName("children")
    String children;
    @SerializedName("garden")
    String garden;
    @SerializedName("fireplace")
    String fireplace;
    @SerializedName("music")
    String music;
    @SerializedName("quiet")
    String quiet;
    @SerializedName("darts")
    String darts;
    @SerializedName("food")
    String food;
    @SerializedName("fruitmachine")
    String fruitmachine;
    @SerializedName("accommodation")
    String accommodation;
    @SerializedName("brewery")
    String brewery;
    @SerializedName("dogs")
    String dogs;
    @SerializedName("parking")
    String parking;
    @SerializedName("function")
    String function;
    @SerializedName("quiz")
    String quiz;
    @SerializedName("btsport")
    String btsport;
    @SerializedName("dj")
    String dj;
    @SerializedName("wifi")
    String wifi;
    @SerializedName("cash")
    String cashPoint;

    public ArrayList<String> getLstBeer() {
        return lstBeer;
    }

    public void setLstBeer(ArrayList<String> lstBeer) {
        this.lstBeer = lstBeer;
    }

    public String getSkytv() {
        return skytv;
    }

    public void setSkytv(String skytv) {
        this.skytv = skytv;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getGarden() {
        return garden;
    }

    public void setGarden(String garden) {
        this.garden = garden;
    }

    public String getFireplace() {
        return fireplace;
    }

    public void setFireplace(String fireplace) {
        this.fireplace = fireplace;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getQuiet() {
        return quiet;
    }

    public void setQuiet(String quiet) {
        this.quiet = quiet;
    }

    public String getDarts() {
        return darts;
    }

    public void setDarts(String darts) {
        this.darts = darts;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getFruitmachine() {
        return fruitmachine;
    }

    public void setFruitmachine(String fruitmachine) {
        this.fruitmachine = fruitmachine;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public String getDogs() {
        return dogs;
    }

    public void setDogs(String dogs) {
        this.dogs = dogs;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public String getBtsport() {
        return btsport;
    }

    public void setBtsport(String btsport) {
        this.btsport = btsport;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getId() {
        return id;
    }

    public String getCashPoint() {
        return cashPoint;
    }

    public void setCashPoint(String cashPoint) {
        this.cashPoint = cashPoint;
    }

    public void setId(String id) {
        this.id = id;
    }



}
