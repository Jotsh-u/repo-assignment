package com.myrepo.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myrepo.db.DBHelper
import com.myrepo.db.RepoData
import com.myrepo.model.Resource
import com.myrepo.model.TrendingResponse
import com.myrepo.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val repoLiveData = MutableLiveData<Resource<ArrayList<RepoData>>>()

    fun getRepoLists(search: String?,context:Context){
        repoLiveData.value = Resource.Loading()
        viewModelScope.launch {

            RetrofitClient.apiService.getTrendingRepo(search).enqueue(object : Callback<TrendingResponse> {
                override fun onResponse(call: Call<TrendingResponse>, response: Response<TrendingResponse>) {
                    if (response.isSuccessful){
                        if (response.body()!=null){

                            val db = DBHelper(context, null)
                            val list : ArrayList<RepoData> = arrayListOf()
                            response.body()!!.items.forEach {
                                val model =RepoData(
                                    forksCount = it.forksCount,
                                    fullName = it.fullName,
                                    description = it.description,
                                    language = it.language,
                                    stargazersCount = it.stargazersCount,
                                    forks = it.forks
                                )
                                db.insertRepoData(model)
                                list.add(model)
                            }
//                            repoLiveData.value = Resource.Success(response.body()!!.items)
                            repoLiveData.value = Resource.Success(list)
                        } else {
                            repoLiveData.value = Resource.Error("Something went wrong.",
                                arrayListOf()
                            )
                        }
                    }else {
                        repoLiveData.value = Resource.Error("Something went wrong.",
                            arrayListOf()
                        )
                    }
                }

                override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
                    Log.e("ERROR","${t.message}")
                    repoLiveData.value = Resource.Error("${t.message}", arrayListOf())
                }

            })
        }
    }
}