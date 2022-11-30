package com.test.gli.newsapss.service
import com.test.gli.newsapss.model.ResponseNewNews
import com.test.gli.newsapss.base.BaseRespon
import com.test.gli.newsapss.base.BaseResponTop
import com.test.gli.newsapss.model.ResponseTopNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {


    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("q") q:String,
        @Query("country") country:String,
        @Query("category") category:String,
        @Query("apiKey") apiKey:String
    ): Call<BaseRespon<List<ResponseNewNews>>>

    @GET("top-headlines/sources")
    fun getTopNews(
        @Query("apiKey") apiKey:String,
        @Query("country") country:String


    ): Call<BaseResponTop<List<ResponseTopNews>>>

}