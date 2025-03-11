package com.sharanya.mmm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

class ViewPagerAdapter(private val context: Context) : PagerAdapter() {

    private val images = intArrayOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3
    )

    override fun getCount(): Int {
        return images.size // Returns the number of slides
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` // No need to cast to LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.slider_layout, container, false)

        val slideTitleImage: ImageView = view.findViewById(R.id.titleImage)
        slideTitleImage.setImageResource(images[position])

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View) // Cast to View instead of LinearLayout
    }
}
