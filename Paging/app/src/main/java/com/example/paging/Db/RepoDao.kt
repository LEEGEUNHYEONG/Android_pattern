package com.example.paging.Db

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.paging.Model.Repo

@Dao
interface RepoDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts : List<Repo>)

    @Query("SELECT * FROM repos WHERE (name LIKE :queryString) OR (description LIKE " +
            ":queryString) ORDER BY stars DESC, name ASC")
    fun reposByName(queryString : String) : DataSource.Factory<Int, Repo>
}