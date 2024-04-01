package com.myrepo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.myrepo.MyApp
import com.myrepo.databinding.AdapterColabItemBinding
import com.myrepo.model.Owner
import com.myrepo.utill.showCircularImage

class AdapterContributor(val context: Context,val list : ArrayList<Owner>): Adapter<AdapterContributor.ViewHolder>() {

    inner class ViewHolder(val binding: AdapterColabItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterColabItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            MyApp.INSTANCE.applicationContext.showCircularImage(binding.imgColabAvtar,list[position].avatarUrl.toString())
        }
    }
}