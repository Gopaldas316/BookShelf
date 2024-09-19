package com.example.bookshelf.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.bookshelf.R
import com.example.bookshelf.models.UserEntity
import com.example.bookshelf.utils.validateCredentials
import com.example.marrowassignment.viewmodel.MyViewModel
import com.example.marrowassignment.viewmodel.MyViewModelFactory
import com.google.android.material.textfield.TextInputEditText

fun newLogInScreenActivityIntent(context: Context?): Intent {
    return Intent(context, LoginScreen::class.java)
}

class LoginScreen : AppCompatActivity() {

    private lateinit var viewModel : MyViewModel

    private lateinit var etUserName : EditText
    private lateinit var tietPasswordLogIn : TextInputEditText
    private lateinit var btLogin : Button
    private var isUser : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        viewModel = ViewModelProvider(this, MyViewModelFactory(this))[MyViewModel::class.java]

        etUserName = findViewById(R.id.etUserName)
        tietPasswordLogIn = findViewById(R.id.tietPasswordLogIn)
        btLogin = findViewById(R.id.btLogin)

        viewModel.isExistingUser.observe(this){
            isUser = it
            if(isUser){
                finish()
                startActivity(newSuccessActivityIntent(this))
            }else{
                Toast.makeText(this, "User doesn't exist with the given credentials", Toast.LENGTH_LONG).show()
            }
        }

        btLogin.setOnClickListener {
            val validation = validateUser()
            if(validation.first) {
                viewModel.login(getUserDetails())
            }else {
                Toast.makeText(this, validation.second, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateUser() : Pair<Boolean, String>{
        val user = getUserDetails()
        return validateCredentials(user.name, user.password, "Country")
    }

    private fun getUserDetails() : UserEntity {
        val userName = etUserName.text.toString()
        val password = tietPasswordLogIn.text.toString()

        return UserEntity(0, name = userName, password = password, "Country")
    }
}