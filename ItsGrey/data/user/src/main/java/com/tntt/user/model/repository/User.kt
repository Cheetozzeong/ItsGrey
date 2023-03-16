package com.tntt.user.model.repository

import java.util.ArrayList

data class User(
    val id: String,
    val name: String,
    val bookIds: ArrayList<String>)