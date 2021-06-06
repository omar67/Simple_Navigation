package com.example.simplenavigation

import android.os.Bundle
import android.util.Log
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
    ): View {
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


        val layoutManager: RecyclerView.LayoutManager?
        var adapter: RecyclerView.Adapter<PhonesAdapter.ViewHolder>?

        layoutManager = LinearLayoutManager(this.context, LinearLayout.HORIZONTAL, false)
        binding.productsRV.layoutManager = layoutManager

//        display and update the loading progress bar on Store Fragment if needed
        viewModel.isLoading.observe(this, {
            if (it)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        })

//        display and update the phones when loaded
        viewModel.phones.observe(this, {
            adapter = PhonesAdapter(viewModel.phones.value!!, viewModel)
            binding.productsRV.adapter = adapter
            Log.d("debugg", "loaded")
        })

        return binding.root
    }

    fun navigateToHelp() {
        binding.root.findNavController().navigate(R.id.action_storeFragment_to_helpFragment)
    }

}