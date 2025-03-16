package com.sharanya.mmm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class HomeFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var likeButton: ImageView
    private lateinit var likeCountText: TextView

    private var isLiked = false
    private var likeCounter = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize ViewPager
        val viewPager1: ViewPager2 = view.findViewById(R.id.viewPager1)
        val viewPager2: ViewPager2 = view.findViewById(R.id.viewPager2)

        // Find all like buttons and count text views
        val likeButton1: ImageView = view.findViewById(R.id.iv_like)
        val likeCountText1: TextView = view.findViewById(R.id.tv_like_count)

        val likeButton2: ImageView = view.findViewById(R.id.iv_like2)
        val likeCountText2: TextView = view.findViewById(R.id.tv_like_count2)

        // Set up like functionality for Post 1
        var isLiked1 = false
        var likeCounter1 = 100
        likeCountText1.text = "$likeCounter1 likes"

        likeButton1.setOnClickListener {
            isLiked1 = toggleLike(likeButton1, likeCountText1, isLiked1, likeCounter1)
            likeCounter1 = if (isLiked1) likeCounter1 + 1 else likeCounter1 - 1
        }

        // Set up like functionality for Post 2
        var isLiked2 = false
        var likeCounter2 = 150
        likeCountText2.text = "$likeCounter2 likes"

        likeButton2.setOnClickListener {
            isLiked2 = toggleLike(likeButton2, likeCountText2, isLiked2, likeCounter2)
            likeCounter2 = if (isLiked2) likeCounter2 + 1 else likeCounter2 - 1
        }

        // Set up ViewPager Adapter
        val imageList1 = listOf(
            R.drawable.post2,
            R.drawable.post1,

        )
        val imageList2 = listOf(
            R.drawable.post4,
            R.drawable.post3,
            )
        val adapter1 = ImageSliderAdapter(imageList1)
        val adapter2 = ImageSliderAdapter(imageList2)
        viewPager1.adapter = adapter1
        viewPager2.adapter = adapter2

        return view
    }



    private fun toggleLike(likeButton: ImageView, likeCountText: TextView, isLiked: Boolean, likeCounter: Int): Boolean {
        if (isLiked) {
            likeButton.setImageResource(R.drawable.ic_heart) // Unliked
            likeCountText.text = "${likeCounter - 1} likes"
            return false
        } else {
            likeButton.setImageResource(R.drawable.ic_heartliked) // Liked
            likeCountText.text = "${likeCounter + 1} likes"
            return true
        }
    }

}
