package com.lgh.androidpattern.ViewModel

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lgh.androidpattern.R
import com.lgh.androidpattern.entity.Word

class WordListAdapter constructor(context : Context) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>()
{
    inner class WordViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        var wordItemView : TextView = itemView.findViewById(R.id.textView)

    }

    var context : Context = context
    var inflater : LayoutInflater = LayoutInflater.from(context)
    var mWords : List<Word>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder
    {
        var itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int)
    {
        if(mWords != null)
        {
            val current = mWords!!.get(position)
            holder.wordItemView.text = current.word
        }
        else
        {
            holder.wordItemView.text = "No Word"
        }
    }

    fun setWords(words: List<Word>?)
    {
        mWords = words
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int
    {
        if(mWords != null)
        {
            return mWords!!.size
        }
        return 0
    }
}