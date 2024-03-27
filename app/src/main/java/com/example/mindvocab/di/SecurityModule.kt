package com.example.mindvocab.di

import com.example.mindvocab.model.account.security.DefaultSecurityUtilsImpl
import com.example.mindvocab.model.account.security.SecurityUtils
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SecurityModule {

    @Binds
    @Singleton
    abstract fun bindSecurityUtils(
        securityUtils: DefaultSecurityUtilsImpl
    ) : SecurityUtils

}