package com.example.solid.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.solid.R
import com.example.solid.domain.usecase.LoginInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

//@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //    @Inject
    var loginInUseCase: LoginInUseCase = get<LoginInUseCase>()

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