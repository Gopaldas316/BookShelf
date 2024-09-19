package com.example.bookshelf.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.bookshelf.R
import com.example.bookshelf.models.UserEntity
import com.example.bookshelf.utils.validateCredentials
import com.example.marrowassignment.viewmodel.MyViewModel
import com.example.marrowassignment.viewmodel.MyViewModelFactory
import com.google.android.material.textfield.TextInputEditText

fun newSignUpScreenActivityIntent(context: Context?): Intent {
    return Intent(context, SignUpScreen::class.java)
}

class SignUpScreen : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var etUserNameSI : EditText
    private lateinit var tietPassword : TextInputEditText
    private lateinit var btSignIn : Button
    private lateinit var spinner : Spinner
    private var country : String = ""
    private lateinit var viewModel : MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)

        viewModel = ViewModelProvider(this, MyViewModelFactory(applicationContext))[MyViewModel::class.java]
        val countries = viewModel.getCountryList()

        etUserNameSI = findViewById(R.id.etUserNameSI)
        tietPassword = findViewById(R.id.tietPassword)
        btSignIn = findViewById(R.id.btSignIn)
        spinner = findViewById(R.id.spinner)

        btSignIn.setOnClickListener {
            val validation = validateUser()
            if(validation.first) {
                viewModel.insertUser(getUserDetails())
                finish()
                startActivity(newSuccessActivityIntent(this))
            }else {
                Toast.makeText(this, validation.second, Toast.LENGTH_LONG).show()
            }
        }

        val adapter : ArrayAdapter<String> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, countries)
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    private fun validateUser() : Pair<Boolean, String>{
        val user = getUserDetails()
        return validateCredentials(user.name, user.password, country)
    }

    private fun getUserDetails() : UserEntity {
        val userName = etUserNameSI.text.toString()
        val password = tietPassword.text.toString()

        return UserEntity(0, name = userName, password = password, country)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        country = parent?.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}