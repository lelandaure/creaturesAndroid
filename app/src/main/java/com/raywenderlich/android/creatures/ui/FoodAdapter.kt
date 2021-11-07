package com.raywenderlich.android.creatures.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.creatures.databinding.ListItemFoodBinding
import com.raywenderlich.android.creatures.model.Food

class FoodAdapter(private val foods: MutableList<Food>) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListItemFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(foods[position])

    fun updateFoods(foods: List<Food>) {
        this.foods.clear()
        this.foods.addAll(foods)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var food: Food

        fun bind(food: Food) {
            this.food = food
            val context = binding.root.context
            binding.foodImage.setImageResource(
                context.resources.getIdentifier(
                    food.thumbnailUtils,
                    null,
                    context.packageName
                )
            )
        }
    }
}