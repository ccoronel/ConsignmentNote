package com.eahc.consignmentnote.entities

import org.ksoap2.serialization.KvmSerializable
import org.ksoap2.serialization.PropertyInfo
import java.util.*

class Movement : KvmSerializable {
    private var NumeroCartaPorte = ""
    private var Unidad = ""
    private var Fecha = ""

    override fun getProperty(p0: Int): Any {
        return when (p0) {
            0 -> this.NumeroCartaPorte
            1 -> this.Unidad
            2 -> this.Fecha
            else -> null!!
        }
    }

    override fun getPropertyCount(): Int {
        return 3
    }

    override fun setProperty(p0: Int, p1: Any?) {
        when (p0) {
            0 -> this.NumeroCartaPorte = p1.toString()
            1 -> this.Unidad = p1.toString()
            2 -> this.Fecha = p1.toString()
        }
    }

    override fun getPropertyInfo(p0: Int, p1: Hashtable<*, *>?, p2: PropertyInfo?) {
        when (p0) {
            0 -> {
                p2?.name = "NumeroCartaPorte"
                p2?.type = PropertyInfo.STRING_CLASS
            }
            1 -> {
                p2?.name = "Unidad"
                p2?.type = PropertyInfo.STRING_CLASS
            }
            2 -> {
                p2?.name = "Fecha"
                p2?.type = PropertyInfo.STRING_CLASS
            }
        }
    }

    fun getPropertyNames(p0: Int): String{
        return when (p0) {
            0 -> {
                "NumeroCartaPorte"
            }
            1 -> {
                "Unidad"
            }
            2 -> {
                "Fecha"
            }
            else -> {
                ""
            }
        }
    }
}