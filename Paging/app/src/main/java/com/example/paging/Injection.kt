package com.example.paging

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.paging.Api.GithubService
import com.example.paging.Data.GithubRepository
import com.example.paging.Db.GithubLocalCache
import com.example.paging.Db.RepoDatabase
import com.example.paging.Ui.ViewModelFactory
import java.util.concurrent.Executors

object Injection
{
    /**
     *  database dao를 기반으로 하여 GithubLocalCache 인스턴스 생성
     */
    private fun provideCache(context : Context) : GithubLocalCache
    {
        val database = RepoDatabase.getInstance(context)
        return GithubLocalCache(database.reposeDao(), Executors.newSingleThreadExecutor())
    }

    /**
     *  githubservice를 기반으로 하여 GithubRepository 생성
     */
    private fun provideGithubRepository(context : Context) : GithubRepository
    {
        return GithubRepository(GithubService.create(), provideCache(context))
    }

    fun provideViewModelFactory(context : Context) : ViewModelProvider.Factory
    {
        return ViewModelFactory(provideGithubRepository(context))
    }




}