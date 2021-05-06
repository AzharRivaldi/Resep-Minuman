package com.azhar.drinkrecipe.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azhar.drinkrecipe.activities.RecipeDrinkActivity
import com.azhar.drinkrecipe.databinding.ListItemDrinkBinding
import com.azhar.drinkrecipe.model.ModelDrink
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Created by Azhar Rivaldi on 18-04-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * Linkedin : https://www.linkedin.com/in/azhar-rivaldi
 */

class DrinkAdapter(private val modelDrink: List<ModelDrink>, private val context: Context) :
        RecyclerView.Adapter<DrinkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            ListItemDrinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            val model = modelDrink[position]

            Glide.with(context)
                    .load(model.strDrinkThumb)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(holder.binding.imageDrink)

            holder.binding.tvNameDrink.text = model.strDrink

            root.setOnClickListener {
                val intent = Intent(context, RecipeDrinkActivity::class.java)
                intent.putExtra(RecipeDrinkActivity.DRINK_RECIPE, modelDrink[position])
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = modelDrink.size

    class ViewHolder(val binding: ListItemDrinkBinding) : RecyclerView.ViewHolder(binding.root)

}