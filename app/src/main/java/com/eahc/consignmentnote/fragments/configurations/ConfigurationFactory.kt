package com.eahc.consignmentnote.fragments.configurations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ConfigurationFactory(
    private val repository: ConfigurationRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ConfigurationVewModel::class.java)) {
            ConfigurationVewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}