package com.example.travel_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FlightAdapter(
    private val items: List<Flight>,
    private val listener: (Flight) -> Unit
) : RecyclerView.Adapter<FlightAdapter.FlightVH>() {

    inner class FlightVH(view: View) : RecyclerView.ViewHolder(view) {
        val tvAirline: TextView = view.findViewById(R.id.tvAirline)
        val tvRoute: TextView = view.findViewById(R.id.tvRoute)
        val tvTimes: TextView = view.findViewById(R.id.tvTimes)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val btnBook: Button = view.findViewById(R.id.btnBookNow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightVH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_flight, parent, false)
        return FlightVH(v)
    }

    override fun onBindViewHolder(holder: FlightVH, position: Int) {
        val f = items[position]

        holder.tvAirline.text = f.airline
        holder.tvRoute.text = "${f.fromCity} → ${f.toCity}"
        holder.tvTimes.text = "${f.departTime} - ${f.arriveTime} · ${f.duration}"
        holder.tvPrice.text = "₹ ${f.price}"

        // When user clicks Book Now → send Flight object to Activity
        holder.btnBook.setOnClickListener {
            listener(f)
        }
    }

    override fun getItemCount(): Int = items.size
}
