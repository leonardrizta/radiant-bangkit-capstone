package com.c314.radiantprojects.ui.activity.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.c314.radiantprojects.databinding.ActivityResultBinding
import com.google.firebase.firestore.FirebaseFirestore

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)

        val extras = intent.extras

    }

}
