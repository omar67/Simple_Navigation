package com.example.simplenavigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


class productFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_product, container, false)

        val currentPhone = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java).currentPhone
        val themeColor = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java).themeColor

        v.findViewById<TextView>(R.id.productTitle).text = currentPhone.model
        v.findViewById<ImageView>(R.id.productImage).setImageResource(currentPhone.image)
        v.findViewById<TextView>(R.id.productBrand).text = currentPhone.brand
        v.findViewById<TextView>(R.id.productModel).text = currentPhone.model
        v.findViewById<TextView>(R.id.productDescription).text = currentPhone.description

        val detailsBtn =v.findViewById<Button>(R.id.detailsBtn)
        if(themeColor!= -1)
            detailsBtn.setBackgroundColor(themeColor)
        detailsBtn.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(currentPhone.url))
            startActivity(browserIntent)

        }

        return v
    }
}