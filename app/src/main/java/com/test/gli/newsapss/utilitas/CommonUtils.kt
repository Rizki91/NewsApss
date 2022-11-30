package com.test.gli.newsapss.utilitas

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.test.gli.newsapss.R


class CommonUtils {

    companion object{

        fun hideSoftKeyboard(a: Activity) {
            val view = a.currentFocus
            if (view != null) {
                val imm = a.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        fun hideKeyboard(view: View){
            val inputMethodManager = view.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun loadImageFromUrl(context: Context?, imageview: ImageView, url:String){
            val options = RequestOptions()
                .error(R.drawable.image_not_available)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

            Glide.with(context!!)
                .asBitmap()
                .apply(options)
                .apply(RequestOptions.fitCenterTransform())
                .load(url)
                .into(imageview)
        }

        fun getDisplayWidth(context: Context): Int {
            val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            return display.width
        }

        fun loadImageFromUrlSize(context: Context?, imageview: ImageView, url:String){
            val options = RequestOptions()
                .error(R.drawable.image_not_available)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

            Glide.with(context!!)
                .asBitmap()
                .apply(options)
                .load(url)
                .override(600, 600 )
                .into(imageview)
        }

        fun loadAppCompatImageFromUrl(context: Context?, imageview: AppCompatImageView, url:String){
            val options = RequestOptions()
                .error(R.drawable.image_not_available)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)

            Glide.with(context!!)
                .asBitmap()
                .apply(options)
                .load(url)
                .into(imageview)
        }





        private fun Bitmap.createBitmapWithBorder(borderSize: Float, borderColor: Int): Bitmap {
            val borderOffset = (borderSize * 2).toInt()
            val halfWidth = width / 2
            val halfHeight = height / 2
            val circleRadius = halfWidth.coerceAtMost(halfHeight).toFloat()
            val newBitmap = Bitmap.createBitmap(
                width + borderOffset,
                height + borderOffset,
                Bitmap.Config.ARGB_8888
            )

            // Center coordinates of the image
            val centerX = halfWidth + borderSize
            val centerY = halfHeight + borderSize

            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            val canvas = Canvas(newBitmap).apply {
                // Set transparent initial area
                drawARGB(0, 0, 0, 0)
            }

            // Draw the transparent initial area
            paint.isAntiAlias = true
            paint.style = Paint.Style.FILL
            canvas.drawCircle(centerX, centerY, circleRadius, paint)

            // Draw the image
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(this, borderSize, borderSize, paint)

            // Draw the createBitmapWithBorder
            paint.xfermode = null
            paint.style = Paint.Style.STROKE
            paint.color = borderColor
            paint.strokeWidth = borderSize
            canvas.drawCircle(centerX, centerY, circleRadius, paint)
            return newBitmap
        }

        const val PATTERN_HTML = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>"

        @JvmStatic
        fun checkHtmlPattern(value:String?):String{

            Log.w("checkHtmlPattern", value?:"")
            return if(value.isNullOrEmpty()){
                "no value"
            }else{
//                val pattern = Pattern.compile(PATTERN_HTML)
//                if (pattern.matcher(value).matches()){
//                    Log.w("checkHtmlPattern", "mathces! text: $value")
//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//                        Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY).toString()
//                    }else{
//                        Html.fromHtml(value).toString()
//                    }
//                } else {
//                    return value
//                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    Html.fromHtml(value, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                }else{
                    Html.fromHtml(value).toString()
                }
            }

        }
        @JvmStatic
        fun checkHtmlPatternQuest(value:String?): Any {

            Log.w("checkHtmlPattern", value?:"")
            return if(value.isNullOrEmpty()){
                "no value"
            }else{
//                val pattern = Pattern.compile(PATTERN_HTML)
//                if (pattern.matcher(value).matches()){
//                    Log.w("checkHtmlPattern", "mathces! text: $value")
//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//                        Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY).toString()
//                    }else{
//                        Html.fromHtml(value).toString()
//                    }
//                } else {
//                    return value
//                }

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    val datas: String?
                    datas = Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY).toString()

                    val mSpannableString = SpannableString(datas)

                    mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)

                    return  mSpannableString
                }else{
                    val datas: String?

                    datas = Html.fromHtml(value).toString()
                    val mSpannableString = SpannableString(datas)
                    mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)

                    return  mSpannableString
                }
            }

        }
    }
}