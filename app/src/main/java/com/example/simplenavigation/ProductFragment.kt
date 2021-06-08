package com.example.simplenavigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.simplenavigation.databinding.FragmentProductBinding
import com.squareup.picasso.Picasso


class ProductFragment : Fragment() {

    private lateinit var currentPhone: Phone

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding =
            DataBindingUtil.inflate<FragmentProductBinding>(
                layoutInflater,
                R.layout.fragment_product,
                container,
                false
            )

        val viewModel: SharedViewModel by activityViewModels()
        currentPhone = viewModel.currentPhone

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        
        try {
            Picasso.with(this.context)
                .load(viewModel.currentPhone.image)
                .into(binding.productImage)
        } catch (e: Exception) {
            Log.d("debugg", "Couldn't load image ${currentPhone.model} img: ${currentPhone.image}")
        }

        binding.detailsBtn.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(currentPhone.url))
            startActivity(browserIntent)
        }

        return binding.root
    }


}