package com.myrepo.network

import com.myrepo.model.TrendingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(REPOSITORIES)
    fun getTrendingRepo(@Query("q") search : String?):Call<TrendingResponse>

    companion object{
        const val REPOSITORIES = "/search/repositories"
    }

}