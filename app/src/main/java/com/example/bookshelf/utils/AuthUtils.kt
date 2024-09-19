package com.example.bookshelf.utils

fun validateCredentials(userName : String, password : String, country : String) : Pair<Boolean, String> {
    var result = Pair(true, "")
    if (userName.isEmpty() || password.isEmpty() || country.isEmpty()) {
        result = Pair(false, "Please provide all the credentials ")
    } else if(password.length < 8) {
        result = Pair(false, "Password should be of minimum 8 characters")
    }else if(!hasSpecialChar(password)) {
        result = Pair(false, "Password should atleast one Special Character from !@#$%&()")
    }else if(!hasOneNumber(password)) {
        result = Pair(false, "Password should atleast contain one number")
    }else if(!hasAtleastOneLowerCase(password)){
        result = Pair(false, "Password should atleast contain one lower case letter")
    }else if(!hasAtleastOneUpperCase(password)){
        result = Pair(false, "Password should atleast contain one upper case letter")
    }
    return result
}

private fun hasSpecialChar(password: String) : Boolean {
    val set = setOf('!', '@', '#', '$', '%', '&', '(', ')')
    var hasSpecialChar = false
    for(ch in password){
        if(ch in set){
            hasSpecialChar = true
            break;
        }
    }
    return hasSpecialChar
}

private fun hasOneNumber(password: String) : Boolean {
    val set = setOf('0','1','2','3','4','5','6','7','8','9')
    var hasOneNumber = false
    for(ch in password) {
        if(ch in set) {
            hasOneNumber = true
            break;
        }
    }
    return hasOneNumber
}

private fun hasAtleastOneLowerCase(password: String): Boolean {
    var hasAtleastOneLower = false
    for(ch in password) {
        if(ch.isLowerCase()){
            hasAtleastOneLower = true
            break
        }
    }
    return hasAtleastOneLower
}

private fun hasAtleastOneUpperCase(password: String): Boolean {
    var hasAtleastOneUpper = false
    for(ch in password) {
        if(ch.isUpperCase()){
            hasAtleastOneUpper = true
            break
        }
    }
    return hasAtleastOneUpper
}