package com.sharanya.mmm

class users {
    var id: Int=0
    val name: String
    val email: String
    val password: String

    constructor( name: String, email: String, password: String) {
        this.name = name
        this.email = email
        this.password = password
    }
}
