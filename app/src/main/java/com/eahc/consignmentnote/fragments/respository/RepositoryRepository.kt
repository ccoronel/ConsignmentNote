package com.eahc.consignmentnote.fragments.respository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eahc.consignmentnote.AppDatabase
import com.eahc.consignmentnote.BseRepository
import com.eahc.consignmentnote.Program
import com.eahc.consignmentnote.entities.Consignment
import com.eahc.consignmentnote.entities.Movement
import com.eahc.consignmentnote.wsapi.ApiResponse
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryRepository @Inject constructor(
    private var db: AppDatabase
    ): BseRepository() {
    fun getConsignment(consignmentNote: String ): MutableLiveData<ApiResponse> {

        val lApiResponse: MutableLiveData<ApiResponse> = MutableLiveData()

        val outputFormatter = SimpleDateFormat(" " +
                "dd.MM.yyyy", Locale("es", "ES")
        )
        val date = Date()

        val movement = Movement()
        movement.setProperty(1, "${Program.getEco()}|${Program.getUser()}")
        movement.setProperty(0, consignmentNote)
        movement.setProperty(2, outputFormatter.format(date))

        sendToWSMovement(movement, lApiResponse)

        return lApiResponse
    }

    fun getListConsignments(): LiveData<List<Consignment>> {
        return db.consignmentDao().getConsignments(Program.getEco(), Program.getUser())
    }

    fun deleteConsignment(consignmentNo: String, context: Context) {
        db.consignmentDao().removeConsignment(consignmentNo)
        deleteFile(consignmentNo, context)
    }

}