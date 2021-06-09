package com.c314.radiantprojects.ui.activity.result

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.c314.radiantprojects.R
import com.c314.radiantprojects.databinding.ActivityResultBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.math.BigDecimal


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras = intent.extras

        val disease = extras?.getString("disease")
        val file = extras?.getString("file")
        val confidence = extras?.getString("confidence")

        val confidenceToBigDecimal =
            BigDecimal(confidence?.trim()?.replace("%", "")).divide(BigDecimal.valueOf(100))
        val confidenceToFloat = confidenceToBigDecimal.toFloat()

        val fileThis = File(file)

        if (extras != null) {
            if (confidenceToFloat > 0.69) {
                result(true)
                when (disease) {
                    "Acne and Rosacea" -> {
                        FirebaseFirestore.getInstance().collection("disease").document("acne").get()
                            .addOnSuccessListener {
                                binding.titleDetail.text = it.getString("title")
                                binding.desc.text = it.getString("description")
                                binding.titleContent.text = resources.getString(R.string.symptoms)
                                binding.content.text = it.getString("symptoms")
                                binding.treatment.text = it.getString("treatments")

                            }
                        binding.confidence.text = confidence
                        Glide.with(applicationContext).load(fileThis).into(binding.ivDetail)
                    }
                    "Actinic Keratosis" -> {
                        FirebaseFirestore.getInstance().collection("disease").document("actinic")
                            .get()
                            .addOnSuccessListener {
                                binding.titleDetail.text = it.getString("title")
                                binding.desc.text = it.getString("description")
                                binding.titleContent.text = resources.getString(R.string.symptoms)
                                binding.content.text = it.getString("symptoms")
                                binding.treatment.text = it.getString("treatments")
                            }
                        binding.confidence.text = confidence
                        Glide.with(applicationContext).load(fileThis).into(binding.ivDetail)
                    }
                    "Light Diseases and Disorders of Pigmentation" -> {
                        FirebaseFirestore.getInstance().collection("disease")
                            .document("Pigmentationdisorder").get().addOnSuccessListener {
                                binding.titleDetail.text = it.getString("title")
                                binding.desc.text = it.getString("description")
                                binding.titleContent.text = resources.getString(R.string.symptoms)
                                binding.content.text = it.getString("symptoms")
                                binding.treatment.text = it.getString("treatments")
                            }
                        binding.confidence.text = confidence
                        Glide.with(applicationContext).load(fileThis).into(binding.ivDetail)
                    }
                    "Nail Fungus and other Nail Disease" -> {
                        FirebaseFirestore.getInstance().collection("disease").document("nailfungus")
                            .get()
                            .addOnSuccessListener {
                                binding.titleDetail.text = it.getString("title")
                                binding.desc.text = it.getString("description")
                                binding.titleContent.text = resources.getString(R.string.symptoms)
                                binding.content.text = it.getString("symptoms")
                                binding.treatment.text = it.getString("treatments")
                            }
                        binding.confidence.text = confidence
                        Glide.with(applicationContext).load(fileThis).into(binding.ivDetail)
                    }
                    "Eczema" -> {
                        FirebaseFirestore.getInstance().collection("disease").document("eczema")
                            .get()
                            .addOnSuccessListener {
                                binding.titleDetail.text = it.getString("title")
                                binding.desc.text = it.getString("description")
                                binding.titleContent.text = resources.getString(R.string.symptoms)
                                binding.content.text = it.getString("symptoms")
                                binding.treatment.text = it.getString("treatments")
                            }
                        binding.confidence.text = confidence
                        Glide.with(applicationContext).load(fileThis).into(binding.ivDetail)
                    }
                }
            } else {
                result(false)
            }


        }

    }

    private fun result(state: Boolean) {
        if (state) {
            binding.noResult.lottieAnimNotFound.visibility = View.GONE
            binding.result.visibility = View.VISIBLE
            binding.noResult.tvNotFound.visibility = View.GONE
        } else {
            binding.noResult.lottieAnimNotFound.visibility = View.VISIBLE
            binding.result.visibility = View.GONE
            binding.noResult.tvNotFound.visibility = View.VISIBLE
        }
    }


    companion object {
        const val disease = "disease"
        const val file = "file"
        const val confidence = "confidence"
    }


}
