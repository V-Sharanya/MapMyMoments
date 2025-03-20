package com.sharanya.mmm

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users")
    fun registerUser(@Body User:users):Call<users>


}

//for delete, update, get write code here.