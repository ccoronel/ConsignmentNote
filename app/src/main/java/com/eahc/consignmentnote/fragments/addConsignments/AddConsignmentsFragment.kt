package com.eahc.consignmentnote.fragments.addConsignments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eahc.consignmentnote.AppDatabase
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.adapters.AddConsignmentListAdapter
import com.eahc.consignmentnote.databinding.FragmentAddConsignmentsBinding
import com.eahc.consignmentnote.dialog.GlobalDialog

class AddConsignmentsFragment : Fragment() {

    private lateinit var mBinding: FragmentAddConsignmentsBinding
    private lateinit var db: AppDatabase
    private lateinit var factory: AddConsignmentFactory
    private lateinit var repository: AddConsignmentRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_consignments, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            this.resources.getString(R.string.titleSearchConsignments)

        db = AppDatabase(requireContext())
        repository = AddConsignmentRepository(db)
        factory = AddConsignmentFactory(repository)
        mBinding.addConsignmentViewModel =
            ViewModelProvider(this, factory).get(AddConsignmentViewModel::class.java)

        mBinding.listConsignments.layoutManager = LinearLayoutManager(requireContext())

        mBinding.addConsignmentViewModel?.getConsignments(this)

        mBinding.addConsignmentViewModel?.consignmentList?.observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    mBinding.addConsignmentViewModel?.consignmentAdapter = AddConsignmentListAdapter(it)
                    mBinding.listConsignments.adapter = mBinding.addConsignmentViewModel?.consignmentAdapter
                }
            }
        )

        mBinding.searchConsignments.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(mBinding.addConsignmentViewModel?.hasConsignmentAdapterInitialized() == true) {
                    mBinding.addConsignmentViewModel?.consignmentAdapter?.filter?.filter(newText)
                }
                return false
            }

        })

        mBinding.allConsignments.setOnCheckedChangeListener { compoundButton, b ->
            mBinding.addConsignmentViewModel?.consignmentList?.value?.forEach {
                it.selected = b
                if (b) {
                    mBinding.addConsignmentViewModel?.consignmentAdapter?.selectedList?.add(it)
                } else {
                    mBinding.addConsignmentViewModel?.consignmentAdapter?.selectedList?.remove(it)
                }
            }

            if(mBinding.addConsignmentViewModel?.hasConsignmentAdapterInitialized()!!) {
                mBinding.addConsignmentViewModel?.consignmentAdapter?.notifyDataSetChanged()
            }
        }

        mBinding.btnAdd.setOnClickListener {
            if (mBinding.listConsignments.adapter != null) {
                val adapter = mBinding.listConsignments.adapter as AddConsignmentListAdapter
                val selecteds = ArrayList(adapter.selectedList.filter { consignmentWS -> consignmentWS.selected })
                if(selecteds.size > 0) {
                    mBinding.addConsignmentViewModel?.addConsignments(selecteds, requireContext())
                    findNavController().navigate(R.id.action_addConsignmentsFragment_to_repositoryFragment)
                } else {
                    val dialog = GlobalDialog(requireContext(), "E", "Se debe seleccionar algun registro para continuar.")
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(true)
                    dialog.setContentView(R.layout.message_dialog_simple)
                    dialog.show()
                }
            }
        }

        mBinding.btnSkip.setOnClickListener {
            findNavController().navigate(R.id.action_addConsignmentsFragment_to_repositoryFragment)
        }

        mBinding.addConsignmentViewModel?.showProgress?.observe(
            viewLifecycleOwner,
            {
                if(it != null) {
                    mBinding.progreasslay.visibility = it.visibility
                    mBinding.progressBarinsideText.text = it.text
                } else {
                    mBinding.progreasslay.visibility = View.GONE
                    mBinding.progressBarinsideText.text = ""
                }
            }
        )

        return mBinding.root
    }

    override fun onResume() {
        mBinding.allConsignments.isChecked = false
        mBinding.allConsignments.isSelected = false
        super.onResume()
    }
}