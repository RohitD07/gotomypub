
package com.gotomypub.networkutilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("postcode")
    @Expose
    private String postcode;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("geoLoc")
    @Expose
    private String geoLoc;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("telephone1")
    @Expose
    private String telephone1;
    @SerializedName("email1")
    @Expose
    private String email1;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("address3")
    @Expose
    private String address3;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("county")
    @Expose
    private String county;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("brewery")
    @Expose
    private String brewery;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("soc_face")
    @Expose
    private String socFace;
    @SerializedName("soc_twitter")
    @Expose
    private String socTwitter;
    @SerializedName("soc_insta")
    @Expose
    private String socInsta;
    @SerializedName("localAuth")
    @Expose
    private String localAuth;
    @SerializedName("ales")
    @Expose
    private String ales;
    @SerializedName("distance")
    @Expose
    private float distance;
    @SerializedName("searchstring")
    @Expose
    private String searchstring;

    @SerializedName("OpenTime_0")
    private String OpenTime_0;

    @SerializedName("ClosedTime_0")
    private String ClosedTime_0;

    @SerializedName("OpenTime_1")
    private String OpenTime_1;

    @SerializedName("ClosedTime_1")
    private String ClosedTime_1;


    @SerializedName("OpenTime_2")
    private String OpenTime_2;

    @SerializedName("ClosedTime_2")
    private String ClosedTime_2;


    @SerializedName("OpenTime_3")
    private String OpenTime_3;

    @SerializedName("ClosedTime_3")
    private String ClosedTime_3;


    @SerializedName("OpenTime_4")
    private String OpenTime_4;

    @SerializedName("ClosedTime_4")
    private String ClosedTime_4;


    @SerializedName("OpenTime_5")
    private String OpenTime_5;

    @SerializedName("ClosedTime_5")
    private String ClosedTime_5;


    @SerializedName("OpenTime_6")
    private String OpenTime_6;

    @SerializedName("ClosedTime_6")
    private String ClosedTime_6;

    @SerializedName("skytv")
    @Expose
    private String skytv;
    @SerializedName("children")
    @Expose
    private String children;
    @SerializedName("garden")
    @Expose
    private String garden;
    @SerializedName("fireplace")
    @Expose
    private String fireplace;
    @SerializedName("music")
    @Expose
    private String music;
    @SerializedName("quiet")
    @Expose
    private String quiet;
    @SerializedName("darts")
    @Expose
    private String darts;
    @SerializedName("food")
    @Expose
    private String food;
    @SerializedName("fruitmachine")
    @Expose
    private String fruitmachine;
    @Expose
    private String accommodation;
    @SerializedName("dogs")
    @Expose
    private String dogs;
    @SerializedName("parking")
    @Expose
    private String parking;
    @SerializedName("function")
    @Expose
    private String function;
    @SerializedName("quiz")
    @Expose
    private String quiz;
    @SerializedName("btsport")
    @Expose
    private String btsport;
    @SerializedName("dj")
    @Expose
    private String dj;
    @SerializedName("wifi")
    @Expose
    private String wifi;
    @SerializedName("LstBeerDetails")
    private ArrayList<BeerItem> pubBeerItemArrayList;
    @SerializedName("LstFeatures")
    private ArrayList<PubFeatureItem> pubFeatureItemArrayList;

    public ArrayList<BeerItem> getPubBeerItemArrayList() {
        return pubBeerItemArrayList;
    }

    public void setPubBeerItemArrayList(ArrayList<BeerItem> pubBeerItemArrayList) {
        this.pubBeerItemArrayList = pubBeerItemArrayList;
    }
    public ArrayList<PubFeatureItem> getPubFeatureItemArrayList() {
        return pubFeatureItemArrayList;
    }

    public void setPubFeatureItemArrayList(ArrayList<PubFeatureItem> pubFeatureItemArrayList) {
        this.pubFeatureItemArrayList = pubFeatureItemArrayList;
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

    public String getOpenTime_0() {
        return OpenTime_0;
    }

    public void setOpenTime_0(String openTime_0) {
        OpenTime_0 = openTime_0;
    }

    public String getClosedTime_0() {
        return ClosedTime_0;
    }

    public void setClosedTime_0(String closedTime_0) {
        ClosedTime_0 = closedTime_0;
    }

    public String getOpenTime_1() {
        return OpenTime_1;
    }

    public void setOpenTime_1(String openTime_1) {
        OpenTime_1 = openTime_1;
    }

    public String getClosedTime_1() {
        return ClosedTime_1;
    }

    public void setClosedTime_1(String closedTime_1) {
        ClosedTime_1 = closedTime_1;
    }

    public String getOpenTime_2() {
        return OpenTime_2;
    }

    public void setOpenTime_2(String openTime_2) {
        OpenTime_2 = openTime_2;
    }

    public String getClosedTime_2() {
        return ClosedTime_2;
    }

    public void setClosedTime_2(String closedTime_2) {
        ClosedTime_2 = closedTime_2;
    }

    public String getOpenTime_3() {
        return OpenTime_3;
    }

    public void setOpenTime_3(String openTime_3) {
        OpenTime_3 = openTime_3;
    }

    public String getClosedTime_3() {
        return ClosedTime_3;
    }

    public void setClosedTime_3(String closedTime_3) {
        ClosedTime_3 = closedTime_3;
    }

    public String getOpenTime_4() {
        return OpenTime_4;
    }

    public void setOpenTime_4(String openTime_4) {
        OpenTime_4 = openTime_4;
    }

    public String getClosedTime_4() {
        return ClosedTime_4;
    }

    public void setClosedTime_4(String closedTime_4) {
        ClosedTime_4 = closedTime_4;
    }

    public String getOpenTime_5() {
        return OpenTime_5;
    }

    public void setOpenTime_5(String openTime_5) {
        OpenTime_5 = openTime_5;
    }

    public String getClosedTime_5() {
        return ClosedTime_5;
    }

    public void setClosedTime_5(String closedTime_5) {
        ClosedTime_5 = closedTime_5;
    }

    public String getOpenTime_6() {
        return OpenTime_6;
    }

    public void setOpenTime_6(String openTime_6) {
        OpenTime_6 = openTime_6;
    }

    public String getClosedTime_6() {
        return ClosedTime_6;
    }

    public void setClosedTime_6(String closedTime_6) {
        ClosedTime_6 = closedTime_6;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getGeoLoc() {
        return geoLoc;
    }

    public void setGeoLoc(String geoLoc) {
        this.geoLoc = geoLoc;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSocFace() {
        return socFace;
    }

    public void setSocFace(String socFace) {
        this.socFace = socFace;
    }

    public String getSocTwitter() {
        return socTwitter;
    }

    public void setSocTwitter(String socTwitter) {
        this.socTwitter = socTwitter;
    }

    public String getSocInsta() {
        return socInsta;
    }

    public void setSocInsta(String socInsta) {
        this.socInsta = socInsta;
    }

    public String getLocalAuth() {
        return localAuth;
    }

    public void setLocalAuth(String localAuth) {
        this.localAuth = localAuth;
    }

    public String getAles() {
        return ales;
    }

    public void setAles(String ales) {
        this.ales = ales;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getSearchstring() {
        return searchstring;
    }

    public void setSearchstring(String searchstring) {
        this.searchstring = searchstring;
    }

}
