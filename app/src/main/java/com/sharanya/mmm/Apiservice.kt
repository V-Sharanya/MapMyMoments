package com.sharanya.mmm

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    // Register a user
    @POST("users")
    fun registerUser(@Body user: users): Call<users>

    // Create a post
    @POST("posts")
    fun savePost(@Body post: posts): Call<posts>


}
