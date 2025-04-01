package com.sharanya.mmm

import com.sharanya.mmm.model.LoginRequest
import com.sharanya.mmm.model.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @POST("users")
    fun registerUser(@Body user: users): Call<responseUser>

    @POST("createprofile")
    fun crateprofile(
        @Body user:ProfileUser
    ):Call<Unit>

    @GET("users")
    fun getUsers(): Call<ResponseWrapper>

    @Multipart
    @POST("posts")
    fun savePost(
        @Part("startDestination") startDestination: RequestBody,
        @Part("date") date: RequestBody,
        @Part("description") description: RequestBody,
        @Part images: List<MultipartBody.Part>,
        @Part("endDestination") endDestination: RequestBody?
    ): Call<ResponseBody>


    @DELETE("users/{id}")
    fun deleteUser(@Path("id") userId: Int): Call<Void>

    @GET("users/{id}")
    fun getUserById(@Path("id") userId: Int): Call<users>


    @POST("users/login")  // Ensure this matches your backend login route
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>



}
