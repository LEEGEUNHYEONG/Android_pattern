package com.example.paging.Ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.example.paging.Data.GithubRepository
import com.example.paging.Model.Repo
import com.example.paging.Model.RepoSearchResult

class SearchRepositoriesViewModel(private val repository : GithubRepository) : ViewModel()
{
    private val queryLiveData = MutableLiveData<String>()
    private val repoResult : LiveData<RepoSearchResult> = Transformations.map(queryLiveData, {
        repository.search(it)
    })

    val repos : LiveData<PagedList<Repo>> = Transformations.switchMap(repoResult, { it -> it.data})

    val networkErrors : LiveData<String> = Transformations.switchMap(repoResult, {it-> it.networkErrors})

    fun searchRepo(queryString : String)
    {
        queryLiveData.postValue(queryString)
    }

    fun lastQueryValue() : String? = queryLiveData.value
}