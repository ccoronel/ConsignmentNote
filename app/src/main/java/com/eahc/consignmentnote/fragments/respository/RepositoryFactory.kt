package com.eahc.consignmentnote.fragments.respository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eahc.consignmentnote.fragments.login.LoginViewModel

class RepositoryFactory(
    private val repository: RepositoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RepositoryViewModel::class.java)) {
            RepositoryViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}