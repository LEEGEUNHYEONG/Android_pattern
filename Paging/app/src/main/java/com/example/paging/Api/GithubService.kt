package com.example.paging.Api

import android.util.Log
import com.example.paging.Model.Repo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val tag = "GithubService"
private val IN_QUALFIER= "in:name,description"


fun searchRepos(service : GithubService, query : String, page : Int, itemsPerPage : Int, onSuccess : (repos: List<Repo>) -> Unit, onError : (error : String) -> Unit)
{
    Log.i(tag, "qeury : $query / page : $page / itemsPerPage : $itemsPerPage")

    val apiQuery = query + IN_QUALFIER

    service.searchRepos(apiQuery, page, itemsPerPage).enqueue(object : Callback<RepoSearchResponse>
    {
        override fun onResponse(call: Call<RepoSearchResponse>?, response: Response<RepoSearchResponse>?)
        {
            Log.i(tag, "success to get data")

            if(response!!.isSuccessful)
            {
                val repos = response.body()?.items ?: emptyList()
                onSuccess(repos)
            }
            else
            {
                onError(response.errorBody().toString() ?: "Unknown error")
            }

        }

        override fun onFailure(call: Call<RepoSearchResponse>?, t: Throwable?)
        {
            Log.e(tag, "fail to get data")
            onError(t?.message ?: "unknown error")
        }
    })

}

interface GithubService
{
    @GET("search/repositories?sort=starts")
    fun searchRepos(@Query("q") query : String, @Query("page") page : Int, @Query("per_page") itemsPerPage: Int) : Call<RepoSearchResponse>

    companion object
    {
        val BASE_URL = "https://api.github.com/"

        fun create() : GithubService
        {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GithubService::class.java)
        }
    }
}