package com.tntt.user.datasource

import com.tntt.network.Firestore
import com.tntt.user.model.UserDto

object RemoteUserDataSourceImpl: RemoteUserDataSource {
    val userCollection by lazy { Firestore.firestore.collection("user") }

    override fun getUser(id: String): UserDto {
        val reference = userCollection.document(id)
        lateinit var userDto: UserDto
        reference.get()
            .addOnSuccessListener { documentSnapShot ->
                val userDto = documentSnapShot.toObject(UserDto::class.java)
            }
        return userDto
    }

    override fun createUser(userDto: UserDto): String {
        val userDto = updateUser(userDto)
        return userDto.id
    }

    override fun updateUser(userDto: UserDto): UserDto {
        userCollection.document(userDto.id).set(userDto)
        return userDto
    }

    override fun deleteUser(id: String): Boolean {
        userCollection.document(id).delete()
        return true
    }
}