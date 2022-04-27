package com.eahc.consignmentnote.fragments.login

import android.content.Context
import androidx.lifecycle.LiveData
import com.eahc.consignmentnote.AppDatabase
import com.eahc.consignmentnote.BseRepository
import com.eahc.consignmentnote.Program
import com.eahc.consignmentnote.entities.ConnObj
import com.eahc.consignmentnote.entities.Consignment
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(private var db: AppDatabase) : BseRepository() {

    fun setLogin(unit: String, user: String) {
        Program.setEco(unit)
        Program.setUser(user)
    }

    fun setActiveConnection(): LiveData<ConnObj> {
        return db.connectDao().getActive()
    }

    fun removeAllExpired(): LiveData<List<Consignment>> {
        return db.consignmentDao().removeExpired()
    }

    fun removeConsignment(consignment: Consignment) {
        db.consignmentDao().deleteConsignment(consignment)
    }
}