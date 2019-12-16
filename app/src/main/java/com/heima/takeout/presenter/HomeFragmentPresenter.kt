package com.heima.takeout.presenter


import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.heima.takeout.model.beans.Seller
import com.heima.takeout.model.net.ResponseInfo
import com.heima.takeout.model.net.TakeoutService
import com.heima.takeout.ui.fragment.HomeFragment
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragmentPresenter(val homeFragment: HomeFragment) {

    val takeoutService:TakeoutService
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.107:8080/TakeoutService/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        takeoutService = retrofit.create<TakeoutService>(TakeoutService::class.java!!)
    }
    fun getHomeInfo(){

        //要异步访问
        val homeCall = takeoutService.getHomeInfo()
        homeCall.enqueue(object :Callback<ResponseInfo>{
            override fun onResponse(call: Call<ResponseInfo>?, response: Response<ResponseInfo>?) {
            if (response == null){
                Log.e("home","服务器没有成功返回")
            }else{
                //服务器确实有所响应，响应也有成功,失败。
                if (response.isSuccessful()) {
                    val responseInfo = response.body()
                if (responseInfo.code.equals("0")){
                    val json = responseInfo.data
                    parserjson(json)
                }else if (responseInfo.code.equals("-1")){

                }
                }else{
                    Log.e("home","服务器代码错误")
                }
            }
            }
            override fun onFailure(call: Call<ResponseInfo>?, t: Throwable?) {
            Log.e("home","没有连上服务器")
            }
        })
    }

     fun parserjson(json: String?) {
         val gson = Gson()
         val jsonObject = JSONObject(json)
         val nearby = jsonObject.getString("nearbySellerList")
         val nearbySeller:List<Seller> = gson.fromJson(nearby, object : TypeToken<List<Seller>>() {}.type)
         val other = jsonObject.getString("otherSellerList")
         val otherSeller:List<Seller> = gson.fromJson(other, object : TypeToken<List<Seller>>() {}.type)

         //刷新UI
         if (nearbySeller.isNotEmpty() || otherSeller.isNotEmpty()) {
             homeFragment.onHomeSuccess();
         }else {
             homeFragment.onHomeFailed();
         }

     }

}