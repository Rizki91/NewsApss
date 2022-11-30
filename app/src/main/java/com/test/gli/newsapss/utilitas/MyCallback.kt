package com.test.gli.newsapss.utilitas

import android.view.View
import com.test.gli.newsapss.base.BaseUI
import com.test.gli.newsapss.model.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class MyCallback<T: Model>(private val baseUI: BaseUI): Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        baseUI.dialog?.dismiss()

        if(response.code() == 200){
            //call on success callback if response code server 200
            baseUI.shimmeringLoading?.stopShimmer()
            baseUI.shimmeringLoading?.visibility = View.GONE
            onSuccess(response.body())

        }else{
            //show dialog to check error

            //check if there is dialog popup still on window, dismiss it
            baseUI.dialog?.dismiss()

            //show error into bottom dialog
//            BottomDialogError.init(response.code().toString(), response.message(), response.errorBody()?.string()?:"-", baseUI.fm)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        baseUI.dialog?.dismiss()
        t.printStackTrace()
        onFailed(call, t)

    }

    abstract fun onSuccess(body:T?)

    abstract fun onFailed(call: Call<T>, t:Throwable)
}