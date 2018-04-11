package com.lgh.androidpattern.database

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.*
import android.content.Context
import android.support.annotation.NonNull
import android.util.Log

import com.lgh.androidpattern.entity.Word
import com.lgh.androidpattern.entity.WordDao

@Database(entities = [(Word::class)], version = 1)

abstract class WordRoomDatabase : RoomDatabase()
{
    abstract fun wordDao(): WordDao

    companion object
    {
        var instance : WordRoomDatabase? = null

        internal fun getDatabase(context:Context) : WordRoomDatabase
        {
            if(instance == null)
            {
                synchronized(WordRoomDatabase::class.java)
                {
                    if(instance == null)
                    {
                        instance = Room.databaseBuilder(context.applicationContext, WordRoomDatabase::class.java, "word_database")
                                .fallbackToDestructiveMigration()
                                .addCallback(roomDatabaseCallback)
                                .build()
                    }
                }
            }
            return instance!!
        }

        object roomDatabaseCallback : RoomDatabase.Callback()
        {
            override fun onOpen(@NonNull db : SupportSQLiteDatabase)
            {
                super.onOpen(db)
                PopulateDbAsync(instance!!).execute()

            }
        }
    }
}