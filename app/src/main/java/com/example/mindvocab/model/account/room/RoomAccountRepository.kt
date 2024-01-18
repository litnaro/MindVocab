package com.example.mindvocab.model.account.room

import com.example.mindvocab.model.account.AccountRepository
import com.example.mindvocab.model.account.etities.Account
import com.example.mindvocab.model.account.etities.SignUpData

class RoomAccountRepository : AccountRepository {

    override fun signIn(email: String, password: String): String {
        TODO("Not yet implemented")
    }

    override fun signUp(signUpData: SignUpData) {
        TODO("Not yet implemented")
    }

    override fun getAccount(): Account {
        TODO("Not yet implemented")
    }

    override fun setUsername(username: String) {
        TODO("Not yet implemented")
    }

    override fun changePassword(password: String, repeatPassword: String) {
        TODO("Not yet implemented")
    }

    override fun setAccountPhoto(photo: String) {
        TODO("Not yet implemented")
    }

}