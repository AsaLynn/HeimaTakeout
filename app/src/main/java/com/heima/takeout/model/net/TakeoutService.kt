package com.heima.takeout.model.net


import retrofit2.http.GET

import retrofit2.Call


interface TakeoutService {
    @GET("home")
    fun getHomeInfo(): Call<ResponseInfo>
}