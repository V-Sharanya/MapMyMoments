package com.sharanya.mmm

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.text.Html
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager


class Onboarding : AppCompatActivity() {

    private lateinit var mSLideViewPager: ViewPager
    private lateinit var mDotLayout: LinearLayout
    private lateinit var skipbtn: Button
    private lateinit var dots: Array<TextView>
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        skipbtn = findViewById(R.id.skipButton)

        skipbtn.setOnClickListener {
            val i = Intent(this, login::class.java)
            startActivity(i)
            finish()
        }

        mSLideViewPager = findViewById(R.id.slideViewPager)
        mDotLayout = findViewById(R.id.indicator_layout)
        viewPagerAdapter = ViewPagerAdapter(this)

        mSLideViewPager.adapter = viewPagerAdapter
        setUpIndicator(0)
        mSLideViewPager.addOnPageChangeListener(viewListener)
    }

    private fun setUpIndicator(position: Int) {
        dots = Array(2) { TextView(this) }
        mDotLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i].text = Html.fromHtml("&#8226")
            dots[i].textSize = 35f
            dots[i].setTextColor(resources.getColor(R.color.inactive, theme))
            mDotLayout.addView(dots[i])
        }

        dots[position].setTextColor(resources.getColor(R.color.active, theme))
    }

    private val viewListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageSelected(position: Int) {
            setUpIndicator(position)
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    private fun getItem(i: Int): Int {
        return mSLideViewPager.currentItem + i
    }
}


