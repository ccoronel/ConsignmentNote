package com.eahc.consignmentnote

import android.content.Context
import android.util.Base64
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import com.eahc.consignmentnote.entities.Movement
import com.eahc.consignmentnote.wsapi.ApiClient
import com.eahc.consignmentnote.wsapi.ApiResponse
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import kotlin.concurrent.thread


open class BseRepository() {

    fun sendToWSMovement(movement: Movement, lApiResponse: MutableLiveData<ApiResponse>) {
        val client = ApiClient()
        thread {
            lApiResponse.postValue(
                client.sendPreSharedMessage(
                    movement
                )
            )
        }
    }

    fun storeFile(base64: String, consignmentNo: String, context: Context) {
        val fPath = "${Program.getWorkPath(context)}/consignments"

        if (!File(fPath).exists()) {
            File(fPath).mkdirs()
        }

        //Remove file if exists
        if (File(fPath, consignmentNo).exists()) {
            File(fPath, consignmentNo).delete()
        }

        try {
            val decodedString = Base64.decode(base64, Base64.DEFAULT)

            val dwldsPath = File(fPath, "${consignmentNo}.pdf")
            val os = FileOutputStream(dwldsPath, false)
            os.write(decodedString)
            os.flush()
            os.close()
        } catch (e: Exception) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(e.message)
            val alert = builder.create()
            alert.show()
        } finally {
        }
    }

    fun deleteFile(consignmentNo: String, context: Context){
        val fPath = "${Program.getWorkPath(context)}/consignments"
        val dwldsPath = File(fPath, "${consignmentNo}.pdf")
        dwldsPath.delete()
    }
}