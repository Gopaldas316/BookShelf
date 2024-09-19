package com.example.marrowassignment.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.db.UserDatabase
import com.example.bookshelf.models.SetOfObj
import com.example.bookshelf.models.UserEntity
import com.example.bookshelf.repository.RepositoryImpl
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class MyViewModel(val context : Context) : ViewModel() {
    private var countryList = emptyList<String>()

    private val booleanFlag = MutableLiveData<Boolean>()
    val isExistingUser : LiveData<Boolean>
        get() = booleanFlag

    private var repository : RepositoryImpl

    init{
        countryList = getCountryListFromJson()
        val database = UserDatabase.getDatabase(context = context)
        repository =  RepositoryImpl(database)
    }

    fun insertUser(user : UserEntity) {
        viewModelScope.launch {
            repository.insertUser(user = user)
        }
    }

    fun login(user : UserEntity){
        viewModelScope.launch {
            repository.isAnExistingUser(user).observe(context as LifecycleOwner, Observer {
                booleanFlag.postValue(it)
            })
        }
    }

    private fun getCountryListFromJson() : List<String> {
        val inputStream = context.assets.open("countries.json")
        val size : Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        val bookType = object : TypeToken<Map<String, SetOfObj>>() {}.type
        val result : Map<String, SetOfObj> = gson.fromJson(json, bookType)
        return convertToListOfStrings(result)
    }

    private fun convertToListOfStrings(data : Map<String, SetOfObj>) : List<String> {
        var myList = mutableListOf<String>()
        data.forEach { (s, setOfObj) ->
            myList.add(setOfObj.country)
        }
        return myList
    }

    fun getCountryList() : List<String> {
        return countryList
    }
}