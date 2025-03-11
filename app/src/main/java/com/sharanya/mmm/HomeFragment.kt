package com.sharanya.mmm

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.sharanya.mmm.R

class HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private val imageList = listOf(
        R.drawable.post1,
        R.drawable.post2,
        R.drawable.post3
    )

    private val sliderHandler = Handler(Looper.getMainLooper())

    private val sliderRunnable = Runnable {
        viewPager.currentItem = (viewPager.currentItem + 1) % imageList.size
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewPager = view.findViewById(R.id.viewPager)
        viewPager.adapter = ImageSliderAdapter(imageList)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000) // Auto-slide every 3 seconds
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString("param1", param1)
                    putString("param2", param2)
                }
            }
    }
}
