package com.example.simplenavigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//shorten the code, less code than HelpFragment!!!
class StoreFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val main = activity as MainActivity
        main.supportActionBar?.title = "Store"

    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_store, container, false)

        val needHelpBtn = v.findViewById<Button>(R.id.needHelpBtn)

        val viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        if (viewModel.themeColor != -1)
            needHelpBtn.setBackgroundColor(viewModel.themeColor)

        var layoutManager: RecyclerView.LayoutManager? = null
        var adapter: RecyclerView.Adapter<PhonesAdapter.ViewHolder>? = null
        val productsRV = v.findViewById<RecyclerView>(R.id.productsRV)

        layoutManager = LinearLayoutManager(this.context, LinearLayout.HORIZONTAL, false)
        productsRV.layoutManager = layoutManager
        adapter = PhonesAdapter(viewModel.phones.phones, viewModel)
        productsRV.adapter = adapter

        needHelpBtn.setOnClickListener {
            v.findNavController().navigate(R.id.action_storeFragment_to_helpFragment)
        }

        return v
    }

}