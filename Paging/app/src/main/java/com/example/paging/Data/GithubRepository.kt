package com.example.paging.Data

import android.arch.paging.LivePagedListBuilder
import android.util.Log
import com.example.paging.Api.GithubService
import com.example.paging.Db.GithubLocalCache
import com.example.paging.Model.RepoSearchResult

class GithubRepository(private val service : GithubService, private val cache : GithubLocalCache)
{
    private val tag = javaClass.simpleName
    private val DATABASE_PAGE_SIZE = 20

    fun search(query : String) : RepoSearchResult
    {
        Log.i(tag, "New query : $query")

        val dataSourceFactory = cache.reposByName(query)

        val boundaryCallback = RepoBoundaryCallback(query, service, cache)
        val networkErrors = boundaryCallback.networkErrors

        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()

        return RepoSearchResult(data, networkErrors)
    }
}