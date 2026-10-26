package com.lypanha.mybookstore.model

data class User(
    val id: Int,
    val imageId: Int,
    val username: String,
    val email: String,
    val password: String,
    val isLoginStatus: Boolean
) {
    companion object {
        const val LOGGED_IN: Boolean = true
        const val LOGGED_OUT: Boolean = false
    }
}
