package com.example.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object{
        const val PREFERENCES = "login_shared_pref"
        const val NAME_KEY = "User_Name"
        const val DESCRIPTION_KEY = "description_text"
    }
}