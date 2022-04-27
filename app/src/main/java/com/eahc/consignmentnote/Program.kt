package com.eahc.consignmentnote

import android.content.Context
import android.os.Environment
import com.eahc.consignmentnote.entities.ConnObj
import java.io.File

class Program {

    /**
     * Aplications constants, global variables and global methods
     *
     * @return A new instance of fragment OperationsFragment.
     */
    companion object {
        const val prefixError = "(E)"

        //WS variables
        const val NAMESPACE = "http://ephierro.com/ACS/consultaPDFCartaPorte"
        const val METHOD = "con:MT_ConsultaPDF_req"
        const val SOAP_ACTION = "$NAMESPACE/$METHOD"

        lateinit var WORK_PATH: String

        private var connObj: ConnObj? = null

        private var user: String = ""
        private var eco: String = ""

        fun setWorkPath(context: Context){
            val fPath = File(context.getExternalFilesDir(Environment.DIRECTORY_DCIM)!!.absolutePath)
            this.WORK_PATH = fPath.path
        }

        fun getWorkPath(context: Context): String{
            if(!this::WORK_PATH.isInitialized){
                setWorkPath(context)
            }
            return this.WORK_PATH
        }


        fun getUser(): String {
            return this.user
        }

        fun setEco(eco: String) {
            this.eco = eco
        }

        fun getEco(): String {
            return this.eco
        }

        fun setUser(user: String) {
            this.user = user
        }

        fun clearUser() {
            this.user = ""
        }

        fun clearEco() {
            this.eco = ""
        }

        fun setExpiration(days: Int, context: Context){
            val sharedPref = context.getSharedPreferences(
                context.getString(R.string.globalConfigs),
                Context.MODE_PRIVATE
            )

            val edit = sharedPref.edit()
            edit.putInt(context.getString(R.string.globalExpiration), days)
            edit.apply()
        }

        fun getExpiration(context: Context): Int{
            val sharedPref = context.getSharedPreferences(
                context.getString(R.string.globalConfigs),
                Context.MODE_PRIVATE
            )

            return sharedPref.getInt(context.getString(R.string.globalExpiration), 0)
        }

        fun removeConnObj() {
            this.connObj = null
        }

        fun setConnObj(connObj: ConnObj) {
            this.connObj = connObj
        }

        fun getConnObj(): ConnObj {
            return this.connObj!!
        }

        fun hasConnAssigned(): Boolean {
            return this.connObj != null
        }

        fun leadZeros(strValue: String?): String? {
            return strValue?.trimStart('0')
        }
    }
}