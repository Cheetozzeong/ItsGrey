package com.tntt.user.model.datasource

import java.util.ArrayList

data class UserDto(
    val id: String,
    val email: String,
    val name: String,
    val books: ArrayList<String>)