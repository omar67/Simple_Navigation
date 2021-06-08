package com.example.simplenavigation

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenavigation.databinding.FragmentStoreBinding
import com.google.android.material.chip.Chip

//shorten the code, less code than HelpFragment!!!
class StoreFragment : Fragment() {

    private lateinit var binding: FragmentStoreBinding
    val viewModel: SharedViewModel by activityViewModels()

    @SuppressLint("WrongConstant")
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


        binding.viewModel = viewModel
        binding.storeFragment = this
        binding.lifecycleOwner = this

        viewModel.allPhones.observe(viewLifecycleOwner, { phones ->
            if (phones.isNotEmpty()) {
                val brands = mutableSetOf<String>()
                phones.map { phone -> if (phone.brand != "null") brands.add(phone.brand) }

                generateChips(brands)
            }
        })

        viewModel.selectedFilter.observe(viewLifecycleOwner, { filter ->

            binding.chipGroup.children.forEach { chipView ->
                val temp = chipView as Chip
                if (temp.tag == filter)
                    temp.chipBackgroundColor = getColorState(true)
                else
                    temp.chipBackgroundColor = getColorState(false)
            }
        })


        val layoutManager: RecyclerView.LayoutManager?
        var adapter: RecyclerView.Adapter<PhonesAdapter.ViewHolder>?

        layoutManager = LinearLayoutManager(this.context, LinearLayout.HORIZONTAL, false)
        binding.productsRV.layoutManager = layoutManager

//        display and update the loading progress bar on Store Fragment if needed
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        })

//        display and update the phones when loaded
        viewModel.phones.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter = PhonesAdapter(viewModel.phones.value!!, viewModel)
                binding.productsRV.adapter = adapter
                Log.d("debugg", "loaded to recycler view")
            }
            Log.d("debugg", "not loaded to recycler view")
        })

        return binding.root
    }

    //    generate chips and display it in UI
    private fun generateChips(brands: MutableSet<String>) {
        binding.chipGroup.removeAllViews()


        val chip = Chip(this.context)
        chip.text = "All"
        chip.tag = "All"
        chip.chipBackgroundColor = getColorState(false)
        chip.setOnClickListener { filterPhones(chip) }

        binding.chipGroup.addView(chip)
        val sortedBrands = brands.sorted()
        for (brand in sortedBrands) {
            val tempChip = Chip(this.context)

            tempChip.text = brand
            tempChip.tag = brand
            tempChip.chipBackgroundColor = getColorState(false)
            tempChip.setOnClickListener { filterPhones(tempChip) }
            binding.chipGroup.addView(tempChip)
        }


    }

    private fun filterPhones(chip: Chip) {
        viewModel.filterPhones(chip.text.toString())
    }

    private fun getColorState(selected: Boolean): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_pressed)
        )

        val disabledColors = intArrayOf(
            Color.GRAY,
            Color.RED,
            Color.GREEN,
            Color.BLUE
        )

        val selectedColors = intArrayOf(
            viewModel.themeColor.value!!,
            Color.RED,
            Color.GREEN,
            Color.BLUE
        )
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_YES -> disabledColors[0] = Color.DKGRAY
            AppCompatDelegate.MODE_NIGHT_NO -> disabledColors[0] = Color.LTGRAY
        }

        return if (selected)
            ColorStateList(states, selectedColors)
        else
            ColorStateList(states, disabledColors)
    }

    fun navigateToHelp() {
        binding.root.findNavController().navigate(R.id.action_storeFragment_to_helpFragment)
    }

}