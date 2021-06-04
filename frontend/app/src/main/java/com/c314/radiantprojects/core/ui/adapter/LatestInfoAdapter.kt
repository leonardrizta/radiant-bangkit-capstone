package com.c314.radiantprojects.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.c314.radiantprojects.R
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import com.c314.radiantprojects.databinding.ItemListLatestInfoBinding
import java.util.ArrayList

class LatestInfoAdapter : RecyclerView.Adapter<LatestInfoAdapter.LatestInfoViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private var list = ArrayList<LatestInfoDomain>()

    fun setLastInfo(latestInfo: List<LatestInfoDomain>?) {
        if (latestInfo == null) return
        this.list.clear()
        this.list.addAll(latestInfo)
    }

    inner class LatestInfoViewHolder(private val binding: ItemListLatestInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(latestInfo: LatestInfoDomain) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(latestInfo.urlImage)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_broken)
                    )
                    .into(ivArticles)

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(latestInfo)
                }
            }
        }

    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    interface OnItemClickCallback {
        fun onItemClicked(latestInfo: LatestInfoDomain)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestInfoViewHolder {
        val itemsListLatestInfoBinding =
            ItemListLatestInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LatestInfoViewHolder(itemsListLatestInfoBinding)
    }

    override fun onBindViewHolder(holder: LatestInfoViewHolder, position: Int) {
        val latestInfo = list[position]
        holder.bind(latestInfo)
    }

    override fun getItemCount(): Int = list.size
}