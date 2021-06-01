package com.example.simplenavigation

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController


class HelpFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val main = activity as MainActivity
        main.supportActionBar?.title = "Help"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_help, container, false)
        val returnBtn = v.findViewById<Button>(R.id.returnToStoreBtn)
        val viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        if (viewModel.themeColor != -1)
            returnBtn.setBackgroundColor(viewModel.themeColor)

        returnBtn.setOnClickListener {
            v.findNavController().navigate(R.id.action_helpFragment_to_storeFragment)
        }


        return v
    }



    }