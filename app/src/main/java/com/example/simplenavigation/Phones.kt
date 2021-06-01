package com.example.simplenavigation

class Phones ( phones: MutableList<Phone>){

    val phones = phones

    fun addPhone(phone: Phone){
        phones.add(phone)
    }
    fun removePhone(phone: Phone){
        phones.remove(phone)
    }


    override fun toString(): String {
        var st = ""
        phones.forEachIndexed{index, phone ->
            st+="$index- Brand: ${phone.brand}, Model: ${phone.model} \n"
        }
        return st

    }
}