package com.lgh.androidpattern.repository

import android.os.AsyncTask
import com.lgh.androidpattern.entity.Word
import com.lgh.androidpattern.entity.WordDao

class InsertAsyncTask(wordDao: WordDao) : AsyncTask<Word, Void, Void>()
{
    var asyncTaskDao : WordDao = wordDao

    override fun doInBackground(vararg params: Word?): Void?
    {
        asyncTaskDao.insert(params[0])

        return null
    }
}