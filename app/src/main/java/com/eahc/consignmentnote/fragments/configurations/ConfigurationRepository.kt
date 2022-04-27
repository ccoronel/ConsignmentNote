package com.eahc.consignmentnote.fragments.configurations

import androidx.lifecycle.LiveData
import com.eahc.consignmentnote.AppDatabase
import com.eahc.consignmentnote.entities.ConnObj
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class ConfigurationRepository @Inject constructor(private val db: AppDatabase) {
    fun addConnection(connObJ: ConnObj) {
        db.connectDao().insertAll(connObJ)
    }

    fun removeConnection(conn: ConnObj) {
        thread {
            db.connectDao().delete(conn)
        }
    }

    fun activateConnection(profile: String){
        thread {
            db.connectDao().activateConnection(profile)
            db.connectDao().deactivateOthersConnections(profile)
        }
    }

    fun getConnections(): LiveData<List<ConnObj>> = db.connectDao().getAll()
}