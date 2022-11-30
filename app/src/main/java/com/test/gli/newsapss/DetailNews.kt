package com.test.gli.newsapss

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.MediaController
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.test.gli.newsapss.base.BaseActivity
import com.test.gli.newsapss.dummy.Category
import com.test.gli.newsapss.utilitas.MyDialog
import kotlinx.android.synthetic.main.activity_detail_news.*
import android.webkit.WebChromeClient
import androidx.annotation.RequiresApi
import com.test.gli.newsapss.MainActivity
import com.test.gli.newsapss.R
import com.test.gli.newsapss.databinding.ActivityDetailNewsBinding


class DetailNews : BaseActivity() {

    companion object{

        fun getInstance(context: Context?, url:String?): Intent {
            Intent(context, DetailNews::class.java).let { intent ->
                intent.putExtra("url", url)

                return intent
            }
        }
    }

    private lateinit var binding: ActivityDetailNewsBinding
    private var url:String?=null
    var mediaControls: MediaController? = null

    override fun getContentView(): Int {
        return R.layout.activity_detail_news
    }

    override fun dataDummy(mainActivity: MainActivity): List<Category>? {
    return  null
    }

    override fun bindingView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_news)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ibBack.setOnClickListener { finish() }
        binding.ibCLose.setOnClickListener {
            MyDialog.showDialogDecisions(this, "Are you sure want to close this page?", object : MyDialog.DialogInfoDecisionListener{
                override fun onPositive() {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }
                override fun onNegative() {}
            })

        }

        url = intent.getStringExtra("url")


        binding.webview.let {
            it.settings.loadWithOverviewMode = true
            it.settings.useWideViewPort = true
            it.settings.builtInZoomControls = true
            it.settings.javaScriptEnabled = true
            it.settings.safeBrowsingEnabled = true
            it.webViewClient = object : WebViewClient() {
                override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                    if (applicationContext != null)
                        Toast.makeText(applicationContext, description, Toast.LENGTH_SHORT).show()
                }

                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
//                    mDialog?.dismiss()
                    Log.e("onPageStarted", url)

                }

                override fun onPageFinished(webView: WebView, url: String) {
//                    mDialog?.dismiss()
                    Log.e("onPageFinished", url)
                    webView.url
                }

                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    Log.e("shouldOverrindin", request.toString())
                    return true
                }


            }

            if(url.toString().contains("https://www.youtube.com/")){

                it.settings.javaScriptEnabled = true
                it.settings.pluginState = WebSettings.PluginState.ON
                it.loadUrl(url?:"https://google.com")
                it.setWebChromeClient(WebChromeClient())

            }else{
                if(!url.toString().contains("https")){
                    var urll = url!!.replace("http","https")
                    Log.d("URRRL:", urll)
                    it.loadUrl(urll)
                }else{
                    Log.d("URRRL:", url.toString())
                    it.loadUrl(url?:"https://google.com")
                }



            }

        }

    }

}