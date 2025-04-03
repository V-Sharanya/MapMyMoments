package com.sharanya.mmm

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var textView: TextView
    private lateinit var subheadingTextView: TextView
    private val fullText = "MapMyMoments"
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen2)

        textView = findViewById(R.id.textView) // Title
        subheadingTextView = findViewById(R.id.subtextView) // Subheading

        animateText()

        handler.postDelayed({
            val intent = Intent(this@SplashScreen, Onboarding::class.java)
            startActivity(intent)
            finish()
        }, 10000) // 5-second delay
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    private fun animateText() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (index <= fullText.length) {
                    textView.text = fullText.substring(0, index)
                    index++
                    handler.postDelayed(this, 150) // Adjust delay for speed
                } else {
                    showSubheading() // Show subheading after title animation
                }
            }
        }, 150)
    }

    private fun showSubheading() {
        subheadingTextView.text = "Where journeys unfold..."
        subheadingTextView.alpha = 0f // Initially invisible
        subheadingTextView.animate()
            .alpha(1f)
            .setDuration(1500) // 1.5 sec duration
            .setInterpolator(AccelerateDecelerateInterpolator()) // Ease-out effect
            .start()
    }
}
