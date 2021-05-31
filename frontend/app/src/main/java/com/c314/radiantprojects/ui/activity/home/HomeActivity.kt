package com.c314.radiantprojects.ui.activity.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.c314.radiantprojects.R
import com.c314.radiantprojects.databinding.ActivityHomeBinding
import com.c314.radiantprojects.ui.activity.camera.CameraActivity
import com.c314.radiantprojects.ui.fragment.home.ContentFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                as NavHostFragment

        navController = navHostFragment.findNavController()

        binding.apply {
            bottomNavigationView.setupWithNavController(navController)
            bottomNavigationView.background = null
            bottomNavigationView.menu.getItem(1).isEnabled = false
            fab.setOnClickListener {
                val intent = Intent(this@HomeActivity, CameraActivity::class.java)
//                    .apply {
//                    putExtra(CameraActivity.CAMERA,"camera")
//                }
                startActivity(intent)
            }
        }

    }
}