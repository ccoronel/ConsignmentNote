package com.eahc.consignmentnote.wsapi

import com.eahc.consignmentnote.Program
import com.eahc.consignmentnote.entities.Movement
import org.kobjects.base64.Base64
import org.ksoap2.HeaderProperty
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import java.util.*
import kotlin.collections.ArrayList

class ApiClient {


    fun sendPreSharedMessage(movement: Movement): ApiResponse {
        return runMessage(movement)
    }
}

private fun runMessage(movement: Movement): ApiResponse {
    val apiResponse = ApiResponse()
    apiResponse.consignmentNo = movement.getProperty(0).toString()
    try {

        val request = SoapObject(Program.NAMESPACE, Program.METHOD)
        request.addAttribute("xmlns:con", Program.NAMESPACE)
        val request2 = SoapObject("", "ConsultaPDFCartaPorte")

        val iteratorN = movement.propertyCount - 1
        for (i in 0..(iteratorN)) {
            request2.addProperty(movement.getPropertyNames(i), movement.getProperty(i))
        }

        request.addSoapObject(request2)

        val soapEnvelope = SoapSerializationEnvelope(SoapEnvelope.VER11)
        soapEnvelope.dotNet = true
        soapEnvelope.setOutputSoapObject(request)

        val headerList: ArrayList<HeaderProperty> = arrayListOf()
        val userPass = Program.getConnObj().User + ":" + Program.getConnObj().Password
        headerList.add(
            HeaderProperty(
                "Authorization",
                "Basic " + Base64.encode(userPass.toByteArray())
            )
        )

        val transport = HttpTransportSE(Program.getConnObj().URL, 1200000)
        transport.setReadTimeout(1200000)
        transport.debug = true
        transport.call(Program.SOAP_ACTION, soapEnvelope, headerList)

        transport.requestDump

        transport.responseDump

        if (soapEnvelope.response != null) {
            val response: SoapObject = if (soapEnvelope.response is SoapObject) {
                soapEnvelope.response as SoapObject
            } else {
                val loop = soapEnvelope.response as Vector<*>
                loop.lastElement() as SoapObject
            }

            var i = 0
            while (i < response.propertyCount) {
                val property = response.getPropertyInfo(i)
                if (property.name.equals("PDF") && response.getProperty(i)
                        .toString() != "anyType{}" && response.getProperty(i).toString() != ""
                ) {
                    apiResponse.message += response.getProperty(i).toString()
                }
                i++
            }
            apiResponse.success = !apiResponse.message.contains(Program.prefixError)

        }

        transport.serviceConnection.disconnect()

    } catch (ex: Exception) {
        apiResponse.success = false
        apiResponse.message.add(ex.message.toString())
    }

    return apiResponse
}