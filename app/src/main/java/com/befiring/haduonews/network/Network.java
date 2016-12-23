package com.befiring.haduonews.network;

import com.befiring.haduonews.app.Config;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Network {

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    public static ApiService getApiService(){

            Retrofit retrofit=new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Config.BASE_URL)
//                    .addConverterFactory(gsonConverterFactory)
//                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
        return retrofit.create(ApiService.class);
    }
}
