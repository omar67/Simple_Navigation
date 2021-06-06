package com.example.simplenavigation

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val list = mutableListOf<Phone>(
        Phone(
            "Samsung",
            "A52",
            "https://shop.mobily.com.sa/wp-content/uploads/2019/08/Huawei-5G.png",
            "Samsung A52 phone: 64GB",
            "https://www.mobily.com.sa/wps/portal/web/personal/mobile/phones-and-accessories/details/samsung-a52/"
        ),
        Phone(
            "Apple",
            "iPhone 12 mini",
            "https://shop.mobily.com.sa/wp-content/uploads/2019/08/Huawei-5G.png",
            "Iphone 12 mini phone: 128GB",
            "https://www.mobily.com.sa/wps/portal/web/personal/mobile/phones-and-accessories/details/iphone12-mini/"
        ),
        Phone(
            "Apple",
            "iPhone 12 Pro Max",
            "https://shop.mobily.com.sa/wp-content/uploads/2019/08/Huawei-5G.png",
            "Iphone 12 Pro Max phone: 254GB",
            "https://www.mobily.com.sa/wps/portal/web/personal/mobile/phones-and-accessories/details/iphone-12-pro-max/"
        )
    )

    private val sharedPref = application.getSharedPreferences("theme", MODE_PRIVATE)

    //    var phones: MutableLiveData<Phones> =  MutableLiveData(Phones(list))
    var phones: MutableLiveData<Phones> = MutableLiveData()
    lateinit var currentPhone: Phone
    val themeColor: MutableLiveData<Int> = MutableLiveData<Int>(-1)

    val themeMode: MutableLiveData<Int> = MutableLiveData<Int>(-1)
    val textColor: MutableLiveData<Int> = MutableLiveData<Int>(-1)

    val isLoading = MutableLiveData(true)

    init {
        val defaultColor = application.resources.getColor(R.color.purple_200)
        themeColor.value = sharedPref.getInt("color", defaultColor)
        themeMode.value = sharedPref.getInt("mode", -1)
        Log.d("debugg", "restore theme color:  ${themeColor.value}")
        Log.d("debugg", "restore theme mode:  ${themeMode.value}")
        retrievePhones()
    }

    fun getSelectedThemeBtn(mode: String): LiveData<Int> {
        val disabledColor = Transformations.map(themeColor) { color ->
            ColorUtils.blendARGB(themeColor.value!!, Color.BLACK, 0.4f)
        }

        if (themeMode.value == AppCompatDelegate.MODE_NIGHT_YES && mode == "dark")
            return disabledColor
        else if (themeMode.value == AppCompatDelegate.MODE_NIGHT_NO && mode == "light")
            return disabledColor
        else if (themeMode.value == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM && mode == "default")
            return disabledColor
        else
            return themeColor
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

    private fun isColorDark(color: Int): Boolean {
        val darkness: Double =
            1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
    }

    private fun retrievePhones() {
        GlobalScope.launch {
            isLoading.postValue(true)
            val res = MobilyAPI.fetchPhones()
            phones.postValue(res)
            isLoading.postValue(false)
        }
    }


}