package com.example.solid.presentation

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.solid.R
import com.example.solid.domain.model.Post
import com.example.solid.domain.usecase.CreatePost
import com.example.solid.domain.usecase.GetPosts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var getPostsUseCase: GetPosts

    @Inject
    lateinit var createPostUseCase: CreatePost
    private lateinit var rvPost: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvPost = findViewById(R.id.rvPosts)
        progressBar = findViewById(R.id.progressBar)
        getPosts()
    }

    private fun getPosts() {
        // TODO: Use viewModelScope after learning ViewModel
        GlobalScope.launch(Dispatchers.IO) {
            val posts = getPostsUseCase()
            //TODO: Handle Exception with Either<L,R>
            withContext(Dispatchers.Main) {
                postAdapter = PostAdapter(posts.toMutableList())
                rvPost.adapter = postAdapter
                rvPost.layoutManager = LinearLayoutManager(this@MainActivity)
                progressBar.visibility = View.GONE
            }
        }
    }

    fun addPost(view: View) {

        progressBar.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.IO) {
            val newPost =
                Post(id = 1, userId = 1, title = "New Post Title", body = "New Post body")
            val newPostFromApi = createPostUseCase(newPost)
            //TODO: Handle Exception with Either<L,R>
            withContext(Dispatchers.Main) {
                postAdapter.addPost(newPostFromApi)
                progressBar.visibility = View.GONE
            }
        }
    }
}