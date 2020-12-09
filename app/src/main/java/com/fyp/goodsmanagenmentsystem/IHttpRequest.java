package com.fyp.goodsmanagenmentsystem;

import com.google.gson.JsonElement;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IHttpRequest {
    String BaseUrl="https://www.khmerbargain.com/api/";

    @Headers("Content-Type: application/json")
    @POST("save-deal")
    Call<JsonElement> register(@Body HashMap registerApiPayload);

}
