package com.test.gli.newsapss.utilitas

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GenericAdapter<T>: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list:List<T>?=null

    fun setData(list:List<T>?){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Bind<T>).bind(list!![position])
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }

    protected abstract fun getViewHolder(parent: ViewGroup, viewType:Int): RecyclerView.ViewHolder

    internal interface Bind<T>{
        fun bind(item:T)
    }
}