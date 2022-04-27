package com.eahc.consignmentnote.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.*

@Entity(tableName = "consignments", primaryKeys = ["unit", "user", "consignment"])
data class Consignment(
    @ColumnInfo(name = "unit") val unit: String,
    @ColumnInfo(name = "user") val user: String,
    @ColumnInfo(name = "consignment") val consignment: String = "",
    @ColumnInfo(name = "storedDate") val storedDate: Date
)