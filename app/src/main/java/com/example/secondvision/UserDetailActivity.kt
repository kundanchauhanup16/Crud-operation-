package com.example.secondvision

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class UserDetailActivity : AppCompatActivity() {

    private lateinit var user: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        // Views
        val nameEdt = findViewById<EditText>(R.id.nameEdt)
        val emailEdt = findViewById<EditText>(R.id.emailEdt)
        val phoneEdt = findViewById<EditText>(R.id.phoneEdt)
        val passwordEdt = findViewById<EditText>(R.id.passwordEdt) // Add password field
        val spinnerU = findViewById<Spinner>(R.id.spinnerGenderU)
        val updateBtn = findViewById<Button>(R.id.updateBtn)
        val deleteBtn = findViewById<Button>(R.id.deleteBtn)

        // Spinner setup
        val genderList = arrayOf("Select Gender", "Male", "Female", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerU.adapter = adapter

        // Get user ID from intent
        val userId = intent.getIntExtra("userId", -1)
        if (userId == -1) {
            Toast.makeText(this, "Invalid user ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Load user data from DB
        lifecycleScope.launch {
            user = AppDatabase.getDatabase(this@UserDetailActivity)
                .userDao()
                .getUserById(userId)

            // Set data in views
            nameEdt.setText(user.name)
            emailEdt.setText(user.mail)
            phoneEdt.setText(user.phone)
            passwordEdt.setText(user.password)

            // Set spinner selection based on user.gender
            val genderIndex = genderList.indexOf(user.gender)
            spinnerU.setSelection(if (genderIndex >= 0) genderIndex else 0)
        }

        // Update user
        updateBtn.setOnClickListener {
            val updatedName = nameEdt.text.toString().trim()
            val updatedEmail = emailEdt.text.toString().trim()
            val updatedPhone = phoneEdt.text.toString().trim()
            val updatedPassword = passwordEdt.text.toString().trim()
            val updatedGender = spinnerU.selectedItem.toString()

            if (updatedName.isEmpty() || updatedEmail.isEmpty() || updatedPhone.isEmpty() || updatedPassword.isEmpty() || updatedGender == "Select Gender") {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                user.name = updatedName
                user.mail = updatedEmail
                user.phone = updatedPhone
                user.password = updatedPassword
                user.gender = updatedGender

                AppDatabase.getDatabase(this@UserDetailActivity)
                    .userDao()
                    .updateUser(user)

                Toast.makeText(this@UserDetailActivity, "Updated Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Delete user
        deleteBtn.setOnClickListener {
            lifecycleScope.launch {
                AppDatabase.getDatabase(this@UserDetailActivity)
                    .userDao()
                    .deleteUser(user)

                Toast.makeText(this@UserDetailActivity, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
