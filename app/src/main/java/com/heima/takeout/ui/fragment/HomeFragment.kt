package com.heima.takeout.ui.fragment

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heima.takeout.R
import com.heima.takeout.presenter.HomeFragmentPresenter
import com.heima.takeout.ui.adapter.HomeRvAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast


class HomeFragment : Fragment(){
    lateinit var homeRvAdapter:HomeRvAdapter
    lateinit var rvhome:RecyclerView
    lateinit var homeFragmentPresenter :HomeFragmentPresenter
    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        distance = 120.dip2px();
        val view = View.inflate(activity,R.layout.fragment_home, null)
        rvhome = view.find<RecyclerView>(R.id.rv_home)
        rvhome.layoutManager =LinearLayoutManager(activity)
        homeRvAdapter=HomeRvAdapter(activity)
        homeFragmentPresenter = HomeFragmentPresenter(this);
        rvhome.adapter =homeRvAdapter
        return view
    }

    fun Int.dip2px(): Int {
        val scale = resources.displayMetrics.density
        return (toFloat() * scale + 0.5f).toInt()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    val datas:ArrayList<String> = ArrayList<String>()
    var sum:Int = 0
    var distance:Int =0
    var alpha = 55
    private fun initData() {
//        for (i in 0 until 100){
//            datas.add("我是商家:"+i)
//        }
        homeFragmentPresenter.getHomeInfo()
        homeRvAdapter.setData(datas);

        rvhome.setOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                sum += dy
                if(sum > distance){
                    alpha = 255
                }else{
                    alpha = sum * 200 / distance
                    alpha += 55
                }
                ll_title_container.setBackgroundColor(Color.argb(alpha,0x31,0x90,0xe8))
            }
        })
    }

    fun onHomeSuccess() {

    toast("成功拿到数据")
    }

    fun onHomeFailed() {
    toast("获取首页数据失败")
    }
}