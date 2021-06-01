package com.example.simplenavigation

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.simplenavigation.databinding.FragmentOptionsBinding
import com.skydoves.colorpickerview.listeners.ColorListener


class OptionsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentOptionsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_options, container, false)
        binding.lifecycleOwner = this
        val viewModel: SharedViewModel by activityViewModels()
        binding.themeColor = resources.getColor(R.color.purple_200)
        val v = binding.root

        val colorPickerView =
            v.findViewById<com.skydoves.colorpickerview.ColorPickerView>(R.id.colorPickerView)
        val changeAccentColor = v.findViewById<Button>(R.id.accentThemeBtn)
        val defaultThemeBtn = v.findViewById<Button>(R.id.defaultThemeBtn)
        val darkThemeBtn = v.findViewById<Button>(R.id.DarkThemeBtn)
        val lightThemeBtn = v.findViewById<Button>(R.id.LightThemeBtn)

        val sharedPref = activity?.getPreferences(MODE_PRIVATE) ?: return null

        viewModel.themeColor.observe(this, Observer { color ->
            if (color != -1) {
                with(sharedPref.edit()) {
                    putInt("color", color)
                    Log.d("debugg", "Saved to Prefs: $color")
                    apply()
                }
                binding.themeColor = color
            }
        })

        colorPickerView.setColorListener(ColorListener { color, fromUser ->
            if (color != -1) {
                viewModel.themeColor.postValue(color)
            }
        })

        changeAccentColor.setOnClickListener {
            if (colorPickerView.visibility == View.VISIBLE) {
                colorPickerView.visibility = View.INVISIBLE
                changeAccentColor.text = "Change Accent Color"
            } else {
                colorPickerView.visibility = View.VISIBLE
                changeAccentColor.text = "Save Accent Color"
            }
        }


        updateButtonStatus(defaultThemeBtn, darkThemeBtn, lightThemeBtn)

        defaultThemeBtn.setOnClickListener {
            updateAndSaveMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, sharedPref)
            updateButtonStatus(defaultThemeBtn, darkThemeBtn, lightThemeBtn)
        }
        darkThemeBtn.setOnClickListener {
            updateAndSaveMode(AppCompatDelegate.MODE_NIGHT_YES, sharedPref)
            updateButtonStatus(defaultThemeBtn, darkThemeBtn, lightThemeBtn)
        }
        lightThemeBtn.setOnClickListener {
            updateAndSaveMode(AppCompatDelegate.MODE_NIGHT_NO, sharedPref)
            updateButtonStatus(defaultThemeBtn, darkThemeBtn, lightThemeBtn)
        }


        return v
    }

    private fun updateAndSaveMode(mode: Int, sharedPref: SharedPreferences) {
        AppCompatDelegate.setDefaultNightMode(mode)
        with(sharedPref.edit()) {
            putInt("mode", mode)
            Log.d("debugg", "Saved to Prefs: $mode")
            apply()
        }
    }

    private fun updateButtonStatus(
        defaultThemeBtn: Button,
        darkThemeBtn: Button,
        lightThemeBtn: Button,
    ) {
        when (AppCompatDelegate.getDefaultNightMode()) {
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