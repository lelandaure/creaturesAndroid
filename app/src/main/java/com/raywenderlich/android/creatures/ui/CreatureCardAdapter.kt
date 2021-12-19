package com.raywenderlich.android.creatures.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.databinding.ListItemCreatureCardBinding
import com.raywenderlich.android.creatures.model.Creature

class CreatureCardAdapter(private val creatures: MutableList<Creature>) :
    RecyclerView.Adapter<CreatureCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemCreatureCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = creatures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }

    inner class ViewHolder(private val binding: ListItemCreatureCardBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var creature: Creature

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(creature: Creature) {
            this.creature = creature
            val context = binding.root.context
            val imageResource =
                context.resources.getIdentifier(creature.uri, null, context.packageName)
            binding.creatureImage.setImageResource(imageResource)
            binding.fullName.text = creature.fullName
            context.setBackgroundColors(imageResource)
            itemView.animateView()

        }

        override fun onClick(v: View?) {
            v?.let {
                val context = it.context
                val intent = CreatureActivity.newIntent(context, creature.id)
                context.startActivity(intent)
//                it.context.startActivity(CreatureActivity.newIntent(it.context, creature.id))
            }
        }

        private fun Context.setBackgroundColors(imageResource: Int) {
            val image = BitmapFactory.decodeResource(this.resources, imageResource)
            Palette.from(image).generate { palette ->
                palette?.let {
                    val backgroundColor =
                        it.getDominantColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    binding.creatureCardView.setBackgroundColor(backgroundColor)
                    binding.nameHolder.setBackgroundColor(backgroundColor)
                    val textColor = if (backgroundColor.isColorDark()) Color.WHITE else Color.BLACK
                    binding.fullName.setTextColor(textColor)
                }
            }
        }

        private fun Int.isColorDark(): Boolean {
            val darkness =
                1 - (0.299 * Color.red(this) +
                        0.587 * Color.green(this) +
                        0.114 * Color.blue(this)) / 255
            return darkness > 0.5
        }

        private fun View.animateView() {
            if (this.animation.equals(null)) {
                this.animation =  AnimationUtils.loadAnimation(this.context, R.anim.slide_from_bottom)
            }
        }

    }

}