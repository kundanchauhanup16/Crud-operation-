package com.example.secondvision

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class UserDetailActivity : AppCompatActivity() {

    private lateinit var user: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val nameEdt = findViewById<EditText>(R.id.nameEdt)
        val emailEdt = findViewById<EditText>(R.id.emailEdt)
        val phoneEdt = findViewById<EditText>(R.id.phoneEdt)
        val updateBtn = findViewById<Button>(R.id.updateBtn)
        val deleteBtn = findViewById<Button>(R.id.deleteBtn)

        val userId = intent.getIntExtra("userId", -1)

        lifecycleScope.launch {
            user = AppDatabase.getDatabase(this@UserDetailActivity)
                .userDao()
                .getUserById(userId)

            nameEdt.setText(user.name)
            emailEdt.setText(user.password)
            phoneEdt.setText(user.phone)
        }

        updateBtn.setOnClickListener {
            lifecycleScope.launch {
                user.name = nameEdt.text.toString()
                user.password = emailEdt.text.toString()
                user.phone = phoneEdt.text.toString()

                AppDatabase.getDatabase(this@UserDetailActivity)
                    .userDao()
                    .updateUser(user)

                finish()
            }
        }

        deleteBtn.setOnClickListener {
            lifecycleScope.launch {
                AppDatabase.getDatabase(this@UserDetailActivity)
                    .userDao()
                    .deleteUser(user)

                finish()
            }
        }
    }
}
