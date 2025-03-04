package com.maou.busapp.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.maou.busapp.MainActivity
import com.maou.busapp.R
import com.maou.busapp.presentation.welcome.WelcomeActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, WelcomeActivity::class.java))
        }, 3000)
    }
}