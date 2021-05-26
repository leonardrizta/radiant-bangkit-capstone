package com.c314.radiantprojects.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.c314.radiantprojects.R
import com.c314.radiantprojects.adapter.ArticlesAdapter
import com.c314.radiantprojects.adapter.LatestInfoAdapter
import com.c314.radiantprojects.databinding.FragmentContentBinding


class ContentFragment : Fragment() {
    private lateinit var binding: FragmentContentBinding

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
            val viewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            )[ContentViewModel::class.java]
            val latestInfo = viewModel.getArticles()

            val mAdapter = LatestInfoAdapter()
            mAdapter.setMovies(latestInfo)

            with(binding.rvLatestInfo) {
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                setHasFixedSize(true)
                adapter = mAdapter
            }
        }

    }


}