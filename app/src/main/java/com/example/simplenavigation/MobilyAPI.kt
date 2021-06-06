package com.example.simplenavigation

import org.jsoup.Jsoup

class MobilyAPI {

    companion object {
        fun fetchPhones(): Phones {
            val phonesList = mutableListOf<Phone>()
            for (i in 1..5) {
                var url = "https://shop.mobily.com.sa/product-category/smartphones/?lang=en"
                if (i > 1)
                    url = "https://shop.mobily.com.sa/product-category/smartphones/page/$i/?lang=en"

                val doc = Jsoup.connect(url).get()
                val phoneList = doc.getElementsByClass("product-h")

                phoneList.forEach { element ->
                    var brand = ""
                    var description = ""
                    val model =
                        element.children()[0].getElementsByClass("product-meta")[0].children()[0].text()
                    val phoneUrl = element.children()[0].attr("href")
                    val phoneImg = element.children()[0].children()[0].attr("src")
                    brand = getBrandName(model)
                    description = "$brand, $model Phone"

                    phonesList.add(Phone(brand, model, phoneImg, description, phoneUrl))
                }
            }

            return Phones(phonesList)
        }


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
}