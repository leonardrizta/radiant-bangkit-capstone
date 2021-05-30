package com.c314.radiantprojects.ui.activity.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.c314.radiantprojects.R
import com.c314.radiantprojects.ui.activity.home.HomeActivity
import com.c314.radiantprojects.databinding.ActivitySplashScreenBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val animSc = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        binding.tvSc.startAnimation(animSc)

        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            startActivity(Intent(this@SplashScreenActivity, HomeActivity::class.java))
            finish()
        }


    }
}