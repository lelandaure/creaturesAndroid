package com.raywenderlich.android.creatures.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.app.inflate
import com.raywenderlich.android.creatures.model.Creature
import com.raywenderlich.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.list_item_creature.view.creatureImage
import kotlinx.android.synthetic.main.list_item_creature_with_food.view.*

class CreatureWithFoodAdapter(private val creatures: MutableList<Creature>) :
    RecyclerView.Adapter<CreatureWithFoodAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private lateinit var creature: Creature
        private val foodAdapter = FoodAdapter(mutableListOf())

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(creature: Creature) {
            this.creature = creature
            val context = itemView.context
            itemView.creatureImage.setImageResource(
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
            itemView.foodRecyclerView.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            itemView.foodRecyclerView.adapter = foodAdapter

            val foods = CreatureStore.getCreatureFoods(creature)
            foodAdapter.updateFoods(foods)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(parent.inflate(R.layout.list_item_creature_with_food))
        viewHolder.itemView.foodRecyclerView.setRecycledViewPool(viewPool)
        LinearSnapHelper().attachToRecyclerView(viewHolder.itemView.foodRecyclerView)
        return viewHolder
    }

    override fun getItemCount() = creatures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }

}