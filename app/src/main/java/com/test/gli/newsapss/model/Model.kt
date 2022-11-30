package com.test.gli.newsapss.model

import com.google.gson.annotations.SerializedName

open class Model {
    @SerializedName("status")
    val status:String?=null

    @SerializedName("totalResults")
    val totalResults:Int?=null

}