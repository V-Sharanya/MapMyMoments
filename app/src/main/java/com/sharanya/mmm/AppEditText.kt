package com.sharanya.mmm.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import com.sharanya.mmm.R

class AppEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        applyCustomFont(context)
    }

    private fun applyCustomFont(context: Context) {
        val fontPref = PreferenceManager.getDefaultSharedPreferences(context)
            .getString("FONT_STYLE", "font/open_sans.ttf")

        val typeface: Typeface? = when (fontPref) {
            "font/dancing_script.ttf" -> ResourcesCompat.getFont(context, R.font.dancing_script)
          //  "font/korean.ttf" -> ResourcesCompat.getFont(context, R.font.korean)
            "font/oswald.ttf" -> ResourcesCompat.getFont(context, R.font.oswald)
            else -> ResourcesCompat.getFont(context, R.font.open_sans)
        }

        setTypeface(typeface)
    }
}
