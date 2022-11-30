package com.test.gli.newsapss.base

import com.google.gson.annotations.SerializedName
import com.test.gli.newsapss.model.Model

class BaseRespon<T>: Model() {

    @SerializedName("articles")
    val articles:T?=null


}