package com.sanket.shaaditest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sanket.shaaditest.ViewModels.ProfileViewModel
import com.sanket.shaaditest.databases.ProfileData
import kotlinx.android.synthetic.main.list_single_profile_view.view.*

class ProfileDataAdapter internal constructor(context : Context,viewModel: ProfileViewModel): RecyclerView.Adapter<ProfileDataAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var profileList = emptyList<ProfileData>()
    private var maincontext = context
    private val profileViewModel = viewModel

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileDataAdapter.ViewHolder {
        val itemView = inflater.inflate(R.layout.list_single_profile_view, parent, false)
        return ViewHolder(itemView)
    }

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun getItemCount(): Int {
        return  profileList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile =  profileList[position]
        holder.itemView.tvName.text = profile.name
        holder.itemView.tvCityState.text = profile.state+", "+profile.country
        holder.itemView.tvAgeGender.text = profile.age+" yrs, "+profile.gender
        if (profile.acception_state == 0){
            holder.itemView.btnAccept.visibility = View.VISIBLE
            holder.itemView.btnReject.visibility = View.VISIBLE
            holder.itemView.btnActionState.visibility = View.GONE
            holder.itemView.btnAccept.setOnClickListener {
                profileViewModel.update(1,profile.id)
            }
            holder.itemView.btnReject.setOnClickListener {
                profileViewModel.update(2,profile.id)
            }
        }else{
            holder.itemView.btnAccept.visibility = View.GONE
            holder.itemView.btnReject.visibility = View.GONE
            holder.itemView.btnActionState.visibility = View.VISIBLE
            holder.itemView.btnActionState.text = if (profile.acception_state == 1) "ACCEPTED" else "REJECTED"
        }
        Glide.with(maincontext)
            .load(profile.profile_picture)
            .override(800,600)
            .fitCenter()
            .dontAnimate()
            .placeholder(R.drawable.default_dp)
            .into(holder.itemView.profileDp);


    }

    internal fun setProfile(profileDataList : List<ProfileData>){
        this.profileList = profileDataList
        notifyDataSetChanged()
    }
}