package com.example.monsterhunterarmor

import android.app.Application
import com.example.monsterhunterarmor.di.AppContainer

class ArmorApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}