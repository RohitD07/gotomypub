package com.gotomypub.networkutilities;

import retrofit2.Response;

public interface APIResponseHandlerInterface {
    public void sendApiResponse(Response response);
    public void sendApiFailureResponse(Throwable  throwable);
    public void sendPositiveAction();
    public void sendNegativeAction();
}
