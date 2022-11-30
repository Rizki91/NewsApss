package com.test.gli.newsapss.dummy

import com.google.gson.annotations.SerializedName

class Category {
    @SerializedName("id")
    private var id: String? = null

    @SerializedName("name")
    private var name: String? = null

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
}