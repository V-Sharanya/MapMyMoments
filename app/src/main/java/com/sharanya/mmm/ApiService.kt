package com.sharanya.mmm

import com.google.android.play.integrity.internal.u
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users")
    fun registerUser(@Body user: users): Call<users>

    @POST("posts")
    fun savePost(@Body post: posts): Call<posts>



}