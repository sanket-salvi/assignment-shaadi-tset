package com.sanket.shaaditest.databases

import androidx.lifecycle.LiveData

class ProfileRepository(private val profileDao: ProfileDao) {
    val allProfiles : LiveData<List<ProfileData>> = profileDao.getAllProfile()

    suspend fun insert(profile: ProfileData){
        profileDao.insert(profile)
    }

    fun updateState(state: Int,id : Int){
        profileDao.updateState(state,id)
    }

     fun getProfiles()  : List<ProfileData>{
        return profileDao.getProfiles()
    }
}