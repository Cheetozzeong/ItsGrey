package com.tntt.itsgrey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirstActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

            val intent =
                if (firebaseAuth.currentUser != null) {
                    Intent(this@FirstActivity, MainActivity::class.java)
                        .putExtra("currentUserEmail",firebaseAuth.currentUser!!.email.toString())
                        .putExtra("currentUserName",firebaseAuth.currentUser!!.displayName.toString())
                } else {
                    Log.d("why!!!!!!","curUser가 Null")
                    Intent(this@FirstActivity, LoginActivity::class.java)
                }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
    }
}