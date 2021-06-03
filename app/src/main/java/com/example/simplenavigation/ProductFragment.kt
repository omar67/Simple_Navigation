package com.example.simplenavigation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.simplenavigation.databinding.FragmentProductBinding


class ProductFragment : Fragment() {

    private lateinit var currentPhone: Phone

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        binding.productImage.setImageResource(viewModel.currentPhone.image)

        binding.detailsBtn.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(currentPhone.url))
            startActivity(browserIntent)
        }

        return binding.root
    }


}