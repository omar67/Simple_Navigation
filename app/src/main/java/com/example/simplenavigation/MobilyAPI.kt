package com.example.simplenavigation

import android.util.Log
import org.jsoup.Jsoup

object MobilyAPI {


    /*
        This method is all about fetching the phones from Mobily site, Jsoup used here to extract the information
        from a selected website based on DOM element of the website (Web Scraping)
     */
    fun fetchPhones(lang: String = "en_us"): MutableList<Phone> {

        val langCode = if(lang.equals("en_us", true)) "en" else "ar"

        val phonesList = mutableListOf<Phone>()

        "https://shop.mobily.com.sa/product-category/smartphones-ar/page/2/"
        for (i in 1..5) {
            var url = if(langCode=="en") "https://shop.mobily.com.sa/product-category/smartphones/?lang=en" else "https://shop.mobily.com.sa/product-category/smartphones-ar/"
            if (i > 1)
                url = if(langCode=="en") "https://shop.mobily.com.sa/product-category/smartphones/page/$i/?lang=en" else "https://shop.mobily.com.sa/product-category/smartphones-ar/page/$i/"

            val doc = Jsoup.connect(url).get()
            val domElements = doc.getElementsByClass("product-h")

            domElements.forEach { element ->
                val data = element.children()[0]

                val model =
                    data.getElementsByClass("product-meta")[0].children()[0].text()
                val phoneUrl = data.attr("href")
                val phoneImg = data.children()[0].attr("src")

                val brand = getBrandName(model, lang)
                val description = "$brand, $model Phone"

                phonesList.add(Phone(brand, model, phoneImg, description, phoneUrl))
            }
        }
        return phonesList
    }

    //  return the brand name based on the model name
    private fun getBrandName(model: String, langCode: String): String {
        return if(langCode.equals("ar", true)) getBrandNamesAR(model) else getBrandNamesEN(model)
    }

    private fun getBrandNamesEN(model: String): String{
        var brand = "null"
        when {
            model.contains("iPhone", ignoreCase = true) -> brand = "Apple"
            model.contains("Samsung", ignoreCase = true) -> brand = "Samsung"
            model.contains("Huawei", ignoreCase = true) -> brand = "Huawei"
            model.contains("HTC", ignoreCase = true) -> brand = "HTC"
        }
        return brand
    }
    private fun getBrandNamesAR(model: String): String{
        var brand = "null"
        when {
            listOf("ايفون", "آيفون").any { it in model } -> brand = "آيفون"
            model.contains("سامسونج", ignoreCase = true) -> brand = "سامسونج"
            model.contains("هواوي", ignoreCase = true) -> brand = "هواوي"
            model.contains("اتش تي سي", ignoreCase = true) -> brand = "اتش تي سي"
        }
        return brand
    }
}