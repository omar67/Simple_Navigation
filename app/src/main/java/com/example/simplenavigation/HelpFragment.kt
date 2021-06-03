package com.example.simplenavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.simplenavigation.databinding.FragmentHelpBinding


class HelpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =
            DataBindingUtil.inflate<FragmentHelpBinding>(
                layoutInflater,
                R.layout.fragment_help,
                container,
                false
            )


        val viewModel: SharedViewModel by activityViewModels()
        binding.viewModel = viewModel

        binding.returnToStoreBtn.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_helpFragment_to_storeFragment)
        }


        return binding.root
    }


}
