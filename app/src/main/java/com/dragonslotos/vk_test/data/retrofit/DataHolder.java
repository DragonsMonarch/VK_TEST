package com.dragonslotos.vk_test.data.retrofit;

import com.dragonslotos.vk_test.domain.model.DateTime;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;


//Json Api Holder for Retrofit
public interface DataHolder {
    @GET("https://www.timeapi.io/api/Time/current/zone?timeZone=Europe/Moscow")
    public Call<DateTime> getTime();
}
