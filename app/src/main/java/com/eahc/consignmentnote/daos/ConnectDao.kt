package com.eahc.consignmentnote.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.eahc.consignmentnote.entities.ConnObj

@Dao
interface ConnectDao{
    @Query("SELECT * FROM connProfile ORDER BY wsActive DESC")
    fun getAll(): LiveData<List<ConnObj>>

    @Query("SELECT * FROM connProfile WHERE wsActive = 1" )
    fun getActive(): LiveData<ConnObj>

    @Query("UPDATE connProfile SET wsActive = 1 WHERE profile = :profile")
    fun activateConnection(profile: String): Int

    @Query("UPDATE connProfile SET wsActive = 0 WHERE profile != :profile AND wsActive = 1")
    fun deactivateOthersConnections(profile: String): Int

    @Query("SELECT * FROM connProfile WHERE profile = :profile")
    fun findByProfile(profile:String): ConnObj

    @Insert
    fun insertAll(vararg connObj: ConnObj)

    @Delete
    fun delete(connObj: ConnObj)
}