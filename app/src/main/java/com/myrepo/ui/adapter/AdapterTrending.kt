package com.myrepo.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.myrepo.R
import com.myrepo.databinding.AdapterRepoItemBinding
import com.myrepo.db.RepoData

class AdapterTrending(
    private val list: ArrayList<RepoData>
) : Adapter<AdapterTrending.ViewHolder>() {

    private var lastPosition = -1

    inner class ViewHolder(itemView: AdapterRepoItemBinding) :
        RecyclerView.ViewHolder(itemView.root)

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
            itemView.findViewById<AppCompatTextView>(R.id.txtTodayStar).text =
                model.forksCount.toString().plus(" stars today")
            itemView.findViewById<AppCompatTextView>(R.id.txtRepoName).text =
                model.fullName.toString()
            itemView.findViewById<AppCompatTextView>(R.id.txtRepoDescription).text =
                model.description.toString()
            itemView.findViewById<AppCompatTextView>(R.id.txtLanguageType).text =
                model.language.toString()
            itemView.findViewById<AppCompatTextView>(R.id.txtTotalStars).text =
                model.stargazersCount.toString()
            itemView.findViewById<AppCompatTextView>(R.id.txtTotalForks).text =
                model.forks.toString()

            val view = itemView.findViewById<ConstraintLayout>(R.id.conColExpand)
            if (lastPosition == position) {
                if (view.isVisible) {
                    view.visibility = View.GONE
                } else {
                    view.visibility = View.VISIBLE
                }
            } else {
                view.visibility = View.GONE
            }


            itemView.findViewById<AppCompatImageView>(R.id.imgFavorite).setOnClickListener {

            }
            itemView.findViewById<CardView>(R.id.cardView).setOnClickListener {

                lastPosition = if (lastPosition != position) {
                    position
                } else {
                    -1
                }
                notifyDataSetChanged()
            }
        }
    }
}