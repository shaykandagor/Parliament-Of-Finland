package fi.shaynek.parliamentfinland.ui.fragments

/**
 * This class defines the list of comments from users
 * @param author defines the name of the user
 * @param content defines the comment of the user
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