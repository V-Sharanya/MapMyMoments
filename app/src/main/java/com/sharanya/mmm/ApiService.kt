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
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @POST("users")
    fun registerUser(@Body user: users): Call<responseUser>

    @POST("createprofile")
    fun crateprofile(
        @Body user:ProfileUser
    ):Call<Unit>

    @PUT("updateprofile/{id}")
    fun updateprofile(
        @Path("id") userId: Int,
        @Body details:Map<String,String>
    ):Call<Unit>

    @PUT("users/{id}")
    fun updateUser(
        @Path("id") userId: Int,
        @Body details: Map<String, String>
    ):Call<Unit>

    @GET("profilebyid/{id}")
    fun getProfile(
        @Path("id") userId: Int
    ): Call<responseUser>


    @GET("users")
    fun getUsers(): Call<ResponseWrapper>



    @DELETE("users/{id}")
    fun deleteUser(@Path("id") userId: Int): Call<Unit>

    @POST("users/login")  // Ensure this matches your backend login route
    fun loginUser(@Body request: LoginRequest): Call<responseUser>

    @Multipart
    @POST("posts")
    fun savePost(
        @Part("startDestination") startDestination: RequestBody,
        @Part("date") date: RequestBody,
        @Part("description") description: RequestBody,
        @Part images: List<MultipartBody.Part>,
        @Part("endDestination") endDestination: RequestBody?
    ): Call<ResponseBody>



//

    //jjj

//    @POST("users")
//    fun registerUser(@Body user: users): Call<responseUser>
//
//    @POST("createprofile")
//    fun crateprofile(
//        @Body user:ProfileUser
//    ):Call<Unit>
//
//    @PUT("updateprofile/{id}")
//    fun updateprofile(
//        @Path("id") userId: Int,
//        @Body details:Map<String,String>
//    ):Call<Unit>
//
//    @PUT("users/{id}")
//    fun updateUser(
//        @Path("id") userId: Int,
//        @Body details: Map<String, String>
//    ):Call<Unit>
//
//    @GET("profilebyid/{id}")
//    fun getProfile(
//        @Path("id") userId: Int
//    ): Call<responseUser>

}
