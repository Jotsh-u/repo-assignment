package com.myrepo

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.myrepo.network.ApiService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotSame
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    @Mock
    lateinit var mockWebServer: MockWebServer
    @Mock
    lateinit var apiService: ApiService
    private lateinit var gson: Gson
    @Before
    fun setup() {
        gson = GsonBuilder().create()
        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("https://api.github.com"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
    @After
    fun deconstruct() {
        mockWebServer.shutdown()
    }
    @Test
    fun validateRepoDataList_return_success(){
        runBlocking {
            val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody("200")
            mockWebServer.enqueue(mockResponse)

            val mockitoResponse = mockResponse.getBody()?.readUtf8()
            val response = apiService.getTrendingRepo("Q").execute()
            assertThat(response.body().toString()).isNotNull()
            assertEquals(mockitoResponse, response.code().toString())
        }
    }
    @Test
    fun validateRepoDataList_return_failure(){
        runBlocking {
            val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody("200")
            mockWebServer.enqueue(mockResponse)

            val mockitoResponse = mockResponse.getBody()?.readUtf8()
            val response = apiService.getTrendingRepo("").execute()
            assertThat(response.body().toString()).isNotNull()
            assertNotSame(mockitoResponse, response.code().toString())
        }
    }
/*
    @Test
    fun validateSearchApi_return_success(){
        runBlocking {
            val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody("200")
            mockWebServer.enqueue(mockResponse)

            val mockitoResponse = mockResponse.getBody()?.readUtf8()
            val response = apiService.getTrendingRepo("Q").execute()
            assertThat(response.body().toString()).isNotNull()
            assertEquals(mockitoResponse, response.code().toString())
        }
    }*/
}