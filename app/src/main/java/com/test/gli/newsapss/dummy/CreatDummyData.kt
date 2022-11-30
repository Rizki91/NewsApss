package com.test.gli.newsapss.dummy

import android.app.Activity
import java.util.ArrayList

class CreatDummyData {

    fun getMessageCategory(mActivity: Activity?): List<Category>? {
        val lst: MutableList<Category> = ArrayList<Category>()
        var mCategory: Category
        mCategory = Category()
        mCategory.setId("1")
        mCategory.setName("business")
        lst.add(mCategory)
        mCategory = Category()
        mCategory.setId("2")
        mCategory.setName("entertainment")
        lst.add(mCategory)
        mCategory = Category()
        mCategory.setId("3")
        mCategory.setName("general")
        lst.add(mCategory)
        mCategory = Category()
        mCategory.setId("4")
        mCategory.setName("health")
        lst.add(mCategory)
        mCategory = Category()
        mCategory.setId("5")
        mCategory.setName("science")
        lst.add(mCategory)
        mCategory = Category()
        mCategory.setId("6")
        mCategory.setName("sports")
        lst.add(mCategory)
        mCategory = Category()
        mCategory.setId("7")
        mCategory.setName("technology")
        lst.add(mCategory)
        return lst
    }
}