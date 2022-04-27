package com.eahc.consignmentnote.fragments.respository

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.adapters.ConsignmentListAdapter
import com.eahc.consignmentnote.entities.Consignment
import com.eahc.consignmentnote.entities.ProgressObj
import com.eahc.consignmentnote.wsapi.ApiResponse

class RepositoryViewModel (
    private val repository: RepositoryRepository
) : ViewModel() {

    var showProgress: MutableLiveData<ProgressObj> = MutableLiveData()

    var consignmentListLiveData: LiveData<List<Consignment>> = MutableLiveData()
    var adapterConsignments: ConsignmentListAdapter = ConsignmentListAdapter(listOf())
    var doneLiveData = MutableLiveData<ApiResponse>()


    fun getConsignmentsList(){
        consignmentListLiveData = repository.getListConsignments()
    }

    fun storeFile(apiResponse: ApiResponse, context: Context){
        repository.storeFile(apiResponse.message[0], apiResponse.consignmentNo, context)
    }

    fun deleteConsignment(consignmentNo: String, context: Context) {
        repository.deleteConsignment(consignmentNo, context)
    }

}