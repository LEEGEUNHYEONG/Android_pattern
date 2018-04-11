package com.lgh.androidpattern.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "word_table")
open class Word(word : String)
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    val word : String = word
}