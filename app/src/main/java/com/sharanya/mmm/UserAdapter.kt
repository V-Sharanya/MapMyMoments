package com.sharanya.mmm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAdapter(responseWrapper: ResponseWrapper) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val users: MutableList<users> = mutableListOf()

    init {
        users.addAll(responseWrapper.users) // Populate list
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtUserName: TextView = view.findViewById(R.id.txtUserName)
        val txtUserEmail: TextView = view.findViewById(R.id.txtUserEmail)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.txtUserName.text = user.name
        holder.txtUserEmail.text = user.email

        holder.btnDelete.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                deleteUser(user.id, position, holder)
            }
        }
    }

    override fun getItemCount(): Int = users.size

    private fun deleteUser(userId: Int, position: Int, holder: UserViewHolder) {
        RetrofitClient.instance.deleteUser(userId).enqueue(object : Callback<Unit?> {
            override fun onResponse(call: Call<Unit?>, response: Response<Unit?>) {
                if (response.isSuccessful) {
                    users.removeAt(position) // Remove from list
                    notifyItemRemoved(position) // Notify RecyclerView
                    notifyItemRangeChanged(position, users.size) // Prevent index issues

                    Toast.makeText(holder.itemView.context, "User deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(holder.itemView.context, "Delete failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }
}
