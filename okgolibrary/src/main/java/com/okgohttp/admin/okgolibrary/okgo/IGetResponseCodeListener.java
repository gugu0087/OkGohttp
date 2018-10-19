package com.okgohttp.admin.okgolibrary.okgo;

import okhttp3.Response;

/**
 * Created by hasee on 2018/1/12.
 */

public interface IGetResponseCodeListener {
    void onSuccessResponse(int code, String response);
    void onFailedResponse(Response response, String msg);
}
