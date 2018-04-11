package com.lgh.androidpattern.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import com.lgh.androidpattern.database.WordRoomDatabase
import com.lgh.androidpattern.entity.Word
import com.lgh.androidpattern.entity.WordDao

class WordRepository (application: Application)
{
    var wordDao : WordDao
    var mAllWords : LiveData<List<Word>>

    init
    {
        val db : WordRoomDatabase = WordRoomDatabase.getDatabase(application)
        wordDao = db.wordDao()
        mAllWords = wordDao.getAllWords()
    }

    fun getAllWords() : LiveData<List<Word>>
    {
        return mAllWords
    }

    fun insert(word : Word)
    {
        InsertAsyncTask(wordDao).execute(word)
    }
}