package com.eahc.consignmentnote.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.eahc.consignmentnote.entities.Consignment
import java.util.*

@Dao
interface ConsignmentDao {
    @Query("SELECT * FROM consignments WHERE unit = :unit AND user = :user")
    fun getConsignments(unit: String, user: String): LiveData<List<Consignment>>

    @Query("SELECT EXISTS(SELECT * FROM consignments WHERE unit = :unit AND user = :user AND consignment = :consignmentNo)")
    fun isConsignmentExist(unit: String, user: String, consignmentNo: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addConsignment(consignment: Consignment)

    @Query("DELETE FROM consignments WHERE consignment = :consignmentNo")
    fun removeConsignment(consignmentNo: String)

    @Query("SELECT * FROM consignments")
    fun removeExpired(): LiveData<List<Consignment>>

    @Delete
    fun deleteConsignment(consignment: Consignment)
}