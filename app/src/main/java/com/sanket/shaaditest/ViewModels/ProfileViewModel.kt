package com.sanket.shaaditest.ViewModels

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sanket.shaaditest.NetworkCallBacks
import com.sanket.shaaditest.databases.MyDatabase
import com.sanket.shaaditest.databases.ProfileData
import com.sanket.shaaditest.databases.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository :ProfileRepository

    var allProfiles : LiveData<List<ProfileData>>

    val profileDao = MyDatabase.getDatabase(application,viewModelScope).profileDao()

    init {

        repository = ProfileRepository(profileDao)
        allProfiles = repository.allProfiles

    }

    fun fetchDataFromServer(
        actvity: Activity,
        callBacks: NetworkCallBacks
    ){
        val queue = Volley.newRequestQueue(actvity)
        val url = "https://randomuser.me/api/?results=10"


        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->

                callBacks.onSuccess(response)
                val jsonResponse = JSONObject(response)
                if (jsonResponse.has("results")){
                    val resultJson = jsonResponse.getJSONArray("results")
                    if (resultJson.length() > 0){

                        for (i in 0 until resultJson.length()) {
                            val detailsJson: JSONObject = resultJson.getJSONObject(i)

                            val nameJson = detailsJson.getJSONObject("name")
                            val locationJson = detailsJson.getJSONObject("location")
                            val dobJson = detailsJson.getJSONObject("dob")
                            val pictureJson = detailsJson.getJSONObject("picture")
                            val profileData = ProfileData()
                            profileData.name = nameJson.getString("first")
                            profileData.age = dobJson.getString("age")
                            profileData.gender = detailsJson.getString("gender")
                            profileData.profile_picture = pictureJson.getString("large")
                            profileData.state = locationJson.getString("state")
                            profileData.country = locationJson.getString("country")

                            insert(profileData)
                        }
                    }
                }

            },
            Response.ErrorListener { error ->
                Log.d("Error",error.localizedMessage)
                callBacks.onFailure(error.localizedMessage)
            })


        queue.add(stringRequest)
    }

    fun insert(profile: ProfileData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(profile)
    }

    fun update(state: Int,id : Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateState(state,id)
    }
     fun getProfiles() : List<ProfileData>{
        return repository.getProfiles()
    }

}