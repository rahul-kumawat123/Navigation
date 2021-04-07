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
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        submit_btn.setOnClickListener {
            saveData()
        }
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