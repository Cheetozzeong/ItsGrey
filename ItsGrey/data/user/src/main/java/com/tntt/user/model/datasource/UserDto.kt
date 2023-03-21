package com.tntt.user.model.datasource

import java.util.ArrayList

data class UserDto(
    val id: String,
    val name: String,
){
    constructor(): this("", "")
}