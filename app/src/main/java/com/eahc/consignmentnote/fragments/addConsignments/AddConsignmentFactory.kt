package com.eahc.consignmentnote.fragments.addConsignments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddConsignmentFactory(private val repository: AddConsignmentRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddConsignmentViewModel::class.java)) {
            AddConsignmentViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}