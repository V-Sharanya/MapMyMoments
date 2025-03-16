package com.sharanya.mmm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private val postList = mutableListOf<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home2, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Populate the post list
        loadPosts()

        postAdapter = PostAdapter(
            postList,
            onLikeClicked = { post ->
                val index = postList.indexOf(post)
                if (index != -1) {
                    postList[index].isLiked = post.isLiked
                    postList[index].likeCount = post.likeCount
                    postAdapter.notifyItemChanged(index)
                }
            },
            onLocationClicked = { lat, long ->
                val uri = Uri.parse("geo:$lat,$long?q=$lat,$long")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.setPackage("com.google.android.apps.maps") // Open in Google Maps app
                startActivity(intent)
            }

        )
        recyclerView.adapter = postAdapter

        return view
    }

    private fun loadPosts() {
        postList.add(
            Post(
                profileImage = R.drawable.liveuser,
                userName = "John Doe",
                destination = "The Maldives",
                description = "Just arrived in the Maldives—crystal-clear waters and white sandy beaches feel like a dream! \uD83C\uDF0A\uD83C\uDFDD✨",
                imageList = listOf(R.drawable.post2, R.drawable.post1),
                likeCount = 100,
                isLiked = false,
                latitude = 3.202151,
                longitude = 73.16237
            )
        )

        postList.add(
            Post(
                profileImage = R.drawable.liveuser,
                userName = "Emma Watson",
                destination = "Bali, Indonesia",
                description = "Touchdown in Bali—lush green rice terraces, stunning beaches, and a magical vibe all around! \uD83C\uDF3F",
                imageList = listOf(R.drawable.post4, R.drawable.post3),
                likeCount = 150,
                isLiked = false,
                latitude = 8.4095,
                longitude = 115.1889
            )
        )
        postList.add(
            Post(
                profileImage = R.drawable.liveuser,
                userName = "Daniel Lee",
                destination = "Kyoto, Japan",
                description = "Exploring ancient temples and cherry blossoms in Kyoto! 🌸🏯",
                imageList = listOf(R.drawable.post7, R.drawable.post8),
                likeCount = 180,
                isLiked = false,
                latitude = 35.0116,
                longitude = 135.7681
            )
        )
        postList.add(
            Post(
                profileImage = R.drawable.liveuser,
                userName = "Olivia Brown",
                destination = "Santorini, Greece",
                description = "Sunsets in Santorini are pure magic! 🌅✨",
                imageList = listOf(R.drawable.post5, R.drawable.post6),
                likeCount = 220,
                isLiked = false,
                latitude = 36.3932,
                longitude = 25.4615
            )
        )



    }
}
