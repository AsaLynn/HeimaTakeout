package com.heima.takeout.ui.activity

import android.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.heima.takeout.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import com.heima.takeout.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val findViewById = findViewById<LinearLayout>(R.id.main_bottom_bar)
        //判断设备是否有虚拟按键，如果有增加paddingBottom=50dp
        if (checkDeviceHasNavigationBar(this)){
            ll_main_activity.setPadding(0,0,0,50.dip2px())
        }
        initBottomBar()
        changeIndex(0)
    }
    val fragments:List<Fragment> = listOf<Fragment>(HomeFragment(),OrderFragment(), UserFragment(),MoreFragment())

    //获取是否存在NavigationBar
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        var hasNavigationBar = false
        val rs = context.getResources()
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {

        }
        return hasNavigationBar

    }
    /**
     * dip或dp转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    fun Int.dip2px(): Int {
        val scale = resources.displayMetrics.density
        return (toFloat() * scale + 0.5f).toInt()
    }



    private fun initBottomBar() {
        for (i in 0 until main_bottom_bar.childCount){

            main_bottom_bar.getChildAt(i).setOnClickListener(){
                view -> changeIndex(i)

            }
        }
    }

    private fun changeIndex(index: Int) {
    for (i in 0 until main_bottom_bar.childCount) {
    val child=main_bottom_bar.getChildAt(i)
        if (i==index) {
            setEnable(child,false)

        }else{
            setEnable(child,true)
        }

    }
        fragmentManager.beginTransaction().replace(R.id.main_content,fragments.get(index)).commit()
    }

    private fun setEnable(child: View, isEnable: Boolean) {
        child.isEnabled = isEnable
        if (child is ViewGroup){
            for(i in 0 until child.childCount){
                child.getChildAt(i).isEnabled = isEnable
            }

        }


    }
}

