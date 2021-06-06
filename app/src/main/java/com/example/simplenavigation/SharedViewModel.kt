package com.example.simplenavigation

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPref = application.getSharedPreferences("theme", MODE_PRIVATE)

    //    var phones: MutableLiveData<Phones> =  MutableLiveData(Phones(list))
    var phones: MutableLiveData<MutableList<Phone>> = MutableLiveData()
    lateinit var currentPhone: Phone
    val themeColor: MutableLiveData<Int> = MutableLiveData<Int>(-1)

    val themeMode: MutableLiveData<Int> = MutableLiveData<Int>(-1)
    val textColor: MutableLiveData<Int> = MutableLiveData<Int>(-1)

//    is loading from fetching data from MobilyAPI
    val isLoading = MutableLiveData(true)


    init {
        val defaultColor = application.resources.getColor(R.color.purple_200)
        themeColor.value = sharedPref.getInt("color", defaultColor)
        themeMode.value = sharedPref.getInt("mode", -1)
        Log.d("debugg", "restore theme color:  ${themeColor.value}")
        Log.d("debugg", "restore theme mode:  ${themeMode.value}")
        retrievePhones()
    }

//    return a grayed out color if the button is selected
    fun getSelectedThemeBtn(mode: String): LiveData<Int> {
        val disabledColor = Transformations.map(themeColor) {
            ColorUtils.blendARGB(themeColor.value!!, Color.BLACK, 0.4f)
        }
        return when {
            themeMode.value == AppCompatDelegate.MODE_NIGHT_YES && mode == "dark" -> disabledColor
            themeMode.value == AppCompatDelegate.MODE_NIGHT_NO && mode == "light" -> disabledColor
            themeMode.value == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM && mode == "default" -> disabledColor
            else -> themeColor
        }
    }

    // save the theme color to shared preferences
    fun saveThemeColor(color: Int) {
        themeColor.value = color
        updateTextColor(color)
        with(sharedPref.edit()) {
            putInt("color", color)
            Log.d("debugg", "Saved theme color to Prefs: $color")
            apply()
        }
    }

    //    update the UI text style color
    private fun updateTextColor(color: Int) {
        if (isColorDark(color))
            textColor.value = Color.WHITE
        else
            textColor.value = Color.BLACK
    }

    // save the theme display mode to shared preferences
    fun saveThemeMode(mode: Int) {
        if (mode != themeMode.value) {
            themeMode.value = mode
            with(sharedPref.edit()) {
                putInt("mode", mode)
                Log.d("debugg", "Saved theme mode to Prefs: $mode")
                apply()
            }
        }
    }

    // return true if the selected color is dark as the name describe!
    private fun isColorDark(color: Int): Boolean {
        val darkness: Double =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
    }

    //    retrieve the phones from the MobilyAPI
    private fun retrievePhones() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.postValue(true)
            phones.postValue(MobilyAPI.fetchPhones())
            isLoading.postValue(false)
        }
    }

    fun selectPhone(phone: Phone) {
        currentPhone = phone
    }


}