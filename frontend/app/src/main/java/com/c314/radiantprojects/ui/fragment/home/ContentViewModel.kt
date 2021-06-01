package com.c314.radiantprojects.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.c314.radiantprojects.core.data.repository.Resource
import com.c314.radiantprojects.core.data.source.firebase.Articles
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import com.c314.radiantprojects.core.domain.usecase.DataUseCase

import com.google.firebase.firestore.FirebaseFirestore

class ContentViewModel(private val dataUseCase: DataUseCase) : ViewModel() {


    fun getLatestInfo(): LiveData<Resource<List<LatestInfoDomain>>> =
        dataUseCase.getLatestInfo().asLiveData()

    fun getArticles() : LiveData<MutableList<Articles>> {
        val mutableData = MutableLiveData<MutableList<Articles>>()
        FirebaseFirestore.getInstance().collection("articles").get().addOnSuccessListener { result ->
            val listData = mutableListOf<Articles>()
            for(document in result){
                val date = document.getString("date")
                val desc = document.getString("desc")
                val title = document.getString("title")
                val imageUrl = document.getString("url_img")
                val article = Articles(
                    date!!,
                    desc!!,
                    title!!,
                    imageUrl!!
                )
                listData.add(article)
            }
            mutableData.value = listData
        }
        return mutableData
    }



}