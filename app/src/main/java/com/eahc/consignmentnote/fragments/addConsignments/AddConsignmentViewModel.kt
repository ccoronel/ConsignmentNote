package com.eahc.consignmentnote.fragments.addConsignments

import android.content.Context
import android.view.View
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eahc.consignmentnote.Program
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.adapters.AddConsignmentListAdapter
import com.eahc.consignmentnote.dialog.GlobalDialog
import com.eahc.consignmentnote.entities.Consignment
import com.eahc.consignmentnote.entities.ConsignmentWS
import com.eahc.consignmentnote.entities.ProgressObj
import java.util.ArrayList

class AddConsignmentViewModel(
    private var repository: AddConsignmentRepository
) : ViewModel() {
    var showProgress: MutableLiveData<ProgressObj> = MutableLiveData()
    lateinit var consignmentAdapter: AddConsignmentListAdapter
    var consignmentList: MutableLiveData<List<ConsignmentWS>> = MutableLiveData()

    fun hasConsignmentAdapterInitialized(): Boolean{
        return this::consignmentAdapter.isInitialized
    }

    fun getConsignments(fragment: Fragment) {
        showProgress.postValue(ProgressObj(View.VISIBLE, "Buscando cartas porte"))
        return repository.getConsignments().observe(
            fragment.viewLifecycleOwner,
            {
                if(it != null && it.success) {
                    val consignList = repository.buildConsignmentList(it)
                    consignmentList.value = consignList

                    if (consignList.isEmpty()){
                        val dialog = GlobalDialog(fragment.requireContext(), "E", "No cuenta con cartas porte para descargar")
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.setCancelable(true)
                        dialog.setContentView(R.layout.message_dialog_simple)
                        dialog.show()
                    }
                } else {
                    val builder = AlertDialog.Builder(fragment.requireContext())
                    builder.setMessage(it.message[0])
                    val alert = builder.create()
                    alert.show()
                }
                showProgress.postValue(ProgressObj(View.GONE, ""))
            }
        )
    }

    fun addConsignments(selectedList: ArrayList<ConsignmentWS>, context: Context) {
        selectedList.forEach {
            repository.addConsignment(Consignment(
                it.unit,
                Program.getUser(),
                it.consignment,
                it.storedDate
            ))
            repository.storeFile(it.PDF, it.consignment, context)
        }
    }

}