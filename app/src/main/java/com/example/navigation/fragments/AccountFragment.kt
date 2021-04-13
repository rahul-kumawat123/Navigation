package com.example.navigation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation.MainActivity
import com.example.navigation.R
import com.example.navigation.showToast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.account_fragment.*

class AccountFragment : Fragment() {

    lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.account_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkLogin()
        loginEvent()
        getSharedPref()

        logout_btn.setOnClickListener {
            removeSharedPrefData()
            navigateToLoginFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //firebase Analytics
        firebaseAnalytics = Firebase.analytics
    }

    private fun loginEvent() {
        val eventName = "screen_is_open"
        val bundle = Bundle().apply{
            putString("screen_name", HomeFragment::class.java.simpleName)
        }
        firebaseAnalytics.logEvent(eventName,bundle)
    }

    private fun removeSharedPrefData() {
        val sharedPreferences = activity?.getSharedPreferences(MainActivity.PREFERENCES , Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.remove(MainActivity.NAME_KEY)
        editor?.apply()
    }

    private fun checkLogin() {
        /*if (isUserLoggedIn()){
            val name = getSharedPref()
            showName_tv.text = "WELCOME!! "+ name.toString()
        }
        else{
            context?.showToast("Please Enter Name")
            navigateToLoginFragment()
        }*/

        try {
            val name = getSharedPref() ?: throw NullPointerException("No user logged in")
            showName_tv.text = "Welcome $name"
        }
        catch (e: Exception) {
            context?.showToast("Please Enter Name")
            FirebaseCrashlytics.getInstance().recordException(e)
            Log.i("Account Fragment", "Exception: $e")
            navigateToLoginFragment()
        }
    }

    private fun navigateToLoginFragment() {
        findNavController().navigate(R.id.action_global_loginFragment)
    }

    private fun getSharedPref(): String? {
        val sharedPreferences = activity?.getSharedPreferences(MainActivity.PREFERENCES , Context.MODE_PRIVATE)
        return sharedPreferences?.getString(MainActivity.NAME_KEY , null)
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = activity?.getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString(MainActivity.NAME_KEY, null)
        return name != null
    }
}