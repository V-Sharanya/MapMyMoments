package com.sharanya.mmm

class posts {
    val startDestination: String
    val date: String
    val description: String
    val imageUrls: List<String>? // List to store multiple image URIs
    val endDestination: String?

    constructor(
        startDestination: String,
        date: String,
        description: String,
        imageUrls: List<String>?, // Changed from String? to List<String>?
        endDestination: String?
    ) {
        this.startDestination = startDestination
        this.date = date
        this.description = description
        this.imageUrls = imageUrls
        this.endDestination = endDestination
    }
}
