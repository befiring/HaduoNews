package com.befiring.haduonews.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/12/13.
 */

public interface ApiService {
    @GET("toutiao/index")
    Observable<ResponseBody> getNews(@Query("type")String type, @Query("key")String key);
    @GET("index")
    Call<ResponseBody> getNews2(@Query("type")String type, @Query("key")String key);
}
