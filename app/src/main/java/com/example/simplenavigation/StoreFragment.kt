package com.example.simplenavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenavigation.databinding.FragmentStoreBinding

//shorten the code, less code than HelpFragment!!!
class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_store,
                container,
                false
            )

        val viewModel: SharedViewModel by activityViewModels()
        binding.viewModel = viewModel
        binding.storeFragment = this
        binding.lifecycleOwner = this

        var layoutManager: RecyclerView.LayoutManager? = null
        var adapter: RecyclerView.Adapter<PhonesAdapter.ViewHolder>? = null

        layoutManager = LinearLayoutManager(this.context, LinearLayout.HORIZONTAL, false)
        binding.productsRV.layoutManager = layoutManager
        adapter = PhonesAdapter(viewModel.phones.phones, viewModel)
        binding.productsRV.adapter = adapter

        return binding.root
    }

    fun navigateToHelp() {
        binding.root.findNavController().navigate(R.id.action_storeFragment_to_helpFragment)
    }

}