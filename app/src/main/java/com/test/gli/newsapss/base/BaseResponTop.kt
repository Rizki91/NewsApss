package com.test.gli.newsapss.base

import com.google.gson.annotations.SerializedName
import com.test.gli.newsapss.model.Model

class BaseResponTop <T>: Model(){

    @SerializedName("sources")
    val sources:T?=null
}