package com.tntt.user.datasource

import com.tntt.network.Firestore
import com.tntt.user.model.datasource.UserDto

class RemoteUserDataSourceImpl: RemoteUserDataSource {
    val userCollection by lazy { Firestore.firestore.collection("user") }

    override fun getUser(token: String): UserDto {
        val reference = userCollection.document(token)
        lateinit var userDto: UserDto
        reference.get().addOnSuccessListener { documentSnapShot ->
            val userDto = documentSnapShot.toObject(UserDto::class.java)
        }
        return userDto
    }
}