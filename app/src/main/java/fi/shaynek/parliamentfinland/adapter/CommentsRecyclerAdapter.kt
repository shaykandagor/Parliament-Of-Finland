package fi.shaynek.parliamentfinland.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.vstec.parliament2.R
import dev.vstec.parliament2.data.models.CommentItem
import fi.shaynek.parliamentfinland.R

class CommentsRecyclerAdapter(private val dataSet: List<CommentItem>): RecyclerView.Adapter<CommentsRecyclerAdapter.CommentCardViewHolder>() {
    inner class CommentCardViewHolder(private val v:View):RecyclerView.ViewHolder(v){
        val author:TextView = v.findViewById(R.id.comment_author)
        val commentContent:TextView = v.findViewById(R.id.comment_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentCardViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.comment_card, parent, false)
        return CommentCardViewHolder(v)
    }

    override fun onBindViewHolder(holder: CommentCardViewHolder, position: Int) {
        holder.author.text = dataSet[position].author
        holder.commentContent.text = dataSet[position].content
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}