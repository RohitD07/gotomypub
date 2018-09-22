package com.gotomypub.mapcluster;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MapClusterItem implements ClusterItem {
        private  LatLng mPosition;
        private  String mTitle;
        private  String mSnippet;
        private int listIndex;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private boolean isActive;

    public int getListIndex() {
        return listIndex;
    }

    public void setListIndex(int listIndex) {
        this.listIndex = listIndex;
    }

    public MapClusterItem(double lat, double lng) {
            mPosition = new LatLng(lat, lng);
        }

        public MapClusterItem(double lat, double lng, String title, String snippet,int listIndex_,boolean isActive_) {
            mPosition = new LatLng(lat, lng);
            mTitle = title;
            mSnippet = snippet;
            listIndex=listIndex_;
            this.isActive=isActive_;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public String getTitle() {
            return mTitle;
        }

        @Override
        public String getSnippet() {
            return mSnippet;
        }
    }

