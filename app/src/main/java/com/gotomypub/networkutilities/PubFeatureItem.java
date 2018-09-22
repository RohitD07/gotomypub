package com.gotomypub.networkutilities;

import com.google.gson.annotations.SerializedName;

public class PubFeatureItem {
    @SerializedName("ID")
    String id;
    @SerializedName("Name")
    String name;
    @SerializedName("DisplayName")
    String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    boolean isSelected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
