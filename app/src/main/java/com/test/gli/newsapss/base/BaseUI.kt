package com.test.gli.newsapss.base

import android.app.Dialog
import android.content.Context
import android.widget.ProgressBar
import com.facebook.shimmer.ShimmerFrameLayout
import com.test.gli.newsapss.dummy.Category
import com.test.gli.newsapss.service.API
import retrofit2.Callback

interface BaseUI {
    val ctx:Context?
    val dialog:Dialog?
    val shimmeringLoading: ShimmerFrameLayout?
    val progressBar:ProgressBar?
    val api: API

//    fun<T: ColorSpace.Model> callApi(call: retrofit2.Call<T>, callback: MyCallback<T>)
    fun<T> callApi(call: retrofit2.Call<T>, callback: Callback<T>)

    val dataDummy: List<Category>?
}