package com.example.paging.Ui

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.paging.Model.Repo
import com.example.paging.R

class RepoViewHolder(view : View) : RecyclerView.ViewHolder(view)
{
    private val name: TextView = view.findViewById(R.id.repo_name)
    private val description: TextView = view.findViewById(R.id.repo_description)
    private val stars: TextView = view.findViewById(R.id.repo_stars)
    private val language: TextView = view.findViewById(R.id.repo_language)
    private val forks: TextView = view.findViewById(R.id.repo_forks)

    private var repo: Repo? = null

    init
    {
        view.setOnClickListener {
            repo?.url?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(intent)
            }
        }
    }

    companion object
    {
        fun create(parnet : ViewGroup) : RepoViewHolder
        {
            val view = LayoutInflater.from(parnet.context)
                    .inflate(R.layout.repo_view_item, parnet, false)
            return RepoViewHolder(view)
        }
    }

    fun bind(repo: Repo?)
    {
        if (repo == null)
        {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
            language.visibility = View.GONE
            stars.text = resources.getString(R.string.unknown)
            forks.text = resources.getString(R.string.unknown)
        }
        else
        {
            showRepoData(repo)
        }
    }

    private fun showRepoData(repo:Repo)
    {
        this.repo = repo
        name.text = repo.fullName

        var descriptionVisibility = View.GONE

        if (repo.description != null)
        {
            description.text = repo.description
            descriptionVisibility = View.VISIBLE
        }

        description.visibility = descriptionVisibility
        stars.text = repo.stars.toString()
        forks.text = repo.forks.toString()

        var languageVisibility = View.GONE

        if (!repo.language.isNullOrEmpty())
        {
            val resources = this.itemView.context.resources
            language.text = resources.getString(R.string.language, repo.language)
            languageVisibility = View.VISIBLE
        }

        language.visibility = languageVisibility
    }

}
