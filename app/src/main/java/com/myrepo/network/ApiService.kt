package com.myrepo.network

import com.myrepo.model.Owner
import com.myrepo.model.TrendingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET(REPOSITORIES)
    fun getTrendingRepo(@Query("q") search : String?):Call<TrendingResponse>

    @GET()
    fun getContributorRepo(@Url url : String):Call<ArrayList<Owner>>

    companion object{
        const val REPOSITORIES = "/search/repositories"
    }

}