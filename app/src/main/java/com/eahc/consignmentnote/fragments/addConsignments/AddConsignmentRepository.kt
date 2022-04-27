package com.eahc.consignmentnote.fragments.addConsignments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eahc.consignmentnote.AppDatabase
import com.eahc.consignmentnote.BseRepository
import com.eahc.consignmentnote.Program
import com.eahc.consignmentnote.entities.Consignment
import com.eahc.consignmentnote.entities.ConsignmentWS
import com.eahc.consignmentnote.entities.Movement
import com.eahc.consignmentnote.wsapi.ApiResponse
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddConsignmentRepository @Inject constructor(private val db: AppDatabase) : BseRepository() {
    fun getConsignments(): LiveData<ApiResponse> {
        val lApiResponse: MutableLiveData<ApiResponse> = MutableLiveData()

        val outputFormatter = SimpleDateFormat(
            "dd.MM.yyyy", Locale("es", "ES")
        )
        val date = Date()

        val movement = Movement()
        movement.setProperty(1, "${Program.getEco()}|${Program.getUser()}")
        movement.setProperty(2, outputFormatter.format(date))

        sendToWSMovement(movement, lApiResponse)

        return lApiResponse
    }

    fun buildConsignmentList(it: ApiResponse): List<ConsignmentWS> {
        val list: ArrayList<ConsignmentWS> = arrayListOf()

        it.message.forEach {
            val parts = it.split("|")
            if(!consignmentExist(Program.getEco(), Program.getUser(), parts[0])) {
                val consignment = ConsignmentWS(
                    Program.getEco(),
                    Program.getUser(),
                    parts[0],
                    parts[1],
                    Date(),
                    false
                )

                list.add(consignment)
            }
        }

        return list
    }

    fun addConsignment(consignment: Consignment){
        db.consignmentDao().addConsignment(consignment)
    }

    fun consignmentExist(unit: String, user: String, consignmentNo: String): Boolean{
        return db.consignmentDao().isConsignmentExist(unit,user,consignmentNo)
    }

}