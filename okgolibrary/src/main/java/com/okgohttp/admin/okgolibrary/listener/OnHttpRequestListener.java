package com.okgohttp.admin.okgolibrary.listener;

public interface OnHttpRequestListener {
    void success(String result);

    void error(int errorCode, String errorMsg);
}
