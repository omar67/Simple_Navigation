package com.example.simplenavigation


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class PhonesAdapter(private val dataSet: MutableList<Phone>, private val viewModel: SharedViewModel) :
    RecyclerView.Adapter<PhonesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhonesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_products, parent, false)



        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: PhonesAdapter.ViewHolder, position: Int) {
        holder.productListView.setImageResource(dataSet[position].image)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
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
