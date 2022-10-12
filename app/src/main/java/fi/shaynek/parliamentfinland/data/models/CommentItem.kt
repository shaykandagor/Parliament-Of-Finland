package fi.shaynek.parliamentfinland.data.models

/**
 * It defines the data of comments that can be fetched from the database
 * displayed when a user creates a comment
 * @param author defines the name of the user
 * @param content defines the comment of the user
 * @param timeStamp defines the time and date when the comment was published
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 05.10.2022
 */


data class CommentItem(
    val author: String,
    val content: String,
    val created:Long

)
val Author = "Shayne"