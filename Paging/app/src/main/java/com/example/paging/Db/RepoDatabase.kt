package com.example.paging.Db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.paging.Model.Repo

@Database(entities = [Repo::class], version = 1, exportSchema = false)
abstract class RepoDatabase : RoomDatabase()
{
    abstract fun reposeDao() : RepoDao

    companion object
    {
        @Volatile
        private var instance : RepoDatabase? = null

        private val LOCK = Any()

        /*
        fun getInstance(context: Context): RepoDatabase =
                instance ?: synchronized(this) {
                    instance ?: buildDatabase(context).also { instance = it }
                } as RepoDatabase
                */

        fun getInstance(context : Context) : RepoDatabase
        {
            if(instance == null)
            {
                synchronized(LOCK)
                {
                    if(instance == null)
                    {
                        instance = Room.databaseBuilder(context.applicationContext, RepoDatabase::class.java, "Github.db")
                                .build()
                    }
                }
            }
            return instance!!
        }
    }
}