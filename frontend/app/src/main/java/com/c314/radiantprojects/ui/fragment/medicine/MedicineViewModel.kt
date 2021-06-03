package com.c314.radiantprojects.ui.fragment.medicine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c314.radiantprojects.core.data.source.firebase.Articles
import com.c314.radiantprojects.core.data.source.firebase.Disease
import com.c314.radiantprojects.core.domain.usecase.DataUseCase
import com.google.firebase.firestore.FirebaseFirestore

class MedicineViewModel() : ViewModel() {

    fun getMedicine() : LiveData<MutableList<Disease>> {
        val mutableData = MutableLiveData<MutableList<Disease>>()
        FirebaseFirestore.getInstance().collection("disease").get().addOnSuccessListener { result ->
            val listData = mutableListOf<Disease>()
            for(document in result){
                val title = document.getString("title")
                val description = document.getString("description")
                val symptoms = document.getString("symptoms")
                val treatment = document.getString("treatments")
                val article = Disease(
                    title!!,
                    description!!,
                    symptoms!!,
                    treatment!!,
                )
                listData.add(article)
            }
            mutableData.value = listData
        }
        return mutableData
    }


}