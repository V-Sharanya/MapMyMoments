package com.sharanya.mmm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class PostAdapter(
    private val postList: MutableList<Post>,
    private val onLikeClicked: (Post) -> Unit,
    private val onLocationClicked: (Double, Double) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ImageView = itemView.findViewById(R.id.iv_profile)
        val userName: TextView = itemView.findViewById(R.id.tv_name)
        val destination: TextView = itemView.findViewById(R.id.tv_destination)
        val description: TextView = itemView.findViewById(R.id.tv_description)
        val viewPager: ViewPager2 = itemView.findViewById(R.id.viewPager)
        val likeButton: ImageView = itemView.findViewById(R.id.iv_like)
        val likeCount: TextView = itemView.findViewById(R.id.tv_like_count)
        val locationButton: ImageView = itemView.findViewById(R.id.iv_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]

        // Set post data
        holder.profileImage.setImageResource(post.profileImage)
        holder.userName.text = post.userName
        holder.destination.text = post.destination
        holder.description.text = post.description
        holder.likeCount.text = "${post.likeCount} likes"

        // Set ViewPager adapter for image slider
        val imageAdapter = ImageSliderAdapter(post.imageList)
        holder.viewPager.adapter = imageAdapter

        // Set initial like button state
        holder.likeButton.setImageResource(if (post.isLiked) R.drawable.ic_heartliked else R.drawable.ic_heart)

        // Handle like button click
        holder.likeButton.setOnClickListener {
            val currentPost = postList[position]

            // Toggle like state
            currentPost.isLiked = !currentPost.isLiked
            currentPost.likeCount += if (currentPost.isLiked) 1 else -1

            // Update only the like button and like count
            holder.likeButton.setImageResource(if (currentPost.isLiked) R.drawable.ic_heartliked else R.drawable.ic_heart)
            holder.likeCount.text = "${currentPost.likeCount} likes"

            // Notify HomeFragment
            onLikeClicked(currentPost)
        }


        // Handle location button click
        holder.locationButton.setOnClickListener {
            onLocationClicked(post.latitude, post.longitude)
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}
