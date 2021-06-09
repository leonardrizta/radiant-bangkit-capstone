package com.c314.radiantprojects.ui.fragment.medicine

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.c314.radiantprojects.R
import com.c314.radiantprojects.core.data.source.firebase.Disease
import com.c314.radiantprojects.core.ui.adapter.MedicineAdapter
import com.c314.radiantprojects.databinding.FragmentMedicineBinding
import com.c314.radiantprojects.ui.activity.detail.DetailActivity
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MedicineFragment : Fragment(R.layout.fragment_medicine) {


    private var binding: FragmentMedicineBinding? = null
    private val viewModel by viewModel<MedicineViewModel>()
    private lateinit var mAdapter: MedicineAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMedicineBinding.bind(view)

        if (activity != null) {
            mAdapter = MedicineAdapter()

            with(binding?.rvMedicine) {
                this?.layoutManager = GridLayoutManager(context, 2)
                this?.setHasFixedSize(true)
                this?.adapter = mAdapter
            }

            lifecycleScope.launch {
                withContext(Dispatchers.Main) {
                    delay(2000)
                    viewModel.getMedicine().observe(viewLifecycleOwner, medicineObserver)
                }
                coroutineContext.cancel()
            }

            mAdapter.setOnItemClickCallback(object : MedicineAdapter.OnItemClickCallback {
                override fun onItemClicked(disease: Disease) {
                    val intent = Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.detailTreatment, disease)
                        putExtra(DetailActivity.content, "treatment")
                    }
                    startActivity(intent)
                }

            })
        }
    }

    private val medicineObserver = Observer<MutableList<Disease>> {
        if (it != null) {
            binding?.shimmerMedicine?.stopShimmer()
            binding?.rvMedicine?.visibility = View.VISIBLE
            binding?.shimmerMedicine?.visibility = View.GONE
            mAdapter.setListData(it)
            mAdapter.notifyDataSetChanged()

        } else {
            binding?.shimmerMedicine?.stopShimmer()
            binding?.shimmerMedicine?.visibility = View.VISIBLE
            binding?.rvMedicine?.visibility = View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}



