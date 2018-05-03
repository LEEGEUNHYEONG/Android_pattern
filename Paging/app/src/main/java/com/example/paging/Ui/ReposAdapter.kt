package com.example.paging.Ui

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.paging.Model.Repo

class ReposAdapter : PagedListAdapter<Repo, RecyclerView.ViewHolder>(REPO_COMPARATOR)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return RepoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val repoItem = getItem(position)
        if(repoItem != null)
        {
            (holder as RepoViewHolder).bind(repoItem)
        }
    }

    companion object
    {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>()
        {
            override fun areItemsTheSame(oldItem: Repo?, newItem: Repo?): Boolean
            {
                return oldItem!!.fullName == newItem!!.fullName
            }

            override fun areContentsTheSame(oldItem: Repo?, newItem: Repo?): Boolean
            {
                return oldItem == newItem
            }
        }
    }
}