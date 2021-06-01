package com.example.simplenavigation

class Phone(brand: String, model: String, image: Int, description: String, url: String){


    val brand = brand
    val model = model
    val image = image
    val description = description
    val url = url



    override fun toString(): String {
        return "$brand $model \n$description"
    }


}