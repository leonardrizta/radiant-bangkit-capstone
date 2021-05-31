package com.c314.radiantprojects.ui.fragment.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.c314.radiantprojects.R
import com.c314.radiantprojects.core.data.repository.Resource
import com.c314.radiantprojects.core.domain.model.LatestInfoDomain
import com.c314.radiantprojects.core.ui.adapter.ArticlesAdapter
import com.c314.radiantprojects.core.ui.adapter.LatestInfoAdapter
import com.c314.radiantprojects.databinding.FragmentContentBinding
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ContentFragment : Fragment(R.layout.fragment_content) {

    private var binding: FragmentContentBinding? = null
    private lateinit var mAdapter: LatestInfoAdapter
    private val viewModel by viewModel<ContentViewModel>()

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentContentBinding.inflate(layoutInflater, container, false)
//        return binding.root
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContentBinding.bind(view)
        if (activity != null) {

            mAdapter = LatestInfoAdapter()

            lifecycleScope.launch {
                withContext(Dispatchers.Main){
                    Log.d("coro","viewmodel start")
                    delay(1500)
                    viewModel.getLatestInfo().observe(viewLifecycleOwner, articleObserver)
                }
                coroutineContext.cancel()
                Log.d("coro","end")
            }



            with(binding?.rvLatestInfo) {
                this?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                this?.setHasFixedSize(true)
                this?.adapter = mAdapter
            }
        }

    }

    private val articleObserver = Observer<Resource<List<LatestInfoDomain>>> { latestInfo ->
        if (latestInfo != null) {
            when (latestInfo) {
                is Resource.Loading -> binding?.shimmerLatestInfo?.startShimmer()
                is Resource.Success -> {
                    binding?.shimmerLatestInfo?.stopShimmer()
                    binding?.shimmerLatestInfo?.visibility = View.GONE
                    binding?.rvLatestInfo?.visibility = View.VISIBLE
                    mAdapter.setLastInfo(latestInfo.data)
                }
                is Resource.Error -> {
                    Toast.makeText(context, "gagal", Toast.LENGTH_SHORT).show()
                    binding?.shimmerLatestInfo?.stopShimmer()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}