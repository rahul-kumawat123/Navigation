package com.example.navigation.fragments

import android.content.Context
import android.os.Bundle
import android.view.Gravity.apply
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat.apply
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation.MainActivity
import com.example.navigation.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loginEvent()
        submit_btn.setOnClickListener {
            saveData()
        }
    }

    private fun loginEvent() {
        val eventName = "screen_is_open"
        val bundle = Bundle().apply{
            putString("screen_name", LoginFragment::class.java.simpleName)
        }
        firebaseAnalytics.logEvent(eventName,bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //firebase Analytics
        firebaseAnalytics = Firebase.analytics
    }

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun saveData() {
        if (name_et.text.isNullOrBlank()){
            name_et.error = "Please Enter a Name"
        }else{
            val name = name_et.text.toString()
            saveInSharedPref(name)
            navigateToHomeFragment()
        }
    }

    private fun saveInSharedPref(name: String) {
        val sharedPreferences = activity?.getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putString(MainActivity.NAME_KEY,name)
        editor?.apply()
    }

}