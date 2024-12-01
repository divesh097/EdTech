package com.example.edtech

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView

class splashscreen : AppCompatActivity() {
    private lateinit var l: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splashscreen)
        l=findViewById(R.id.p)
        l.playAnimation()
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this,loginactivity::class.java).also {
                startActivity(it)
                finish()
            }
        },2500)
    }
}