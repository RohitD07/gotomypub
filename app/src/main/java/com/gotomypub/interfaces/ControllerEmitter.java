package com.gotomypub.interfaces;

import com.gotomypub.networkutilities.ApiResponse;

public interface ControllerEmitter {
    void netWorkResponser(ApiResponse apiResponse);
    void uiUpdater();
}
