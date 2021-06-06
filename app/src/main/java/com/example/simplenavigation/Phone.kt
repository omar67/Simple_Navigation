package com.example.simplenavigation

class Phone(val brand: String, val model: String, val image: String, val description: String,
            val url: String
){

    override fun toString(): String {
        return "$brand $model \n$description"
    }


}