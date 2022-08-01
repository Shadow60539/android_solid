package com.example.solid.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.solid.R
import com.example.solid.domain.model.Post

class PostAdapter(private val posts: MutableList<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvBody: TextView = itemView.findViewById(R.id.tvBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        val post = posts[index]
        holder.tvTitle.text = post.title
        holder.tvBody.text = post.body
    }

    override fun getItemCount() = posts.size

    fun addPost(post: Post) {
        posts.add(0, post)
        notifyDataSetChanged()
    }

    fun addPosts(newPosts: List<Post>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }
}