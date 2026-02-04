package com.example.secondvision

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        val logout=findViewById<Button>(R.id.logout)
        sessionManager = SessionManager(this)

        recyclerView.layoutManager= LinearLayoutManager(this)
        lifecycleScope.launch {
            val users = AppDatabase.getDatabase(this@dashboard)
                .userDao()
                .getAllUsers()

            recyclerView.adapter = UserAdapter(users) { user ->
                val intent = Intent(this@dashboard, UserDetailActivity::class.java)
                intent.putExtra("userId", user.id)
                startActivity(intent)
            }

        }

        logout.setOnClickListener {
            sessionManager.logout()
            val intent = Intent(this, signin::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)        }
    }
}