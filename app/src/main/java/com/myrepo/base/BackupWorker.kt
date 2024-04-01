package com.myrepo.base

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.myrepo.db.DBRepository
import com.myrepo.db.UserRepo
import com.myrepo.model.TrendingResponse
import com.myrepo.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BackupWorker(val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {



    override suspend fun doWork(): Result {
        try {
            Log.e("WORK","Success Call WORK MANAGER")
           /* val response  =RetrofitClient.apiService.getTrendingRepo("Q").execute()
            if (response.body()!!.items.size > 0) {
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
                }
            }*/
            apiCall()
            return Result.success()
        }catch (e:Exception) {
            e.printStackTrace()
            Log.e("WORK","failure...${e.message}")
            return Result.failure()
        }

//        return Result.success()
    }

    fun insertRepoData(context: Context, data: UserRepo) {
        return DBRepository.insertData(context, data)
    }

    fun deleteAllRecord(context: Context) {
        return DBRepository.deleteAllRecord(context)
    }

    private suspend fun apiCall() {
        Log.e("WORK","WORK MANAGER")
        RetrofitClient.apiService.getTrendingRepo("Q")
            .enqueue(object : Callback<TrendingResponse> {
                override fun onResponse(
                    call: Call<TrendingResponse>,
                    response: Response<TrendingResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {

//                            deleteAllRecord(context)
                            if (response.body()!!.items.size > 0) {
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
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<TrendingResponse>, t: Throwable) {
                    Log.e("ERROR", "${t.message}")
                }
            })
    }
}