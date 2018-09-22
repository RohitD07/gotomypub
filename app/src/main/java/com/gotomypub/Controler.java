package com.gotomypub;

import android.content.Context;

import com.gotomypub.interfaces.ControllerEmitter;
import com.gotomypub.networkutilities.APIResponseHandlerInterface;
import com.gotomypub.networkutilities.ApiResponse;
import com.gotomypub.utilitycomponents.utils.UtilityMethods;

import java.util.ResourceBundle;

import retrofit2.Response;

public class Controler implements APIResponseHandlerInterface {
    Context mContext;
    ControllerEmitter controllerEmitter;

    public Controler(Context context,ControllerEmitter controllerEmitter_){
        this.mContext=context;
        this.controllerEmitter=controllerEmitter_;
    }

    @Override
    public void sendApiResponse(Response response) {
        if(response.isSuccessful()){
            controllerEmitter.netWorkResponser((ApiResponse) response.body());

        }
        else {

        }

    }

    @Override
    public void sendApiFailureResponse(Throwable throwable) {

    }

    @Override
    public void sendPositiveAction() {

    }

    @Override
    public void sendNegativeAction() {

    }
}
