package com.wisedrive.dealerapp1.commonclasses1.commonclasses;

public interface ResponseListener {
    public void ResponseSuccess(ResponseExtension response);
    public void ResponseFailure(int responseCode, String errorMsg);
}
