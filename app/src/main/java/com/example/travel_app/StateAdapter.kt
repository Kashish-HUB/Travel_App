package com.example.travel_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StateAdapter(
    private val states: List<State>,
    private val onClick: (State) -> Unit
) : RecyclerView.Adapter<StateAdapter.StateViewHolder>() {

    class StateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.stateImage)
        val name: TextView = itemView.findViewById(R.id.stateName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_state, parent, false)
        return StateViewHolder(view)
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        val state = states[position]
        holder.img.setImageResource(state.images[0])
        holder.name.text = state.name

        holder.itemView.setOnClickListener { onClick(state) }
    }

    override fun getItemCount() = states.size
}
