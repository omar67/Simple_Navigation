package com.example.simplenavigation

import org.jsoup.Jsoup

object MobilyAPI {


    /*
        This method is all about fetching the phones from Mobily site, Jsoup used here to extract the information
        from a selected website based on DOM element of the website (Web Scraping)
     */
    fun fetchPhones(): MutableList<Phone> {
        val phonesList = mutableListOf<Phone>()

        for (i in 1..5) {
            var url = "https://shop.mobily.com.sa/product-category/smartphones/?lang=en"
            if (i > 1)
                url = "https://shop.mobily.com.sa/product-category/smartphones/page/$i/?lang=en"

            val doc = Jsoup.connect(url).get()
            val domElements = doc.getElementsByClass("product-h")

            domElements.forEach { element ->
                val data = element.children()[0]

                val model =
                    data.getElementsByClass("product-meta")[0].children()[0].text()
                val phoneUrl = data.attr("href")
                val phoneImg = data.children()[0].attr("src")

                val brand = getBrandName(model)
                val description = "$brand, $model Phone"

                phonesList.add(Phone(brand, model, phoneImg, description, phoneUrl))
            }
        }
        return phonesList
    }


    //  return the brand name based on the model name
    private fun getBrandName(model: String): String {
        var brand = "null"
        when {
            model.contains("iPhone", ignoreCase = true) -> brand = "Apple"
            model.contains("Samsung", ignoreCase = true) -> brand = "Samsung"
            model.contains("Huawei", ignoreCase = true) -> brand = "Huawei"
            model.contains("HTC", ignoreCase = true) -> brand = "HTC"
        }
        return brand
    }
}