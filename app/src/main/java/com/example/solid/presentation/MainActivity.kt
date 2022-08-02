package com.example.solid.presentation

import android.content.res.Configuration
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
import com.example.solid.presentation.post_view_model.PostEvent
import com.example.solid.presentation.post_view_model.PostState
import com.example.solid.presentation.post_view_model.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()
    private lateinit var rvPost: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var dialog: AddPostBottomSheetFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeUi()
        addViewModelObserver()
    }

    private fun initializeUi() {
        rvPost = findViewById(R.id.rvPosts)
        progressBar = findViewById(R.id.progressBar)
        dialog = AddPostBottomSheetFragment()

        postAdapter = PostAdapter(mutableListOf())
        rvPost.adapter = postAdapter
        rvPost.layoutManager = LinearLayoutManager(this@MainActivity)
        rvPost.visibility = View.INVISIBLE
    }


    private fun addViewModelObserver() {
        lifecycleScope.launch {
            viewModel.getPostState
                .collect {
                    when (it) {
                        is PostState.Error -> Toast.makeText(
                            this@MainActivity,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        is PostState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is PostState.Success -> {
                            postAdapter.addPost(it.data!!.toMutableList())
                            rvPost.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            rvPost.smoothScrollToPosition(0)
                        }
                        else -> {}
                    }
                }
        }
        lifecycleScope.launch {
            viewModel.addPostState.collect {
                when (it) {
                    is PostState.Error -> {
                        Toast.makeText(
                            this@MainActivity,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        progressBar.visibility = View.GONE
                    }
                    is PostState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is PostState.Success -> {
                        postAdapter.addPost(it.data!!)
                        rvPost.smoothScrollToPosition(0)
                        progressBar.visibility = View.GONE
                        dialog.closeDialog()
                    }
                    else -> {}
                }
            }
        }

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        viewModel.add(PostEvent.ConfigurationChange)
    }

    fun addPost(view: View) {
        dialog.show(supportFragmentManager, "AddPostBottomSheetDialog")
    }

}