package com.c314.radiantprojects.ui.activity.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c314.radiantprojects.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras

        val diseaseTitle = extras?.getString("disease")

        binding.titleDisease.text = diseaseTitle

    }


    companion object{
        const val disease = "disease"
    }
}