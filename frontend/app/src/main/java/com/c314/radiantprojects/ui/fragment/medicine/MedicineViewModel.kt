package com.c314.radiantprojects.ui.fragment.medicine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c314.radiantprojects.core.data.source.firebase.Disease
import com.google.firebase.firestore.FirebaseFirestore

class MedicineViewModel : ViewModel() {

    fun getMedicine(): LiveData<MutableList<Disease>> {
        val mutableData = MutableLiveData<MutableList<Disease>>()
        FirebaseFirestore.getInstance().collection("disease").get().addOnSuccessListener { result ->
            val listData = mutableListOf<Disease>()
            for (document in result) {
                val title = document.getString("title")
                val description = document.getString("description")
                val symptoms = document.getString("symptoms")
                val image = document.getString("image")
                val treatment = document.getString("treatments")
                val article = Disease(
                    title!!,
                    description!!,
                    symptoms!!,
                    treatment!!,
                    image!!
                )
                listData.add(article)
            }
            mutableData.value = listData
        }
        return mutableData
    }


}