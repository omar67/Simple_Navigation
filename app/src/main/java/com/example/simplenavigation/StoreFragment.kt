package com.example.simplenavigation

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.simplenavigation.databinding.FragmentStoreBinding
import com.google.android.material.chip.Chip
import kotlin.math.abs

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

        bindData()
        handleOnSwipeRefresh()
        setupFilterChips()

        handleLoadingState()
        handlePhonesAdapter()

        return binding.root
    }


    //        display and update the phones when loaded
    private fun handlePhonesAdapter() {
        var adapter: RecyclerView.Adapter<PhonesAdapter.ViewHolder>?

        viewModel.phones.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter = PhonesAdapter(viewModel.phones.value!!, viewModel)
                binding.productsRV.adapter = adapter
                binding.productsRV.apply {
                    this.adapter = adapter
                    this.clipToPadding = false
                    this.clipChildren = false
                    this.offscreenPageLimit = 3
                }
                binding.productsRV.setPageTransformer(getCompositeTransformer())
                Log.d("debugg", "Phones is loaded to view holder")
            }
            Log.d("debugg", "Phones is not loaded to view holder")
        })
    }

    //    return a CompositePageTransformer that animate the entrance of phones
    private fun getCompositeTransformer(): CompositePageTransformer {
        val compositeTrans = CompositePageTransformer()
        compositeTrans.apply {
            this.addTransformer(MarginPageTransformer(20))
            this.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = (0.85f + r * 0.15f)
            }
        }
        return compositeTrans
    }

    //        display and update the loading progress bar + swipe refresh bar  on Store Fragment if needed
    private fun handleLoadingState() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.swipeRefreshLayout.isRefreshing = it
        })

    }

    private fun setupFilterChips() {
        handleGeneratingFilterChips()
        handleFilterChipsCurrentState()
    }

    private fun handleFilterChipsCurrentState() {
        viewModel.selectedFilter.observe(viewLifecycleOwner, { filter ->
            binding.chipGroup.children.forEach { chipView ->
                val temp = chipView as Chip
                if (temp.tag == filter)
                    temp.chipBackgroundColor = getColorState(true)
                else
                    temp.chipBackgroundColor = getColorState(false)
            }
        })
    }

    private fun handleGeneratingFilterChips() {
        viewModel.allPhones.observe(viewLifecycleOwner, { phones ->
            if (phones.isNotEmpty()) {
                val brands = mutableSetOf<String>()
                phones.map { phone -> if (phone.brand != "null") brands.add(phone.brand) }
                generateChips(brands)
            }
        })
    }

    private fun handleOnSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            Log.d("debugg", "refreshing phones ")
            viewModel.refreshPhones()
        }
    }

    private fun bindData() {
        binding.viewModel = viewModel
        binding.storeFragment = this
        binding.lifecycleOwner = this
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