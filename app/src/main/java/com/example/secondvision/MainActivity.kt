package com.example.secondvision

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sessionManager = SessionManager(this)

        Handler(Looper.getMainLooper()).postDelayed({

            if (sessionManager.isLoggedIn()) {
                startActivity(Intent(this, dashboard::class.java))
            } else {
                startActivity(Intent(this, signin::class.java))
            }
            finish()

        }, 4000)
    }
}