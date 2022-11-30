package com.test.gli.newsapss.model

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
import com.test.gli.newsapss.utilitas.CommonUtils
import java.text.SimpleDateFormat
import java.util.*

data class ResponseNewNews(

	@field:SerializedName("publishedAt")
	val publishedAt: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("urlToImage")
	val urlToImage: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("source")
	val source: Source? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("content")
	val content: String? = null
){
	companion object{

		@BindingAdapter("bannerPhoto")
		@JvmStatic fun setBannerPhoto(view: ImageView, url:String?){
			if(url != null){
				CommonUtils.loadImageFromUrl(
					view.context,
					view,
					url
				)
			}
		}


		@BindingAdapter("showDD")
		@JvmStatic fun showDD(view: TextView, date:String?){
			date?.let {
				val curSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
				val sdf = SimpleDateFormat("dd-MMM-yyyy-HH:mm:ss", Locale.getDefault())

				val curDate = curSdf.parse(it)
				val newDate = sdf.format(curDate)

				val split = newDate.split("-")
				view.text = newDate
			}
		}
	}
}

data class Source(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)



