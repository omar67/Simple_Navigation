package com.example.simplenavigation

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val list = mutableListOf<Phone>(
        Phone(
            "Samsung",
            "A52",
            R.drawable.pixel,
            "Samsung A52 phone: 64GB",
            "https://www.mobily.com.sa/wps/portal/web/personal/mobile/phones-and-accessories/details/samsung-a52/"
        ),
        Phone(
            "Apple",
            "iPhone 12 mini",
            R.drawable.iphone12,
            "Iphone 12 mini phone: 128GB",
            "https://www.mobily.com.sa/wps/portal/web/personal/mobile/phones-and-accessories/details/iphone12-mini/"
        ),
        Phone(
            "Apple",
            "iPhone 12 Pro Max",
            R.drawable.iphone12pro,
            "Iphone 12 Pro Max phone: 254GB",
            "https://www.mobily.com.sa/wps/portal/web/personal/mobile/phones-and-accessories/details/iphone-12-pro-max/"
        )
    )
    private val sharedPref = application.getSharedPreferences("theme", MODE_PRIVATE)
    var phones = Phones(list)
    var currentPhone: Phone = phones.phones[0]
    var themeColor: MutableLiveData<Int> = MutableLiveData<Int>(-1)
    var themeMode: MutableLiveData<Int> = MutableLiveData<Int>(-1)

    init {
        val defaultColor = application.resources.getColor(R.color.purple_200)
        themeColor.value = sharedPref.getInt("color", defaultColor)
        themeMode.value = sharedPref.getInt("mode", -1)
        Log.d("debugg", "restore theme color:  ${themeColor.value}")
        Log.d("debugg", "restore theme mode:  ${themeMode.value}")
    }

    // save the theme color to shared preferences
    fun saveThemeColor(color: Int) {
        themeColor.value = color
        with(sharedPref.edit()) {
            putInt("color", color)
            Log.d("debugg", "Saved theme color to Prefs: $color")
            apply()
        }
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


}