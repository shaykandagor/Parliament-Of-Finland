package fi.shaynek.parliamentfinland.data.models

/**
 * contains all the data to be displayed in recycler view items(viewholder)
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 05.10.2022
 */

data class PartyMemberItem(
    var name: String,
    var image: String,
    var constituency: String,
    var seatNumber: Int,
    var onClick:Runnable
)