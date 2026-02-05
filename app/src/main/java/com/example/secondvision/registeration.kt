package com.example.secondvision

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class registeration : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        sessionManager = SessionManager(this)

        val nameET = findViewById<EditText>(R.id.nameET)
        val numberET = findViewById<EditText>(R.id.numberET)
        val passwordET = findViewById<EditText>(R.id.passwordET)
        val mailET = findViewById<EditText>(R.id.mailET)
        val spinner = findViewById<Spinner>(R.id.spinnerGender)
        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val loginTV = findViewById<TextView>(R.id.loginTV)

        // Spinner setup
        val genderList = arrayOf("Select Gender", "Male", "Female", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Login click
        loginTV.setOnClickListener {
            startActivity(Intent(this, signin::class.java))
            finish()
        }

        // Signup click
        signupBtn.setOnClickListener {
            val name = nameET.text.toString().trim()
            val phone = numberET.text.toString().trim()
            val password = passwordET.text.toString().trim()
            val mail = mailET.text.toString().trim()
            val gender = spinner.selectedItem.toString()

            // Validate fields
            if (name.isEmpty() || phone.isEmpty() || password.isEmpty() || mail.isEmpty() || gender == "Select Gender") {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save to database
            lifecycleScope.launch {
                val user = UserEntity(
                    name = name,
                    phone = phone,
                    password = password,
                    mail = mail,
                    gender = gender
                )

                AppDatabase.getDatabase(this@registeration)
                    .userDao()
                    .insertUser(user)

                sessionManager.setLogin(true)

                Toast.makeText(this@registeration, "Registered Successfully", Toast.LENGTH_SHORT).show()

                // Go to dashboard
                startActivity(Intent(this@registeration, dashboard::class.java))
                finish()
            }
        }
    }
}
