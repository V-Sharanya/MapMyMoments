package com.sharanya.mmm

class posts {
    val startDestination: String
    val date: String
    val description: String
    val imageUrl: String?// Nullable since image selection is optional
    val endDestination: String?

    constructor(
        startDestination: String,
        date: String,
        description: String,
        imageUrl: String?,
        endDestination: String?
    ) {
        this.startDestination = startDestination
        this.date = date
        this.description = description
        this.imageUrl = imageUrl
        this.endDestination = endDestination
    }
}