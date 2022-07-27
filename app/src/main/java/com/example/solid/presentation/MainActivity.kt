package com.example.solid.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.solid.R
import com.example.solid.domain.usecase.LoginInUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var loginInUseCase: LoginInUseCase
    lateinit var tvUserName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvUserName = findViewById(R.id.tvName)
    }

    fun onLoginPressed(view: View) {
        GlobalScope.launch(Dispatchers.IO) {
            val loggedInUser = loginInUseCase()
            //TODO: Handle Exception after learning ViewModels
            withContext(Dispatchers.Main) {
                tvUserName.text = loggedInUser.name
            }
        }
    }
}