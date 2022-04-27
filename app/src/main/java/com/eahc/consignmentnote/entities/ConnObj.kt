package com.eahc.consignmentnote.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "connProfile")
data class ConnObj(
    /** "http://vhephxidci.hec.palaciohierro.com.mx:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=SYS_EPH_SCNR_DEV&receiverParty=&receiverService=&interface=GoodsMovement_out&interfaceNamespace=http:/LGCY/sendGoodsMovement"
     * "HTTPSCNR"
     * "SCNRdev50"
     */
    @PrimaryKey val profile: String,
    @ColumnInfo(name = "wsUrl") val URL: String = "",
    @ColumnInfo(name = "wsUser") val User: String = "",
    @ColumnInfo(name = "wsPassword") val Password: String = "",
    @ColumnInfo(name = "wsActive") var Active: Boolean = false
){
    override fun toString(): String {
        return profile
    }
}