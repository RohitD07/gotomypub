package com.gotomypub.networkutilities;

/**
 * Created by kritid821 on 14/08/2017.
 * This is custome throwable to handle 401 error
 * which helps to increase session of user or logout
 * based on the reason passed to constructor
 */

public class APIThrowable extends Throwable {
    public String getReason() {
        return reason;
    }

    private String reason;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    private int code;
    public APIThrowable(){
        //throw new RuntimeException("SessionTimeout");
    }

    public APIThrowable(String rsn,int cde){
        //throw new RuntimeException("SessionTimeout");
        reason =rsn;
        code =cde;
    }


}
