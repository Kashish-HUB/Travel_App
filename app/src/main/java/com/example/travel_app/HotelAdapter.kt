package com.example.travel_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HotelAdapter(
    private var list: List<Hotel>,
    private val onViewDetails: (Hotel) -> Unit,
    private val onToggleWishlist: (Hotel) -> Unit,
    private val isInWishlist: (Hotel) -> Boolean
) : RecyclerView.Adapter<HotelAdapter.HotelViewHolder>() {

    inner class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgHotel: ImageView = itemView.findViewById(R.id.imgHotel)
        val tvHotelName: TextView = itemView.findViewById(R.id.tvHotelName)
        val tvCity: TextView = itemView.findViewById(R.id.tvCity)
        val tvRating: TextView = itemView.findViewById(R.id.tvRating)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val btnDetails: TextView = itemView.findViewById(R.id.btnViewDetails)
        val btnWishlist: TextView = itemView.findViewById(R.id.btnWishlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_hotel, parent, false)
        return HotelViewHolder(v)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val h = list[position]
        holder.imgHotel.setImageResource(h.imageRes)
        holder.tvHotelName.text = h.name
        holder.tvCity.text = h.city
        holder.tvRating.text = "⭐ ${h.rating}"
        holder.tvPrice.text = "₹ ${h.price} / night"

        holder.btnDetails.setOnClickListener { onViewDetails(h) }

        // Wishlist toggle
        val inList = isInWishlist(h)
        holder.btnWishlist.text = if (inList) "♥ Saved" else "♡ Save"
        holder.btnWishlist.setOnClickListener {
            onToggleWishlist(h)
            // immediate UI update
            val nowInList = isInWishlist(h)
            holder.btnWishlist.text = if (nowInList) "♥ Saved" else "♡ Save"
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<Hotel>) {
        list = newList
        notifyDataSetChanged()
    }
}
