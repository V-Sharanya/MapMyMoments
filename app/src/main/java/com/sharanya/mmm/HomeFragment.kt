package com.sharanya.mmm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize views using view.findViewById()
        val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)

        // List of images (Replace with your drawable resources)
        val imageList = listOf(
            R.drawable.post1,
            R.drawable.post2,
            R.drawable.post3
        )

        // Set up the adapter
        val adapter = ImageSliderAdapter(imageList)
        viewPager.adapter = adapter

        return view
    }
}
