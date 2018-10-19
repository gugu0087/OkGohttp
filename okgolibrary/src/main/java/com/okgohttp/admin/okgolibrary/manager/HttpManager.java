package com.okgohttp.admin.okgolibrary.manager;

import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.okgohttp.admin.okgolibrary.listener.OnHttpRequestListener;
import com.okgohttp.admin.okgolibrary.okgo.JsonCallback;

import org.json.JSONObject;

import java.io.File;

/**
 * Create by Admin on 2018/9/11
 */
public class HttpManager {
    private static HttpManager instance = null;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        if (instance == null) {
            instance = new HttpManager();
        }
        return instance;

    }

    /**
     * get请求
     * @param url
     * @param httpParams  参数，多个，单个
     * @param serviceListener
     */
    public void sendGetRequest(String url, HttpParams httpParams, final OnHttpRequestListener serviceListener) {

        OkGo.<String>get(url).tag(this)
                .params(httpParams)
                .execute(new JsonCallback<String>() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        serviceListener.success(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        serviceListener.error(response.code(), response.message());
                    }
                });
    }

    /**
     * post请求
     * @param url
     * @param params 参数
     * @param serviceListener
     */
    public void sendPostRequest(String url, JSONObject params, final OnHttpRequestListener serviceListener) {
        OkGo.<String>post(url)
                .upJson(params.toString())
                .tag(this)
                .execute(new JsonCallback<String>() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        serviceListener.success(response.body());

                    }

                    @Override
                    public void onError(Response<String> response) {
                        serviceListener.error(response.code(), response.message());

                    }
                });

    }
    /**
     * 网络请求一个bitmap的时候。返回bitmap
     */
    public void getBitmap(String imgUrl, final BitmapCallback bitmapCallback) {
        OkGo.<Bitmap>get(imgUrl)
                .tag(this)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        bitmapCallback.onSuccess(response);
                    }

                    @Override
                    public void onCacheSuccess(Response<Bitmap> response) {
                        bitmapCallback.onCacheSuccess(response);
                    }

                    @Override
                    public void onError(Response<Bitmap> response) {
                        bitmapCallback.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        bitmapCallback.onFinish();
                    }

                    @Override
                    public void onStart(Request<Bitmap, ? extends Request> request) {
                        bitmapCallback.onStart(request);
                    }
                });
    }
    /**
     * 普通的文件下载
     * downloadProgress 已经回调在主线程了，可以直接刷新UI
     */
    public void downLoadFiles(String url, final FileCallback fileCallback) {
        OkGo.<File>get(url)
                .tag(this)
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {
                        // file 为文件数据
                        fileCallback.onSuccess(response);
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        //这里是进度回调，在主线程
                        fileCallback.downloadProgress(progress);
                    }
                });
    }

    /**
     * 上传文件，
     * 返回值类型，不一定是string，，可以是对象什么的，
     * key1 ,key2表示可以同时传多个文件，
     * key3表示，一个key，可以传多个文件
     * params 这个参数特别注意。 需要是这种 类型的params("key1", new File("filePath1"))
     */
    public void uploadFile(String url, HttpParams params, final OnHttpRequestListener serviceListener) {
        OkGo.<String>post(url)
                .tag(this)
                .isMultipart(false)
                .params(params)
                .execute(new JsonCallback<String>() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        serviceListener.success(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        serviceListener.error(response.code(),response.message());
                    }
                });
    }
}
