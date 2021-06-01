package com.example.simplenavigation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.skydoves.colorpickerview.listeners.ColorListener


class OptionsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_options, container, false)
        val colorPickerView =
            v.findViewById<com.skydoves.colorpickerview.ColorPickerView>(R.id.colorPickerView)
        val changeAccentColor = v.findViewById<Button>(R.id.accentThemeBtn)
        val defaultThemeBtn = v.findViewById<Button>(R.id.defaultThemeBtn)
        val darkThemeBtn = v.findViewById<Button>(R.id.DarkThemeBtn)
        val lightThemeBtn = v.findViewById<Button>(R.id.LightThemeBtn)

        val viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val main = activity as MainActivity
        val actionBar = main.supportActionBar
        if (viewModel.themeColor != -1)
        {
            changeAccentColor.setBackgroundColor(viewModel.themeColor)
            defaultThemeBtn.setBackgroundColor(viewModel.themeColor)
            darkThemeBtn.setBackgroundColor(viewModel.themeColor)
            lightThemeBtn.setBackgroundColor(viewModel.themeColor)
            activity?.window?.statusBarColor = viewModel.themeColor
            activity?.window?.navigationBarColor = viewModel.themeColor


            val colorDrawable = ColorDrawable(viewModel.themeColor)
            actionBar?.setBackgroundDrawable(colorDrawable)

        }
        colorPickerView.setColorListener(ColorListener { color, fromUser ->
            if (color != -1) {
                viewModel.themeColor = color

                changeAccentColor.setBackgroundColor(color)
                defaultThemeBtn.setBackgroundColor(color)
                darkThemeBtn.setBackgroundColor(color)
                lightThemeBtn.setBackgroundColor(color)
                activity?.window?.statusBarColor = color
                activity?.window?.colorMode = color
                activity?.window?.navigationBarColor = color

                val colorDrawable = ColorDrawable(viewModel.themeColor)
                actionBar?.setBackgroundDrawable(colorDrawable)
            }
        })





        changeAccentColor.setOnClickListener {
            if (colorPickerView.visibility == View.VISIBLE) {
                colorPickerView.visibility = View.INVISIBLE
                changeAccentColor.text = "Change Accent Color"
                Toast.makeText(v.context, "Your theme has been saved", Toast.LENGTH_SHORT).show()
            } else {
                colorPickerView.visibility = View.VISIBLE
                changeAccentColor.text = "Save Accent Color"
            }
        }


        updateButtonStatus(defaultThemeBtn, darkThemeBtn, lightThemeBtn)

        defaultThemeBtn.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            updateButtonStatus(defaultThemeBtn, darkThemeBtn, lightThemeBtn)
        }
        darkThemeBtn.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            updateButtonStatus(defaultThemeBtn, darkThemeBtn, lightThemeBtn)
        }
        lightThemeBtn.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            updateButtonStatus(defaultThemeBtn, darkThemeBtn, lightThemeBtn)
        }


        return v
    }

    private fun updateButtonStatus(
        defaultThemeBtn: Button,
        darkThemeBtn: Button,
        lightThemeBtn: Button
    ) {
        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        when (currentNightMode) {
            AppCompatDelegate.MODE_NIGHT_YES -> {
                defaultThemeBtn.isEnabled = true
                darkThemeBtn.isEnabled = false
                lightThemeBtn.isEnabled = true
            } // Night mode is active, we're using dark theme
            AppCompatDelegate.MODE_NIGHT_NO -> {
                defaultThemeBtn.isEnabled = true
                darkThemeBtn.isEnabled = true
                lightThemeBtn.isEnabled = false
            } // Night mode is not active, we're using the light theme
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
                defaultThemeBtn.isEnabled = false
                darkThemeBtn.isEnabled = true
                lightThemeBtn.isEnabled = true
            } // Night mode is set to default
        }


    }
}