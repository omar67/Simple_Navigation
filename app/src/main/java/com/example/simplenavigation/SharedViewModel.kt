package com.example.simplenavigation

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SharedViewModel : ViewModel() {

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

    var phones = Phones(list)
    var currentPhone: Phone = phones.phones[0]
    var themeColor: MutableLiveData<Int> = MutableLiveData<Int>(-1)

}