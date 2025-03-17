package com.sharanya.mmm

data class Post(
    val profileImage: Int,  // Resource ID for profile image
    val userName: String,
    val destination: String,
    val description: String,
    val imageList: List<Int>, // List of image resource IDs for ViewPager2
    var likeCount: Int,
    var isLiked: Boolean,
    val longitude: Double,
    val latitude: Double
)
