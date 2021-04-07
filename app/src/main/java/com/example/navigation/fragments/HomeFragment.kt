package com.example.navigation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.navigation.MainActivity
import com.example.navigation.R
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setListeners()
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(MainActivity.DESCRIPTION_KEY)
            ?.observe(viewLifecycleOwner , Observer {
                Log.i(MainActivity.DESCRIPTION_KEY,"Description is $it")
                addDescription_tv.text = it
            })
    }

    private fun setListeners() {
        openAccount_btn.setOnClickListener {
            navigateToAccountFragment()
        }

        addDescription_tv.setOnClickListener {
            checkTitleAndNavigate()
        }
    }

    private fun navigateToDialogDescriptionFragment(title: String) {

        val action = HomeFragmentDirections.actionHomeFragmentToDialogDescriptionFragment()
        findNavController().navigate(action)
    }

    private fun navigateToAccountFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_accountFragment)
    }

    private fun checkTitleAndNavigate() {
        if (title_et.text.isNullOrBlank()){
            title_et.error = "Please Enter Title"
        }
        else{
            val title = title_et.text.toString()
            navigateToDialogDescriptionFragment(title)
        }
    }
}