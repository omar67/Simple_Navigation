package com.example.simplenavigation


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PhonesAdapter(
    private val dataSet: MutableList<Phone>,
    private val viewModel: SharedViewModel
) :
    RecyclerView.Adapter<PhonesAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhonesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_list_products, parent, false)

        context = parent.context


        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: PhonesAdapter.ViewHolder, position: Int) {

//        holder.productListView.setImageResource(dataSet[position].image)
        if (dataSet[position].image.isEmpty())
            return
        try {
            Picasso.with(context)
                .load(dataSet[position].image)
                .into(holder.productListView)
        } catch (e: Error) {
            Log.d("debugg", "Error: at Phone adapter at Picasso Loading $e")
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productListView: ImageView

        init {
            productListView = itemView.findViewById(R.id.productListView)
            itemView.setOnClickListener {
                viewModel.currentPhone = dataSet[position]
                itemView.findNavController().navigate(R.id.action_storeFragment_to_productFragment)

            }
        }
    }

}
