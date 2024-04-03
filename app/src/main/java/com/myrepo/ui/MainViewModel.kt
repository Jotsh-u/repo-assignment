package com.myrepo.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myrepo.db.DBRepository
import com.myrepo.db.UserRepo
import com.myrepo.model.Resource
import com.myrepo.model.TrendingResponse
import com.myrepo.network.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val repoLiveData = MutableLiveData<Resource<ArrayList<UserRepo>>>()
    fun dataFromDB(context: Context, searchKeyword: String? = null) {
        repoLiveData.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val listRoom =
                    if (searchKeyword == null) DBRepository.getRepoData(context)!! else DBRepository.getAllSearchData(
                        context,
                        searchKeyword
                    )!!
                val list: ArrayList<UserRepo> = arrayListOf()
                listRoom.forEach {
                    list.add(it)
                }
                if (list.size == 0) {
                    val strSearchKey = searchKeyword ?: "Q"
                    getRepoLists(strSearchKey, context)
                } else {
                    repoLiveData.value = Resource.Success(list)
                }
//                repoLiveData.value = Resource.Success(list)
            } catch (e: Exception) {
                repoLiveData.value = Resource.Error("Something went wrong")
            }
        }
    }

    suspend fun getRepoData(context: Context): List<UserRepo>? {
        return DBRepository.getRepoData(context)!!
    }

    fun insertRepoData(context: Context, data: UserRepo) {
        return DBRepository.insertData(context, data)
    }

    fun deleteAllRecord(context: Context) {
        return DBRepository.deleteAllRecord(context)
    }

    private fun getRepoLists(search: String?, context: Context) {
        repoLiveData.value = Resource.Loading()
        viewModelScope.launch {
            RetrofitClient.apiService.getTrendingRepo(search)
                .enqueue(object : Callback<TrendingResponse> {
                    override fun onResponse(
                        call: Call<TrendingResponse>,
                        response: Response<TrendingResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val list: ArrayList<UserRepo> = arrayListOf()
                                response.body()!!.items.forEach {
                                    val modelUser = UserRepo(
                                        forksCount = it.forksCount,
                                        fullName = it.fullName,
                                        description = it.description,
                                        language = it.language,
                                        stargazersCount = it.stargazersCount,
                                        forks = it.forks,
                                        avatarUrl = it.owner?.avatarUrl,
                                        contributorUrl = it.contributorsUrl
                                    )
                                    insertRepoData(context, modelUser)
                                    list.add(modelUser)
                                }
                                repoLiveData.value = Resource.Success(list)
                            } else {
                                repoLiveData.value = Resource.Error(
                                    "Something went wrong.",
                                    arrayListOf()
                                )
                            }
                        } else {
                            repoLiveData.value = Resource.Error(
                                "Something went wrong.",
                                arrayListOf()
                            )
                        }
                    }

                    override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
                        Log.e("ERROR", "${t.message}")
                        repoLiveData.value = Resource.Error("${t.message}", arrayListOf())
                    }
                })
        }
    }
}