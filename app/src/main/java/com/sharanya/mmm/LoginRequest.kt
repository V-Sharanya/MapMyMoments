//package com.sharanya.mmm.model
//
//data class LoginRequest(
//    val name: String,
//    val password: String
//)
package com.sharanya.mmm.model

data class LoginRequest(
    val email: String,  // Use email instead of name
    val password: String
)
