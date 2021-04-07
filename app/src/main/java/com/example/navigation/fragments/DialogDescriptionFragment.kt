@file:Suppress("DEPRECATION")

package com.example.navigation.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.navArgs
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.navigation.MainActivity
import com.example.navigation.R
import com.example.navigation.showToast
import kotlinx.android.synthetic.main.dialog_description.*
import org.w3c.dom.Text

class DialogDescriptionFragment : androidx.fragment.app.DialogFragment() {

    val args : DialogDescriptionFragmentArgs by navArgs()
    lateinit var descriptionEditText: EditText
    lateinit var navController: NavController

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        navController = findNavController()
        val title = args.dialogFragmentTitle
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_description,null)
        descriptionEditText = dialogLayout.findViewById(R.id.description_et)

        return builder
            .setView(dialogLayout)
            .setTitle(title)
            .setCancelable(false).setPositiveButton("Submit"){dialog ,which -> getDescriptionFromDialog() }
            .create()

    }

    private fun getDescriptionFromDialog() {
        if(descriptionEditText.text.toString() == ""){
            navController.previousBackStackEntry?.savedStateHandle?.set(MainActivity.DESCRIPTION_KEY , "PLEASE ENTER DESCRIPTION")
            context?.showToast("Please Enter Description")
        }else{
            Log.i(MainActivity.DESCRIPTION_KEY, "DESCRIPTION IS ${descriptionEditText.text}")
            navController.previousBackStackEntry?.savedStateHandle?.set(MainActivity.DESCRIPTION_KEY , descriptionEditText.text.toString())
        }
    }
}