package com.example.paging.Db

import android.arch.paging.DataSource
import android.util.Log
import com.example.paging.Model.Repo
import java.util.concurrent.Executor


class GithubLocalCache(private val repoDao : RepoDao, private val ioExecutor : Executor)
{
    private val tag = javaClass.simpleName

    fun insert(repos : List<Repo>, insertFinished: ()-> Unit)
    {
        ioExecutor.execute {
            Log.i(tag, "insert ${repos.size} repos")
            repoDao.insert(repos)
            insertFinished()
        }
    }

    fun reposByName(name : String) : DataSource.Factory<Int, Repo>
    {
        val query = "%${name.replace(' ', '%')}%"
        return repoDao.reposByName(query)
    }
}