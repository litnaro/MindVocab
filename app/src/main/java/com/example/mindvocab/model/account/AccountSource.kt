package com.example.mindvocab.model.account

import com.example.mindvocab.model.account.etities.Account
import com.example.mindvocab.model.account.etities.SignUpData

interface AccountSource {

    fun signIn(email: String, password: String) : String

    fun signUp(signUpData: SignUpData)

    fun getAccount() : Account

    fun setUsername(username: String)

    fun changePassword(password: String, repeatPassword: String)

    fun setAccountPhoto(photo: String)

}