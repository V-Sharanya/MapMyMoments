package com.sharanya.mmm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminUsersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_admin_users, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fetchUsers()  // Load users
        return view
    }

    private fun fetchUsers() {
        RetrofitClient.instance.getUsers().enqueue(object : Callback<ResponseWrapper> {
            override fun onResponse(call: Call<ResponseWrapper>, response: Response<ResponseWrapper>) {
                if (response.isSuccessful && response.body() != null) {
                    val responseWrapper = response.body()!!

                    // Initialize the adapter and set it to the RecyclerView
                    userAdapter = UserAdapter(responseWrapper)
                    recyclerView.adapter = userAdapter
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch users", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseWrapper>, t: Throwable) {
                Toast.makeText(requireContext(), "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
