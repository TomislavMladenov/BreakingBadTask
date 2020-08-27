package com.breaking.bad.framework.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.breaking.bad.R
import com.breaking.bad.framework.datasource.model.Character
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.layout_character.view.*


class CharactersListAdapter(
    private val requestManager: RequestManager,
    private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: List<Character> = ArrayList()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                holder.bind(list[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, interaction, requestManager)
    }

    override fun getItemCount(): Int {
        return list.size ?: 0
    }

    fun setCharacters(list: List<Character>){
        this.list = list
        notifyDataSetChanged()

    }

    // Prepare the images that will be displayed in the RecyclerView.
    // This also ensures if the network connection is lost, they will be in the cache
    fun preloadGlideImages(
        requestManager: RequestManager,
        list: List<Character>
    ){
        for(character in list){
            requestManager
                .load(character.image)
                .preload()
        }
    }

    class ViewHolder private constructor(
        itemView: View,
        private val requestManager: RequestManager,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Character) = with(itemView) {
            itemView.tv_name.text = item.name
            itemView.mb_nickname.text = item.nickname
            itemView.tv_description.text = item.category

            requestManager
                .load(item.image)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemView.iv_image)

            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
        }

        companion object {

            fun from(parent: ViewGroup, interaction: Interaction?, requestManager: RequestManager): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.layout_character,
                        parent,
                        false
                    ),
                    requestManager,
                    interaction
                )
            }
        }
    }
}

interface Interaction {
    fun onItemSelected(position: Int, item: Character)
}

class TaskDiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}