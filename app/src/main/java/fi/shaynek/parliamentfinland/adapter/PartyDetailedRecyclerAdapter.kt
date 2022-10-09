package fi.shaynek.parliamentfinland.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import fi.shaynek.parliamentfinland.data.models.PartyMemberItem
import fi.shaynek.parliamentfinland.R

/**
 * Defines members list for a particular party
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 05.10.2022
 */

class PartyDetailedRecyclerAdapter(private var dataSet: List<PartyMemberItem>) :
    RecyclerView.Adapter<PartyDetailedRecyclerAdapter.PartyMemberViewHolder>() {
    inner class PartyMemberViewHolder(private var v: View) : RecyclerView.ViewHolder(v) {
        val memberName:TextView = v.findViewById(R.id.party_member_name)
        val seatNumber:TextView = v.findViewById(R.id.party_members_seat_number)
        val constituency:TextView = v.findViewById(R.id.party_member_constituency)
        var image:ImageView = v.findViewById(R.id.party_member_img)
        val cardView : CardView = v.findViewById(R.id.party_member_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyMemberViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.party_member_card, parent, false)
        return PartyMemberViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PartyMemberViewHolder, position: Int) {
        holder.memberName.text = dataSet[position].name
        holder.seatNumber.text = "Seat: " + dataSet[position].seatNumber.toString()
        holder.constituency.text = "Constituency " + dataSet[position].constituency
        holder.image.load(dataSet[position].image)
        holder.cardView.setOnClickListener { dataSet[position].onClick.run() }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}