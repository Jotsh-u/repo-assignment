package com.myrepo.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.myrepo.MyApp
import com.myrepo.databinding.AdapterRepoItemBinding
import com.myrepo.db.UserRepo
import com.myrepo.model.Owner
import com.myrepo.network.RetrofitClient
import com.myrepo.utill.getColorBasedOnLang
import com.myrepo.utill.showCircularImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterTrending(
    private val list: ArrayList<UserRepo>
) : Adapter<AdapterTrending.ViewHolder>() {

    private var lastPosition = -1

    inner class ViewHolder(val binding: AdapterRepoItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterRepoItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        with(holder) {
            MyApp.INSTANCE.applicationContext.showCircularImage(
                binding.imgUserAvtar,
                model.avatarUrl.toString()
            )
            binding.txtTodayStar.text =
                model.forksCount.toString().plus(" stars today")
            binding.txtRepoName.text =
                model.fullName.toString()
            binding.txtRepoDescription.text =
                model.description.toString()
            binding.txtLanguageType.text =
                model.language.toString()
            binding.txtTotalStars.text =
                model.stargazersCount.toString()
            binding.txtTotalForks.text =
                model.forks.toString()

            binding.imgLanguageColor.setBackgroundColor(
                model.language.toString().getColorBasedOnLang()
            )

//            model.contributorUrl?.let { getContributor(it,binding.rvCollaborator) }
            val view = binding.conColExpand
            if (lastPosition == position) {
                view.visibility = if (view.isVisible) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            } else {
                view.visibility = View.GONE
            }

            binding.imgFavorite.setOnClickListener {

            }
            binding.cardView.setOnClickListener {
                lastPosition = if (lastPosition != position) {
                    position
                } else {
                    -1
                }
                notifyDataSetChanged()
            }
        }
    }

    private fun getContributor(urlLString: String, rvList: RecyclerView) {
        RetrofitClient.apiService.getContributorRepo(urlLString)
            .enqueue(object : Callback<ArrayList<Owner>> {
                override fun onResponse(
                    call: Call<ArrayList<Owner>>,
                    response: Response<ArrayList<Owner>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            rvList.apply {
                                layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
                                val list: ArrayList<Owner> = response.body()!!
                                adapter = AdapterContributor(context, list)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<Owner>>, t: Throwable) {
                    Log.e("ERROR", "${t.message}")
                }
            })
    }
}