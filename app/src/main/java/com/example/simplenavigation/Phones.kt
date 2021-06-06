package com.example.simplenavigation

class Phones (val phones: MutableList<Phone>){

    fun addPhone(phone: Phone){
        phones.add(phone)
    }
    fun removePhone(phone: Phone){
        phones.remove(phone)
    }


    override fun toString(): String {
        var st = ""
        phones.forEachIndexed{index, phone ->
            st+="$index- Brand: ${phone.brand}, Model: ${phone.model} \n${phone.image}\n"
        }
        return st

    }
    fun isEmpty(): Boolean{
        return phones.isEmpty()
    }
}