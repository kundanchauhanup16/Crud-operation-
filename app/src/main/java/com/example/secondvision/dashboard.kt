package com.example.secondvision

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class dashboard : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val logout=findViewById<Button>(R.id.logout)
        sessionManager = SessionManager(this)


        logout.setOnClickListener {
            sessionManager.logout()
            val intent = Intent(this, signin::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)        }
        loadUsers() // refresh every time activity resumes

    }

    private fun loadUsers() {
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager= LinearLayoutManager(this)

        lifecycleScope.launch {
            val users = AppDatabase.getDatabase(this@dashboard)
                .userDao()
                .getAllUsers()
            if (users.isEmpty()) {
                // logout user if no data exists
                sessionManager.logout()

                val intent = Intent(this@dashboard, signin::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                return@launch
            }

            recyclerView.adapter = UserAdapter(users) { user ->
                val intent = Intent(this@dashboard, UserDetailActivity::class.java)
                intent.putExtra("userId", user.id)
                startActivity(intent)
            }

        }    }

    override fun onResume() {
        super.onResume()  // Always call super.onResume() first
        // Your code here
        // For example, refresh UI or restart animations
        loadUsers() // refresh every time activity resumes
    }
}