package com.sanket.shaaditest.databases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(profile: ProfileData)

    @Query("Update profile_data SET acception_state =:state WHERE id =:id")
    fun updateState(state : Int,id : Int)

    @Query("SELECT * FROM profile_data ORDER BY id ASC")
    fun getAllProfile(): LiveData<List<ProfileData>>

    @Query("SELECT * FROM profile_data ORDER BY id ASC")
    fun getProfiles(): List<ProfileData>

}