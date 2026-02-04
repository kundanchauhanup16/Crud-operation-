package com.example.secondvision

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class registeration : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        val spinner = findViewById<Spinner>(R.id.spinnerGender)
        val loginTV = findViewById<TextView>(R.id.loginTV)
        val signupBtn = findViewById<Button>(R.id.signupBtn)
        val nameET = findViewById<EditText>(R.id.nameET)
        val passwordET = findViewById<EditText>(R.id.passwordET)
        val numberET = findViewById<EditText>(R.id.numberET)
        sessionManager = SessionManager(this)

        lifecycleScope.launch {
            val user = UserEntity(
                name = nameET.text.toString(),
                password = passwordET.text.toString(),
                phone = numberET.text.toString()
            )

            AppDatabase.getDatabase(this@registeration)
                .userDao()
                .insertUser(user)

//            finish()
        }

        sessionManager.setLogin(true)


        loginTV.setOnClickListener {
            startActivity(Intent(this, signin::class.java))
        }
        val genderList = arrayOf(
            "Select Gender",
            "Male",
            "Female",
            "Other"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            genderList
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinner.adapter = adapter

//        signupBtn.setOnClickListener {
//            val selectedGender = spinner.selectedItem.toString()
//
//            if (selectedGender == "Select Gender") {
//                Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
//            } else {
//                startActivity(Intent(this, dashboard::class.java))
//                finish()
//                Toast.makeText(this, "Selected: $selectedGender", Toast.LENGTH_SHORT).show()
//            }
//        }

        signupBtn.setOnClickListener {
            val name = nameET.text.toString().trim()
            val phone = numberET.text.toString().trim()
            val password = passwordET.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = UserEntity(
                    name = name,
                    phone = phone,
                    password = password
                )

                AppDatabase.getDatabase(this@registeration)
                    .userDao()
                    .insertUser(user)

                Toast.makeText(this@registeration, "Registered Successfully", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this@registeration, dashboard::class.java))
                finish()
            }
        }


    }
}