package com.lgh.androidpattern.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.lgh.androidpattern.entity.Word
import com.lgh.androidpattern.repository.WordRepository

class WordViewModel(application: Application) : AndroidViewModel(application)
{
    var repository : WordRepository = WordRepository(application)

    internal var allWords : LiveData<List<Word>>

    init
    {
        allWords = repository.getAllWords()
    }

    fun insert(word:Word)
    {
        repository.insert(word)
    }

}