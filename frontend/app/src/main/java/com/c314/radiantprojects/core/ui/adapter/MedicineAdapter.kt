package com.c314.radiantprojects.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.c314.radiantprojects.R
import com.c314.radiantprojects.core.data.source.firebase.Articles
import com.c314.radiantprojects.core.data.source.firebase.Disease
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import com.c314.radiantprojects.databinding.GridLayoutBinding

class MedicineAdapter : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private var dataList = mutableListOf<Disease>()


    fun setListData(data:MutableList<Disease>?){
        if (data != null) {
            dataList = data
        }
    }

    inner class MedicineViewHolder(private val binding:  GridLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(disease: Disease) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(disease.image)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_broken)
                    )
                    .into(ivTreatment)
                titleTreatment.text = disease.title

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(disease)
                }
            }

        }

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    interface OnItemClickCallback {
        fun onItemClicked(disease: Disease)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val gridLayoutBinding = GridLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MedicineViewHolder(gridLayoutBinding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val mDisease = dataList[position]
        holder.bind(mDisease)
    }

    override fun getItemCount(): Int = dataList.size
}