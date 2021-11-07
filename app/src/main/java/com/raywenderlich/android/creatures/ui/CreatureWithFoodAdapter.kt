package com.raywenderlich.android.creatures.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.creatures.databinding.ListItemCreatureWithFoodBinding
import com.raywenderlich.android.creatures.model.Creature
import com.raywenderlich.android.creatures.model.CreatureStore

class CreatureWithFoodAdapter(private val creatures: MutableList<Creature>) :
    RecyclerView.Adapter<CreatureWithFoodAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    inner class ViewHolder(private val binding: ListItemCreatureWithFoodBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        private lateinit var creature: Creature
        private val foodAdapter = FoodAdapter(mutableListOf())

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(creature: Creature) {
            this.creature = creature
            val context = binding.root.context
            binding.creatureImage.setImageResource(
                context.resources.getIdentifier(creature.uri, null, context.packageName)
            )
            setupFoods()
        }

        override fun onClick(v: View?) {
            v?.let {
                it.context.startActivity(CreatureActivity.newIntent(it.context, creature.id))
            }
        }

        private fun setupFoods() {
            binding.foodRecyclerView.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.foodRecyclerView.adapter = foodAdapter

            val foods = CreatureStore.getCreatureFoods(creature)
            foodAdapter.updateFoods(foods)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemCreatureWithFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        binding.foodRecyclerView.setRecycledViewPool(viewPool)
        LinearSnapHelper().attachToRecyclerView(binding.foodRecyclerView)

        return ViewHolder(binding)
    }

    override fun getItemCount() = creatures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }

}