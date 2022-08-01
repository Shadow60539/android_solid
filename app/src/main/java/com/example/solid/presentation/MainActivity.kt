package com.example.solid.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solid.R
import com.example.solid.domain.failure.PostFailure
import com.example.solid.domain.model.Post
import com.example.solid.presentation.post_view_model.PostEvent
import com.example.solid.presentation.post_view_model.PostEventListener
import com.example.solid.presentation.post_view_model.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()
    private lateinit var rvPost: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUi()
        addViewModelEventListener()
        viewModel.add(PostEvent.GetPosts)
    }

    private fun initializeUi() {
        rvPost = findViewById(R.id.rvPosts)
        progressBar = findViewById(R.id.progressBar)

        postAdapter = PostAdapter(mutableListOf())
        rvPost.adapter = postAdapter
        rvPost.layoutManager = LinearLayoutManager(this@MainActivity)
    }


    private fun addViewModelEventListener() {
        lifecycleScope.launchWhenStarted {
            viewModel.listener.collect {
                when (it) {
                    is PostEventListener.OnGetPosts -> it.failureOrSuccess.fold(
                        ifLeft = { postFailure ->
                            Toast.makeText(
                                this@MainActivity, when (postFailure) {
                                    PostFailure.ServerFailure -> "Server Failure"
                                    PostFailure.CacheFailure -> "No Cache found"
                                    else -> null
                                }, Toast.LENGTH_SHORT
                            ).show()
                        },
                        ifRight = {
                            postAdapter.addPosts(viewModel.state.posts)
                        },
                    )
                    is PostEventListener.OnAddPost -> it.failureOrSuccess.fold(
                        ifLeft = { postFailure ->
                            Toast.makeText(
                                this@MainActivity, when (postFailure) {
                                    PostFailure.CreatePostFailure -> "Unable to create Post"
                                    PostFailure.ServerFailure -> "Server Failure"
                                    PostFailure.ClientFailure -> "No Internet"
                                    else -> null
                                }, Toast.LENGTH_SHORT
                            ).show()
                        },
                        ifRight = { post ->
                            postAdapter.addPost(post)
                        },
                    )
                }
                progressBar.visibility = View.GONE
            }
        }
    }

    fun addPost(view: View) {
        progressBar.visibility = View.VISIBLE
        val newPost =
            Post(
                id = 1,
                userId = 1,
                title = "New Post Title",
                body = viewModel.state.posts.size.toString()
            )
        viewModel.add(PostEvent.AddPost(newPost))
    }
}