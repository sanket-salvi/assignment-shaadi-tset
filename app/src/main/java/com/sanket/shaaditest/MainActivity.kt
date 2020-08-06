package com.sanket.shaaditest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanket.shaaditest.ViewModels.ProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val adapter = ProfileDataAdapter(this,profileViewModel)
        profileList.adapter = adapter
        profileList.layoutManager = LinearLayoutManager(this)


        profileViewModel.allProfiles.observe(this, Observer { contact ->
            // Update the cached copy of the words in the adapter.
            contact?.let { adapter.setProfile(it) }
        })
        //if(profileViewModel.getProfiles().size < 1)
        profileViewModel.fetchDataFromServer(this,object : NetworkCallBacks{
            override fun onSuccess(response: String) {
                tvError.visibility = View.GONE
            }

            override fun onFailure(errorMessage: String) {
                if (profileViewModel.getProfiles().size < 1){
                    tvError.visibility = View.VISIBLE
                    tvError.text = "Unable to fetch data. Kindly check your network connection"
                    profileList.visibility = View.GONE
                }else{
                    tvError.visibility = View.GONE
                }
            }

        })



    }
}