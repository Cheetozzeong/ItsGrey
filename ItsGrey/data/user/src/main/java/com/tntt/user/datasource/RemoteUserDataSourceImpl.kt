package com.tntt.user.datasource

import com.google.firebase.firestore.FirebaseFirestore
import com.tntt.network.Firestore
import com.tntt.user.model.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteUserDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): RemoteUserDataSource {
    val userCollection by lazy { firestore.collection("user") }

    override suspend fun getUser(id: String): Flow<UserDto> = flow {
        val reference = userCollection.document(id)
        var userDto: UserDto = UserDto("1", "1")
        reference.get()
            .addOnSuccessListener { documentSnapShot ->
                val userDto = documentSnapShot.toObject(UserDto::class.java)
            }
        emit(userDto)
    }

    override suspend fun createUser(userDto: UserDto): Flow<String> = flow {
        val userDto = updateUser(userDto).collect() { userDto ->
            emit(userDto.id)
        }
    }

    override suspend fun updateUser(userDto: UserDto): Flow<UserDto> = flow {
        userCollection.document(userDto.id).set(userDto)
        emit(userDto)
    }

    override suspend fun deleteUser(id: String): Flow<Boolean> = flow {
        userCollection.document(id).delete()
        emit(true)
    }
}