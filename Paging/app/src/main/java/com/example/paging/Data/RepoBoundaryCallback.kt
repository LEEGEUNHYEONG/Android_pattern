package com.example.paging.Data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import android.util.Log
import com.example.paging.Api.GithubService
import com.example.paging.Api.searchRepos
import com.example.paging.Db.GithubLocalCache
import com.example.paging.Model.Repo

class RepoBoundaryCallback (private val query : String, private val service : GithubService, private val cache : GithubLocalCache) : PagedList.BoundaryCallback<Repo>()
{
    private val tag = javaClass.simpleName
    private val NETWORK_PAGE_SIZE = 50

    private var lastRequestedPage = 1

    private val tempNetworkErrors = MutableLiveData<String>()

    val networkErrors : LiveData<String>
        get() = tempNetworkErrors

    private var isRequestInProgress = false

    override fun onZeroItemsLoaded()
    {
        Log.i(tag, "onZeroItemsLoaded")
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Repo)
    {
        Log.i(tag, "onItemAtEndLoaded")
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query : String)
    {
        if(isRequestInProgress)
            return

        isRequestInProgress = true

        searchRepos(service, query, lastRequestedPage, NETWORK_PAGE_SIZE,
                {
                    repos -> cache.insert(repos,
                        {
                            lastRequestedPage++
                            isRequestInProgress = false
                        })
                },
                {
                    error -> tempNetworkErrors.postValue(error)
                    isRequestInProgress = false

                })

    }

}
