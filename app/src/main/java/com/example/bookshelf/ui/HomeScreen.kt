package com.example.bookshelf.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.bookshelf.R
import com.example.marrowassignment.viewmodel.MyViewModel
import com.example.marrowassignment.viewmodel.MyViewModelFactory

fun newHomeScreenActivityIntent(context: Context?): Intent {
    return Intent(context, HomeScreen::class.java)
}

class HomeScreen : AppCompatActivity() {
    private lateinit var butSignUp : Button
    private lateinit var butLogIn : Button
    private lateinit var viewModel : MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, MyViewModelFactory(applicationContext))[MyViewModel::class.java]

        butSignUp = findViewById(R.id.butSignUp)
        butSignUp.setOnClickListener {
            startActivity(newSignUpScreenActivityIntent(this))
        }

        butLogIn = findViewById(R.id.butLogIn)
        butLogIn.setOnClickListener {
            startActivity(newLogInScreenActivityIntent(this))
        }
    }
}