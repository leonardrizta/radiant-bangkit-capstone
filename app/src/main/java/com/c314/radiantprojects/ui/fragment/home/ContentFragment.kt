package com.c314.radiantprojects.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.c314.radiantprojects.core.data.repository.Resource
import com.c314.radiantprojects.core.domain.model.ArticleDomain
import com.c314.radiantprojects.core.ui.adapter.ArticlesAdapter
import com.c314.radiantprojects.databinding.FragmentContentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContentFragment : Fragment() {

    private lateinit var binding: FragmentContentBinding
    private lateinit var mAdapter: ArticlesAdapter
    private val viewModel by viewModel<ContentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            mAdapter = ArticlesAdapter()
            viewModel.getArticles().observe(viewLifecycleOwner, articleObserver)

            with(binding.rvLatestInfo) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = mAdapter
            }
        }

    }

    private val articleObserver = Observer<Resource<List<ArticleDomain>>> { article ->
        if (article != null) {
            when (article) {
//                is Resource.Loading ->
                is Resource.Success -> {
                    mAdapter.setLatestInfo(article.data)
                }
                is Resource.Error -> {
                    Toast.makeText(context, "gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}