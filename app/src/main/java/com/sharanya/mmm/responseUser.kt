package com.sharanya.mmm

data class responseUser(
    val id:Int,
    val name:String,
    val email:String,
    val password:String,
    val role:String,
    val bio:String?,
    val age:Int?,
    val gender:String?,
    val profileImage:String?,
    val userid:Int

)
