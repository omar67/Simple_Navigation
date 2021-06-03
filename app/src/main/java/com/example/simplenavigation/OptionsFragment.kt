package com.example.simplenavigation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.ColorUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.simplenavigation.databinding.FragmentOptionsBinding
import com.skydoves.colorpickerview.listeners.ColorListener


class OptionsFragment : Fragment() {

    private lateinit var binding: FragmentOptionsBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_options, container, false)

        binding.optionsFragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // color wheel listener, listens for any change in the color
        binding.colorPickerView.setColorListener(ColorListener { color, fromUser ->
            if (color != -1 && color != viewModel.themeColor.value)
                viewModel.saveThemeColor(color)
        })


//        update the fragment view for preferred theme
        updateButtonState()
        return binding.root
    }

    //    this method will change the state of changeAccent button
    fun changeAccentColor() {
        val colorPickerView = binding.colorPickerView
        val changeAccentColorBtn = binding.accentThemeBtn

        if (colorPickerView.visibility == View.VISIBLE) {
            colorPickerView.visibility = View.INVISIBLE
            changeAccentColorBtn.text = "Change Accent Color"
        } else {
            colorPickerView.visibility = View.VISIBLE
            changeAccentColorBtn.text = "Save Accent Color"
        }
    }

    //    this method gets called when Light,Dark,Default buttons gets clicked
    fun changeDisplayMode(btn: String) {
        when (btn) {
            "Light" -> updateAndSaveMode(AppCompatDelegate.MODE_NIGHT_NO)
            "Dark" -> updateAndSaveMode(AppCompatDelegate.MODE_NIGHT_YES)
            "Default" -> updateAndSaveMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
        updateButtonState()
    }

    // this method will save the current mode and main activity will observer changes and update
    private fun updateAndSaveMode(mode: Int) {
        viewModel.saveThemeMode(mode)
    }

    //    this method is just to update buttons state
    private fun updateButtonState() {
        val defaultThemeBtn = binding.defaultThemeBtn
        val darkThemeBtn = binding.DarkThemeBtn
        val lightThemeBtn = binding.LightThemeBtn

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