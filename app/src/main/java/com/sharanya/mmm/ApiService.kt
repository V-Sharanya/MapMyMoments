package com.sharanya.mmm

import com.sharanya.mmm.model.LoginRequest
import com.sharanya.mmm.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("users")
    fun registerUser(@Body user: users): Call<users>

    @GET("users")
    fun getUsers(): Call<ResponseWrapper>

    @POST("posts")
    fun savePost(@Body post: posts): Call<posts>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") userId: Int): Call<Void>

    @POST("users/login")  // Ensure this matches your backend login route
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

}
