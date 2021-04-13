package com.example.navigation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation.MainActivity
import com.example.navigation.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.splash_fragment.*

class SplashFragment : Fragment() {

    lateinit var firebaseAnalytics: FirebaseAnalytics
    private val USER_TYPE = "user_type"
    private val EXISTING_USER = "existing_user"
    private val NO_USER = "no_user"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.splash_fragment,container,false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        //firebase Analytics
        firebaseAnalytics = Firebase.analytics
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        next_btn.setOnClickListener {
            //check login
            if(isUserLoggedIn()){
                setUserLoggedInProperty()
                navigateToHomeFragment()
            }else{
                setUserLoggedInProperty()
                navigateToLoginFragment()
            }
        }
        loginEvent()
    }

    private fun setUserLoggedInProperty() {
        if(isUserLoggedIn()){
            setDefaultProperty(getUserName())
            firebaseAnalytics.setUserProperty(USER_TYPE , EXISTING_USER)
        }else{
            setDefaultProperty(null)
            firebaseAnalytics.setUserProperty(USER_TYPE , NO_USER)
        }
    }

    private fun getUserName(): String {
        val sharedPreferences = activity?.getSharedPreferences(MainActivity.PREFERENCES,Context.MODE_PRIVATE)
        return sharedPreferences?.getString(MainActivity.NAME_KEY , "").toString()
    }

    private fun setDefaultProperty(userName: String?) {

        val bundle = Bundle().apply {
            firebaseAnalytics.setUserProperty("user_id",userName)
        }
        firebaseAnalytics.setDefaultEventParameters(bundle)
    }

    private fun loginEvent() {
        val eventName = "screen_is_open"
        val bundle = Bundle().apply{
            putString("screen_name", SplashFragment::class.java.simpleName)
        }
        firebaseAnalytics.logEvent(eventName,bundle)
    }

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }

    private fun navigateToLoginFragment() {
        findNavController().navigate(R.id.action_global_loginFragment)
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = activity?.getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE)
        val name = sharedPreferences?.getString(MainActivity.NAME_KEY, "")
        return name != ""

    }


}