package com.example.secondvision

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val users: List<UserEntity>,
    private val clickListener: (UserEntity) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.nameTxt)
        val email = view.findViewById<TextView>(R.id.emailTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.name.text = user.name
        holder.email.text = user.password

        holder.itemView.setOnClickListener {
            clickListener(user)
        }
    }

    override fun getItemCount() = users.size

   /* fun updateData(newUsers: List<UserEntity>) {
        users = newUsers
        notifyDataSetChanged() // <-- refresh RecyclerView
    }*/
}
