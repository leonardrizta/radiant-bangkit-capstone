package com.c314.radiantprojects.ui.activity.result

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.c314.radiantprojects.databinding.ActivityResultBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras

        val disease = extras?.getString("disease")
        val image = extras?.getString("image")
        val file = extras?.getString("file")
        val fileUri = Uri.parse(image)
        val fileThis = File(file)



        if (extras != null) {
            if (disease == "Acne and Rosacea Photos") {
                FirebaseFirestore.getInstance().collection("disease").document("acne").get()
                    .addOnSuccessListener {
                        binding.titleDetail.text = it.getString("title")
                        binding.desc.text = it.getString("description")
                        binding.titleContent.text = "Symptoms"
                        binding.content.text = it.getString("symptoms")
                        binding.treatment.text = it.getString("treatments")

                    }
//                binding.ivDetail.setImageURI(fileUri)
                Glide.with(applicationContext).load(fileThis).into(binding.ivDetail)
            } else if (disease == "Actinic Keratosis Basal Cell Carcinoma and other Malignant Lesions") {
                FirebaseFirestore.getInstance().collection("disease").document("actinic").get()
                    .addOnSuccessListener {
                        binding.titleDetail.text = it.getString("title")
                        binding.desc.text = it.getString("description")
                        binding.titleContent.text = "Symptoms"
                        binding.content.text = it.getString("symptoms")
                        binding.treatment.text = it.getString("treatments")
                    }
//                binding.ivDetail.setImageURI(fileUri)
                Glide.with(applicationContext).load(fileThis).into(binding.ivDetail)
            } else if (disease == "Light Diseases and Disorders of Pigmentation") {
                FirebaseFirestore.getInstance().collection("disease")
                    .document("Pigmentationdisorder").get().addOnSuccessListener {
                    binding.titleDetail.text = it.getString("title")
                    binding.desc.text = it.getString("description")
                    binding.titleContent.text = "Symptoms"
                    binding.content.text = it.getString("symptoms")
                    binding.treatment.text = it.getString("treatments")
                }
//                binding.ivDetail.setImageURI(fileUri)
                Glide.with(applicationContext).load(fileThis).into(binding.ivDetail)
            } else if (disease == "Nail Fungus and other Nail Disease") {
                FirebaseFirestore.getInstance().collection("disease").document("nailfungus").get()
                    .addOnSuccessListener {
                        binding.titleDetail.text = it.getString("title")
                        binding.desc.text = it.getString("description")
                        binding.titleContent.text = "Symptoms"
                        binding.content.text = it.getString("symptoms")
                        binding.treatment.text = it.getString("treatments")
                    }
//                binding.ivDetail.setImageURI(fileUri)
                Glide.with(applicationContext).load(fileThis).into(binding.ivDetail)
            } else if (disease == "Eczema Photos") {
                FirebaseFirestore.getInstance().collection("disease").document("eczema").get()
                    .addOnSuccessListener {
                        binding.titleDetail.text = it.getString("title")
                        binding.desc.text = it.getString("description")
                        binding.titleContent.text = "Symptoms"
                        binding.content.text = it.getString("symptoms")
                        binding.treatment.text = it.getString("treatments")
                    }
                Glide.with(applicationContext).load(fileThis).into(binding.ivDetail)
            }

        }

    }


    companion object {
        const val disease = "disease"
        const val image = "image"
        const val file = "file"
    }


}
