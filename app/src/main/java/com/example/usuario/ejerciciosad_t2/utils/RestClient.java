package com.example.usuario.ejerciciosad_t2.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by usuario on 9/11/17.
 */

public class RestClient {
    private static final String BASE_URL = "";
    //Según Paco: tiempo de espera contra servidor local
    private static final int MAX_TIMEOUT = 2000;
    private static final int RETRIES = 3;
    //Según Paco: tiempo de espera contra servidor remoto
    private static final int TIMEOUT_BETWEEN_RETRIES = 5000;
    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.get(getAbsoluteUrl(url), responseHandler);
    }
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(MAX_TIMEOUT);
        client.setMaxRetriesAndTimeout(RETRIES, TIMEOUT_BETWEEN_RETRIES);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    public static void cancelRequests(Context c, boolean flag) {
        client.cancelRequests(c, flag);
    }
}