package com.gotomypub.networkutilities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kritid821 on 04/07/2017.
 */

public class APIError  implements Serializable {
    @SerializedName("rsn")
    private String rsn;

    @SerializedName("customCd")
    private String customCd;

    @SerializedName("cd")
    private String cd;

    public String getRsn ()
    {
        return rsn;
    }

    public void setRsn (String rsn)
    {
        this.rsn = rsn;
    }

    public String getCustomCd ()
    {
        return customCd;
    }

    public void setCustomCd (String customCd)
    {
        this.customCd = customCd;
    }

    public String getCd ()
    {
        return cd;
    }

    public void setCd (String cd)
    {
        this.cd = cd;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rsn = "+ rsn +", customCd = "+ customCd +", cd = "+ cd +"]";
    }
}
