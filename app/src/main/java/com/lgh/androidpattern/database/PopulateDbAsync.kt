package com.lgh.androidpattern.database

import android.os.AsyncTask
import com.lgh.androidpattern.entity.Word
import com.lgh.androidpattern.entity.WordDao

class PopulateDbAsync(db : WordRoomDatabase) : AsyncTask<Void, Void, Void>()
{
    var dao : WordDao = db.wordDao()

    override fun doInBackground(vararg params: Void?): Void?
    {
        dao.deleteAll()
        var word = Word("Hello")
        dao.insert(word)

        word = Word("World")
        dao.insert(word)

        return null
    }
}