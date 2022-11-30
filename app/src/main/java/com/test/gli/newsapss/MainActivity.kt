package com.test.gli.newsapss

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.gli.newsapss.DetailNews
import com.test.gli.newsapss.model.ResponseNewNews
import com.test.gli.newsapss.utilitas.GenericAdapter
import com.test.gli.newsapss.R
import com.test.gli.newsapss.base.BaseActivity
import com.test.gli.newsapss.base.BaseRespon
import com.test.gli.newsapss.base.BaseResponTop
import com.test.gli.newsapss.databinding.ActivityMainBinding
import com.test.gli.newsapss.databinding.ItemKategoriBinding
import com.test.gli.newsapss.databinding.ItemNewsBinding
import com.test.gli.newsapss.databinding.ItemNewsNewBinding
import com.test.gli.newsapss.dummy.Category
import com.test.gli.newsapss.model.ResponseTopNews
import com.test.gli.newsapss.utilitas.MyCallback
import retrofit2.Call

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding
    private var adapter:MyAdapterCategory?=null
    private lateinit var adapterTopNews: GenericAdapter<ResponseTopNews>
    private lateinit var adapterNewNews: GenericAdapter<ResponseNewNews>
    private var listKategori:List<Category>?=null
    private var listNews:List<ResponseTopNews>?=null
    private var listNewNews:List<ResponseNewNews>?=null
    private var category:String?=""


    override fun bindingView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        adapter = MyAdapterCategory()
        binding.rcKategori.apply {
//            layoutManager = GridLayoutManager(context, 7)
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }.also { it.adapter = adapter }

        adapterTopNews = object : GenericAdapter<ResponseTopNews>() {
            override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return VHAdapterTopNews(
                    DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_news,
                        parent,
                        false
                    )
                )
            }
        }

        adapterNewNews = object : GenericAdapter<ResponseNewNews>() {
            override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return VHAdapterNewNews(
                    DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_news_new,
                        parent,
                        false
                    )
                )
            }
        }


        binding.etSearch.addTextChangedListener(object  : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
             getNewNews(p0.toString(),category)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


        listKategori = dataDummy(this)
        adapter!!.setData(listKategori)
        binding.rcKategori.apply {

            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }.also { it.adapter = adapter }

        getTopNews()
        getNewNews()
    }

    fun getTopNews(){
        showLoading()
        callApi(api.getTopNews(getString(R.string.key),"us"),object : MyCallback<BaseResponTop<List<ResponseTopNews>>>(this){
            override fun onSuccess(body: BaseResponTop<List<ResponseTopNews>>?) {
             if(body != null){
                 if(body.status!!.equals("ok")){
                     listNews = body.sources

                     adapterTopNews.setData(listNews)
                     binding.rcTopNews.apply {

                         layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                         setHasFixedSize(true)
                     }.also { it.adapter = adapterTopNews }

                 }
             }
            }

            override fun onFailed(call: Call<BaseResponTop<List<ResponseTopNews>>>, t: Throwable) {

            }

        })

    }

    fun getNewNews(q:String?="",category:String?=""){
//        showLoading()
        callApi(api.getTopHeadlines(q!!,"in",category!!,getString(R.string.key)),object : MyCallback<BaseRespon<List<ResponseNewNews>>>(this){
            override fun onSuccess(body: BaseRespon<List<ResponseNewNews>>?) {
                if(body != null){
                    if(body.status.equals("ok")){
                        listNewNews = body.articles

                        adapterNewNews.setData(listNewNews)
                        binding.rcNews.apply {

                            layoutManager = LinearLayoutManager(this@MainActivity)
                            setHasFixedSize(true)
                        }.also { it.adapter = adapterNewNews }
                    }
                }
            }

            override fun onFailed(call: Call<BaseRespon<List<ResponseNewNews>>>, t: Throwable) {

            }

        })
    }

    override fun getContentView(): Int {
        return   R.layout.activity_main
    }

    override fun dataDummy(mainActivity: MainActivity): List<Category>? {
        return  dataDummy
    }

    inner class MyAdapterCategory:RecyclerView.Adapter<MyAdapterCategory.VHAdapter>(){
        private var list:List<Category>?=null
        private var selectedItem = -1
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MyAdapterCategory.VHAdapter {
            return VHAdapter(DataBindingUtil.inflate(layoutInflater, R.layout.item_kategori, parent, false))

        }

        fun setData(list:List<Category>?){
            this.list = list
            notifyDataSetChanged()
        }


        override fun onBindViewHolder(holder: MyAdapterCategory.VHAdapter, position: Int) {
            holder.bind(list!![position])
        }

        inner class VHAdapter(private val binding: ItemKategoriBinding):RecyclerView.ViewHolder(binding.root){
            fun bind(arg:Category){
                binding.data = arg

                if(selectedItem == bindingAdapterPosition){
                    binding.iconCategory.background = ContextCompat.getDrawable(this@MainActivity,R.drawable.bg_outline_gold_no_padding_red)
//
                }else{
                    binding.iconCategory.background = ContextCompat.getDrawable(this@MainActivity,R.drawable.bg_outline_gold_no_padding)
//
                }
            }
            init {
                itemView.setOnClickListener {
                    notifyItemChanged(selectedItem)
                    selectedItem = bindingAdapterPosition
                    notifyItemChanged(selectedItem)
                    showLoading()
                    category = listKategori?.get(adapterPosition)?.getName().toString()
                    getNewNews("",listKategori?.get(adapterPosition)?.getName().toString())

                }
            }
        }

        override fun getItemCount(): Int {
            return list?.size?:0
        }

    }


    inner class VHAdapterTopNews(private val binding: ItemNewsBinding):RecyclerView.ViewHolder(binding.root), GenericAdapter.Bind<ResponseTopNews>{
        override fun bind(item: ResponseTopNews) {
            binding.data = item
        }
        init {
           itemView.setOnClickListener {

               startActivity(DetailNews.getInstance(this@MainActivity, listNews?.get(adapterPosition)?.url))

           }

        }
    }






    inner class VHAdapterNewNews(private val binding: ItemNewsNewBinding):RecyclerView.ViewHolder(binding.root), GenericAdapter.Bind<ResponseNewNews>{
        override fun bind(item: ResponseNewNews) {
            binding.data = item
        }
        init {
            itemView.setOnClickListener {
                startActivity(DetailNews.getInstance(this@MainActivity, listNewNews?.get(adapterPosition)?.url))

           }
        }


    }


}