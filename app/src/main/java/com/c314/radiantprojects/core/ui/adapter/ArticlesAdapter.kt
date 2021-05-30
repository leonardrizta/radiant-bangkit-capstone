package com.c314.radiantprojects.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c314.radiantprojects.core.domain.model.ArticleDomain
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import com.c314.radiantprojects.databinding.ItemListLatestInfoBinding
import java.util.ArrayList

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {
    private var list = ArrayList<ArticleDomain>()

    fun setLatestInfo(latestInfo: List<ArticleDomain>?) {
        if (latestInfo == null) return
        this.list.clear()
        this.list.addAll(latestInfo)
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(private val binding: ItemListLatestInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(latestInfo: ArticleDomain) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(latestInfo.urlImage)
//                    .apply(
//                        RequestOptions.placeholderOf(R.drawable.ic_loading)
//                            .error(R.drawable.ic_broken)
//                    )
                    .into(ivArticles)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemsListLatestInfoBinding =
            ItemListLatestInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(itemsListLatestInfoBinding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val latestInfo = list[position]
        holder.bind(latestInfo)
    }

    override fun getItemCount(): Int = list.size


}