package com.raywenderlich.android.creatures.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.creatures.databinding.ListItemCreatureBinding
import com.raywenderlich.android.creatures.model.Creature

class CreatureAdapter(private val creatures: MutableList<Creature>) :
    RecyclerView.Adapter<CreatureAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListItemCreatureBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var creature: Creature

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(creature: Creature) {
            this.creature = creature
            val context = binding.root.context
            binding.creatureImage.setImageResource(
                context.resources.getIdentifier(creature.uri, null, context.packageName)
            )
            binding.fullName.text = creature.fullName
            binding.nickName.text = creature.nickname
        }

        override fun onClick(v: View?) {
            v?.let {
                val context = it.context
                val intent = CreatureActivity.newIntent(context, creature.id)
                context.startActivity(intent)
//                it.context.startActivity(CreatureActivity.newIntent(it.context, creature.id))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemCreatureBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = creatures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }

    fun updateCreatures(creatures: List<Creature>) {
        this.creatures.clear()
        this.creatures.addAll(creatures)
        notifyDataSetChanged()
    }

}