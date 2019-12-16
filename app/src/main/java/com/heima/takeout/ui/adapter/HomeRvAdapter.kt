package com.heima.takeout.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.heima.takeout.R
import org.jetbrains.anko.find

class HomeRvAdapter (val context:Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val TYPE_TITLE = 0
        val TYPE_SELLER = 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_TITLE
        } else {
            return TYPE_SELLER
        }
    }

    var mDatas: ArrayList<String> = ArrayList();

    fun setData(data: ArrayList<String>) {
        this.mDatas = data;
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType){
            TYPE_TITLE -> return TitleHolder(View.inflate(context, R.layout.item_title, null))
            TYPE_SELLER ->return SellerHolder(View.inflate(context, R.layout.item_seller, null))
            else -> return TitleHolder(View.inflate(context, R.layout.item_home_common, null))
        }

    }

    inner class SellerHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bindData(data: String) {
        }


        init {
        }
    }
    var url_maps:HashMap <String,String> = HashMap()
    inner class TitleHolder(item: View) : RecyclerView.ViewHolder(item) {
        val sliderLayout:SliderLayout

        fun bindData(data: String) {
            if(url_maps.size == 0){
                url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
                url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
                url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
                url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

             for((key,value) in url_maps){
                 var textSliderView : TextSliderView = TextSliderView(context)
                 textSliderView.description(key).image(value)
                 sliderLayout.addSlider(textSliderView)

             }
            }

        }
        init {
            sliderLayout = item.findViewById(R.id.slider)
        }
    }

    override fun getItemCount(): Int {
        if (mDatas.size > 0){
            return mDatas.size + 1
        }else{
            return 0
        }
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //实际上说的就是holder这个视图（holder实际上就是textView这个视图），绑定的是binData这个数据。
        //(holder as HomeItemHolder).bindData(mDatas[position])
        val viewType = getItemViewType(position)
        when(viewType){
            TYPE_TITLE -> (holder as TitleHolder).bindData("我是大哥...................")
            TYPE_SELLER ->(holder as SellerHolder).bindData(mDatas[position -1])
        }

    }

}