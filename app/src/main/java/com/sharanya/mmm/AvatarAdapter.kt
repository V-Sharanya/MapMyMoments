package com.sharanya.mmm

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class AvatarAdapter(private val context: Context, private val avatars: Array<Int>) : BaseAdapter() {

    override fun getCount(): Int = avatars.size

    override fun getItem(position: Int): Any = avatars[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView = ImageView(context)
        imageView.setImageResource(avatars[position])
        imageView.layoutParams = ViewGroup.LayoutParams(200, 200)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return imageView
    }
}
