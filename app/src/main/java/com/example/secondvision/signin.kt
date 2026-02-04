package com.example.secondvision

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class signin : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val registerationTV = findViewById<TextView>(R.id.registerationTV)
        val numberET = findViewById<EditText>(R.id.numberET)
        val passwordET = findViewById<EditText>(R.id.passwordET)
        val loginBtn = findViewById<Button>(R.id.loginBtn)

        sessionManager = SessionManager(this)



        loginBtn.setOnClickListener {
            val number = numberET.text.toString().trim()
            val password = passwordET.text.toString().trim()

            when {
                number.isEmpty() -> numberET.error = "Enter number"
                password.isEmpty() -> passwordET.error = "Enter password"
                else -> {
                    lifecycleScope.launch {
                        val user = AppDatabase.getDatabase(this@signin)
                            .userDao()
                            .login(number, password)

                        if (user != null) {
                            sessionManager.setLogin(true)

                            val intent = Intent(this@signin, dashboard::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            runOnUiThread {
                                passwordET.error = "Invalid number or password"
                            }
                        }
                    }
                }
            }
        }


        registerationTV.setOnClickListener {
            startActivity(Intent(this, registeration::class.java))
            finish()
        }
    }
}
