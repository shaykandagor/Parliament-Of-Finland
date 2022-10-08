package fi.shaynek.parliamentfinland.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fi.shaynek.parliamentfinland.data.models.PartyItem
import fi.shaynek.parliamentfinland.R

class PartiesRecyclerAdapter(private val dataSet: List<PartyItem>) :
    RecyclerView.Adapter<PartiesRecyclerAdapter.PartyCardViewHolder>() {
    inner class PartyCardViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        val partyName: TextView = v.findViewById(R.id.party_name)
        val partyHead: TextView = v.findViewById(R.id.party_head)
        private val partyCard: CardView = v.findViewById(R.id.party_card)
        val partyMembersCount: TextView = v.findViewById(R.id.party_members_count)
        val partyIcon: ImageView = v.findViewById(R.id.party_icon)

        init {
            partyCard.setOnClickListener {
                dataSet[adapterPosition].onClick.run()
                //Toast.makeText(it.context, "${dataSet[adapterPosition]}", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyCardViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.party_card, parent, false)
        return PartyCardViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PartyCardViewHolder, position: Int) {
        holder.partyName.text = dataSet[position].title
        holder.partyHead.text = dataSet[position].head
        holder.partyMembersCount.text = "Members: " + dataSet[position].count.toString()
        holder.partyIcon.setImageResource(dataSet[position].icon)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}