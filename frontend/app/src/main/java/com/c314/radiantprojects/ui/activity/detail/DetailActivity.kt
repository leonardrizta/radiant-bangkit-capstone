package com.c314.radiantprojects.ui.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.c314.radiantprojects.R
import com.c314.radiantprojects.core.data.source.firebase.Articles
import com.c314.radiantprojects.core.data.source.firebase.Disease
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import com.c314.radiantprojects.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        val detailLatestInfo = intent.getParcelableExtra<LatestInfoDomain?>(detailLatestInfo)
        val detailArticle = intent.getParcelableExtra<Articles?>(detailArticle)
        val detailTreatment = intent.getParcelableExtra<Disease?>(detailTreatment)

        if (extras != null) {
            val contentDetailLatestInfo = extras.getString(content)
            val contentDetailArticle = extras.getString(content)
            val contentDetailTreatment = extras.getString(content)
            if (contentDetailLatestInfo == "latestInfo") {
                showDetailLatestInfo(detailLatestInfo)
            } else if (contentDetailArticle == "article") {
                showDetailArticle(detailArticle)
            } else if (contentDetailTreatment == "treatment") {
                showDetailTreatment(detailTreatment)
            } else {
                Toast.makeText(this,"Nothing detail content founded",Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun showDetailTreatment(detailTreatment: Disease?) {
        binding.let {
            Glide.with(this)
                .load(detailTreatment?.image)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_broken)
                ).into(it.ivDetail)

            it.titleDetail.text = detailTreatment?.title
            it.desc.text = detailTreatment?.description
            it.titleContent.text = "Symptoms"
            it.content.text = detailTreatment?.symptoms
            it.treatment.text = detailTreatment?.treatment

        }

    }

    private fun showDetailArticle(detailArticle: Articles?) {
        binding.let{
            Glide.with(this)
                .load(detailArticle?.url_img)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_broken)
                ).into(it.ivDetail)

            it.titleDetail.text = detailArticle?.title
            it.dateDetail.text = detailArticle?.date
            it.desc.text = detailArticle?.desc
            it.titleContent.visibility = View.GONE
            it.titleTreatment.visibility = View.GONE
        }

    }

    private fun showDetailLatestInfo(detailLatestInfo: LatestInfoDomain?) {
        binding.let {
            Glide.with(this)
                .load(detailLatestInfo?.urlImage)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_broken)
                ).into(it.ivDetail)

            it.titleDetail.text = detailLatestInfo?.title
            it.authorDetail.text = detailLatestInfo?.author
            it.dateDetail.text = detailLatestInfo?.publishedAt
            it.desc.text = detailLatestInfo?.description
            it.content.text = detailLatestInfo?.content
            it.titleTreatment.visibility = View.GONE
        }
    }


    companion object{
        const val detailLatestInfo = "detailLatestInfo"
        const val detailArticle = "detailArticle"
        const val detailTreatment = "detailTreatment"
        const val content = "content"
    }
}