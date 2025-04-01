package com.sharanya.mmm

class profiles {

    var name: String
    var username: String
    var bio: String
    var gender: String
    var avatar: Int// Nullable, since profile image is optional

    constructor(
        name: String,
        username: String,
        bio: String,
        gender: String,
        avatar: Int,
    ) {
        this.name = name
        this.username = username
        this.bio = bio
        this.gender = gender
        this.avatar = avatar
    }
}
