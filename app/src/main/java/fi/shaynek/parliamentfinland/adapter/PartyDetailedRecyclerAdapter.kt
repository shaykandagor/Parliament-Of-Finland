package fi.shaynek.parliamentfinland.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.vstec.parliament2.R
import dev.vstec.parliament2.data.models.PartyMemberItem
import fi.shaynek.parliamentfinland.R

class PartyDetailedRecyclerAdapter(private var dataSet: List<PartyMemberItem>) :
    RecyclerView.Adapter<PartyDetailedRecyclerAdapter.PartMemberViewHolder>() {
    inner class PartMemberViewHolder(private var v: View) : RecyclerView.ViewHolder(v) {
        val memberName:TextView = v.findViewById(R.id.party_member_name)
        val seatNumber:TextView = v.findViewById(R.id.party_members_seat_number)
        val constituency:TextView = v.findViewById(R.id.party_member_constituency)
        var image:ImageView = v.findViewById(R.id.party_member_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartMemberViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.party_member_card, parent, false)
        return PartMemberViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PartMemberViewHolder, position: Int) {
        holder.memberName.text = dataSet[position].name
        holder.seatNumber.text = "Seat: " + dataSet[position].seatNumber.toString()
        holder.constituency.text = "Constituency " + dataSet[position].constituency
        holder.image.setImageResource(R.drawable.ic_account)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}