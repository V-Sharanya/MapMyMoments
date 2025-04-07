package com.sharanya.mmm.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import com.sharanya.mmm.R

class AppTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val fontPref = prefs.getString("FONT_STYLE", "font/open_sans.ttf") ?: "font/open_sans.ttf"

        val typeface: Typeface? = when (fontPref) {
            "font/dancing_script.ttf" -> ResourcesCompat.getFont(context, R.font.dancing_script)
            "font/korean.ttf" -> ResourcesCompat.getFont(context, R.font.korean)
            "font/oswald.ttf" -> ResourcesCompat.getFont(context, R.font.oswald)
            //"font/spanish.ttf" -> ResourcesCompat.getFont(context, R.font.spanish)
            else -> ResourcesCompat.getFont(context, R.font.open_sans)
        }

        setTypeface(typeface)
    }
}
