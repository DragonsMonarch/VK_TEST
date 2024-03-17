package com.dragonslotos.vk_test.data.Hilt

import com.dragonslotos.vk_test.data.retrofit.DataHolder
import android.content.Context;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;

import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory;



//impements retrofit dependecy
@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {


    @Provides
    fun getJSONApi(retrofit: Retrofit): DataHolder {
        return retrofit.create(DataHolder::class.java)
    }

    @Singleton
    @Provides
    fun retrofit(logging: HttpLoggingInterceptor?, okHttpClient: OkHttpClient?): Retrofit {
        return Builder()
            .baseUrl("https://www.timeapi.io/api/Time/current/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun okHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor?): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }
}