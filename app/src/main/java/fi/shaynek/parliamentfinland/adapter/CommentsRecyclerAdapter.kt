package fi.shaynek.parliamentfinland.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fi.shaynek.parliamentfinland.R
import fi.shaynek.parliamentfinland.data.models.CommentItem
import java.text.SimpleDateFormat
import java.util.*

/**
 * It defines all comments from users about members in a list
 * @param author defines the user who wrote the comment
 * @param commentContent defines the the comment
 * @param timeStamp defines the time and date the comment was created
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 05.10.2022
 */

class CommentsRecyclerAdapter(private val dataSet: List<CommentItem>): RecyclerView.Adapter<CommentsRecyclerAdapter.CommentCardViewHolder>() {
    inner class CommentCardViewHolder(private val v:View):RecyclerView.ViewHolder(v){
        val author:TextView = v.findViewById(R.id.comment_author)
        val commentContent:TextView = v.findViewById(R.id.comment_content)
        val timeStamp: TextView = v.findViewById(R.id.comment_created)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentCardViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.comment_card, parent, false)
        return CommentCardViewHolder(v)
    }

    override fun onBindViewHolder(holder: CommentCardViewHolder, position: Int) {
        holder.author.text = dataSet[position].author
        holder.commentContent.text = dataSet[position].content
        val date = Date(dataSet[position].created)
        holder.timeStamp.text = SimpleDateFormat("dd MMMM yyyy hh:mm:ss", Locale.ENGLISH).format(date)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}