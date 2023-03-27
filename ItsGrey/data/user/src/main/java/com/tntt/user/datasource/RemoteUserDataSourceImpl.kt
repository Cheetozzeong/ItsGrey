package com.tntt.user.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.network.Firestore
import com.tntt.user.model.UserDto
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): RemoteUserDataSource {
    val userCollection by lazy { firestore.collection("user") }

    override fun getUser(id: String): UserDto {
        val reference = userCollection.document(id)
        var userDto: UserDto = UserDto("1", "1")
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