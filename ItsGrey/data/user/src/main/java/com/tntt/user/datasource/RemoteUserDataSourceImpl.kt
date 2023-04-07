package com.tntt.user.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.user.model.UserDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): RemoteUserDataSource {
    val userCollection by lazy { firestore.collection("user") }

    override suspend fun getUser(userId: String): Flow<UserDto> = flow {
        val documentSnapshot = userCollection
            .document(userId)
            .get()
            .await()

        val data = documentSnapshot.data
        val id = data?.get("id") as String
        val name = data?.get("name") as String

        val userDto = UserDto(id, name)
        emit(userDto)
    }

    override suspend fun createUser(userDto: UserDto): Flow<String> = flow {
        userCollection
            .document(userDto.id)
            .set(userDto)
            .await()
        emit(userDto.id)
    }

    override suspend fun updateUser(userDto: UserDto): Flow<Boolean> = flow {
        var result = false
        userCollection
            .document(userDto.id)
            .set(userDto)
            .addOnSuccessListener {
                result = true
            }.await()
        emit(result)
    }

    override suspend fun deleteUser(userId: String): Flow<Boolean> = flow {
        var result = false
        userCollection
            .document(userId)
            .delete()
            .addOnSuccessListener {
                result = true
            }
        emit(result)
    }
}