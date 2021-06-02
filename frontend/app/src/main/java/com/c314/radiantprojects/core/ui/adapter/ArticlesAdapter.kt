package com.c314.radiantprojects.core.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.c314.radiantprojects.R
import com.c314.radiantprojects.core.data.source.firebase.Articles
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import com.c314.radiantprojects.databinding.ItemListArticlesBinding


class ArticlesAdapter()
    : RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>()  {

    private var dataList = mutableListOf<Articles>()


    fun setListData(data:MutableList<Articles>?){
        if (data != null) {
            dataList = data
        }
    }

    inner class ArticlesViewHolder(private val binding: ItemListArticlesBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Articles) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(article.url_img)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_broken)
                    )
                    .into(imgItems)
                tvTitle.text = article.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val itemsListArticlesBinding =
            ItemListArticlesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticlesViewHolder(itemsListArticlesBinding)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val mArticle = dataList[position]
        holder.bind(mArticle)
    }

    override fun getItemCount(): Int = dataList.size


}