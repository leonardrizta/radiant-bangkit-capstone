package com.c314.radiantprojects.ui.activity.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.c314.radiantprojects.databinding.ActivityResultBinding
import com.google.firebase.firestore.FirebaseFirestore

class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras

        val diseaseTitle = extras?.getString("disease")

        if(diseaseTitle == "Acne and Rosacea Photos"){
            FirebaseFirestore.getInstance().collection("disease").document("acne").get().addOnSuccessListener {
                binding.titleDetail.text = it.getString("title")
                binding.desc.text = it.getString("description")
                binding.titleContent.text = "Symptoms"
                binding.content.text = it.getString("symptoms")
                binding.treatment.text = it.getString("treatments")
            }

        } else if (diseaseTitle == "Light Diseases and Disorders of Pigmentation"){
            FirebaseFirestore.getInstance().collection("disease").document("Pigmentationdisorder").get().addOnSuccessListener {
                binding.titleDetail.text = it.getString("title")
                binding.desc.text = it.getString("description")
                binding.titleContent.text = "Symptoms"
                binding.content.text = it.getString("symptoms")
                binding.treatment.text = it.getString("treatments")
            }

        } else if(diseaseTitle == "Actinic Keratosis Basal Cell Carcinoma and other Malignant Lesions"){
            FirebaseFirestore.getInstance().collection("disease").document("actinic").get().addOnSuccessListener {
                binding.titleDetail.text = it.getString("title")
                binding.desc.text = it.getString("description")
                binding.titleContent.text = "Symptoms"
                binding.content.text = it.getString("symptoms")
                binding.treatment.text = it.getString("treatments")
            }

        } else if(diseaseTitle == "Eczema Photos"){
            FirebaseFirestore.getInstance().collection("disease").document("Eczema").get().addOnSuccessListener {
                binding.titleDetail.text = it.getString("title")
                binding.desc.text = it.getString("description")
                binding.titleContent.text = "Symptoms"
                binding.content.text = it.getString("symptoms")
                binding.treatment.text = it.getString("treatments")
            }
        } else if(diseaseTitle == "Nail Fungus and other Nail Disease"){
            FirebaseFirestore.getInstance().collection("disease").document("Nail fungus").get().addOnSuccessListener {
                binding.titleDetail.text = it.getString("title")
                binding.desc.text = it.getString("description")
                binding.titleContent.text = "Symptoms"
                binding.content.text = it.getString("symptoms")
                binding.treatment.text = it.getString("treatments")
            }
        } else {
            Toast.makeText(this,"nothing to result",Toast.LENGTH_SHORT).show()
        }

    }

    companion object{
        const val disease = "disease"
    }
}