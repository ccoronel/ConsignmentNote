package com.eahc.consignmentnote.fragments.configurations

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.eahc.consignmentnote.Program
import com.eahc.consignmentnote.entities.ConnObj

class ConfigurationVewModel(
    private var repository: ConfigurationRepository
) : ViewModel() {

    var list: LiveData<List<ConnObj>>? = null

    fun addConnection(
        name: String,
        url: String,
        user: String,
        password: String,
        active:Boolean
    ) {
        val newConnection = ConnObj(
            name,
            url,
            user,
            password,
            active
        )
        repository.addConnection(newConnection)
    }

    fun removeConnection(conn: ConnObj) {
        repository.removeConnection(conn)
    }

    fun seActiveConnection(connection: ConnObj) {
        if (!connection.Active) {
            connection.Active = true
            Program.setConnObj(connection)
            repository.activateConnection(connection.profile)
        }
    }

    fun getConnections(): LiveData<List<ConnObj>> = repository.getConnections()
}