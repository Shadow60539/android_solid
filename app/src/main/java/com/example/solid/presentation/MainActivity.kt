package com.example.solid.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.solid.R
import com.example.solid.domain.usecase.LoginInUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var loginInUseCase: LoginInUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onLoginPressed(view: View) {
        GlobalScope.launch(Dispatchers.Main) {
            val loggedInUser = loginInUseCase()
            //TODO: Handle Exception after learning ViewModels
            println(loggedInUser.name)
        }
    }
}