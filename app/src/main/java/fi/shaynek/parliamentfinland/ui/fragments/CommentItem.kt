package fi.shaynek.parliamentfinland.ui.fragments

/**
 * It defines the properties of comments from the user that can be fetched from the database
 * @param author defines the name of the user
 * @param content defines the comment of the user
 * @param timeStamp defines the time and date when the commnet was published
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 05.10.2022
 */

data class CommentItem(
    val author: String,
    val content: String,
    val timeStamp: Long
)