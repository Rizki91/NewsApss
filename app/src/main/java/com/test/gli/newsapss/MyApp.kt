package com.test.gli.newsapss

import android.media.MediaDrm
import android.view.View
import com.google.gson.GsonBuilder
import com.test.gli.newsapss.utilitas.MyCallback
import com.test.gli.newsapss.base.BaseUI
import com.test.gli.newsapss.model.Model
import com.test.gli.newsapss.service.API
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest
import java.util.*
import java.util.concurrent.TimeUnit

class MyApp {

    companion object{

        /**
         * In this section, initial method for request to REST API.
         * I made the method function staticly.
         *
         * Retrofit 2.x
         */
        fun <T> callApi(baseUI: BaseUI, call: Call<T>, callback: Callback<T>){
            baseUI.progressBar?.visibility = View.VISIBLE
            call.enqueue(callback)
        }

        fun <T: Model> callApi(baseUI: BaseUI, call: Call<T>, myCallback: MyCallback<T>){
            baseUI.progressBar?.visibility = View.VISIBLE
            call.enqueue(myCallback)
        }

        fun getApi(): API {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(getClient())
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create()
                    )
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            return retrofit.create(API::class.java)
        }

        private fun getClient(): OkHttpClient {

            try {

                val builder = OkHttpClient.Builder()
                return builder
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(getLoggingInterceptor())
                    .build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

        private fun getLoggingInterceptor(): Interceptor {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = getInterceptorLevel()
            return httpLoggingInterceptor
        }


        private fun getInterceptorLevel(): HttpLoggingInterceptor.Level {
            return if (com.test.gli.newsapss.BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
//            return HttpLoggingInterceptor.Level.BODY
        }

        fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }
        fun getUUID():String?{

            val WIDEVINE_UUID = UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L)
            var wvDrm: MediaDrm? = null
            try {
                wvDrm = MediaDrm(WIDEVINE_UUID)
                val widevineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID)
                val md = MessageDigest.getInstance("SHA-256")
                md.update(widevineId)
                return  md.digest().toHexString()
            } catch (e: Exception) {
                //WIDEVINE is not available
                return null
            } catch (e: NoSuchMethodError){
                return null
            } finally {
                wvDrm?.close()

            }

        }

    }
}