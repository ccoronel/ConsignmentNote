package com.eahc.consignmentnote.fragments.respository

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eahc.consignmentnote.AppDatabase
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.adapters.ConsignmentListAdapter
import com.eahc.consignmentnote.databinding.FragmentRepositoryBinding

class RepositoryFragment : Fragment(), ConsignmentListAdapter.EventListener {


    private lateinit var mBinding: FragmentRepositoryBinding
    private lateinit var factory: RepositoryFactory
    private lateinit var repository: RepositoryRepository
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_repository, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            this.resources.getString(R.string.titleDownloadedConsignments)

        db = AppDatabase(requireContext())
        repository = RepositoryRepository(db)
        factory = RepositoryFactory(repository)

        mBinding.repositoryViewModel =
            ViewModelProvider(this, factory).get(RepositoryViewModel::class.java)

        mBinding.listConsignments.layoutManager = LinearLayoutManager(requireContext())

        mBinding.repositoryViewModel?.getConsignmentsList()

        mBinding.repositoryViewModel?.consignmentListLiveData?.observe(
            viewLifecycleOwner, {
                mBinding.repositoryViewModel?.adapterConsignments = ConsignmentListAdapter(it)
                mBinding.repositoryViewModel?.adapterConsignments?.setEventListener(this)
                mBinding.repositoryViewModel?.adapterConsignments?.notifyDataSetChanged()
                mBinding.listConsignments.adapter =
                    mBinding.repositoryViewModel?.adapterConsignments
            }
        )
        mBinding.repositoryViewModel?.doneLiveData?.observe(
            viewLifecycleOwner,
            {
                if (it.success) {
                    mBinding.repositoryViewModel?.getConsignmentsList()
                }

            }
        )

        mBinding.repositoryViewModel?.showProgress?.observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    mBinding.progreasslay.visibility = it.visibility
                    mBinding.progressBarinsideText.text = it.text
                } else {
                    mBinding.progreasslay.visibility = View.GONE
                    mBinding.progressBarinsideText.text = ""
                }
            }
        )

        mBinding.searchConsignments.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mBinding.repositoryViewModel?.adapterConsignments?.filter?.filter(newText)
                return false
            }

        })

        return mBinding.root
    }


    override fun deleteConsignment(consignment: String) {
        mBinding.repositoryViewModel?.deleteConsignment(consignment, requireContext())
    }

    override fun handleConsignment(consignment: String) {
        mBinding.searchConsignments.setQuery("", false)
        mBinding.searchConsignments.clearFocus()
        val bundle = Bundle()
        bundle.putString("consignmentNo", consignment)
        findNavController().navigate(R.id.action_repositoryFragment_to_pdfViewFragment, bundle)
    }

}