package fi.shaynek.parliamentfinland.data.models

/**
 * It defines the data of parties that can be fetched from the database
 * displayed when a user clicks one particular party
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 3.0
 * @since 29.09.2022
 */

data class PartyItem(
    var title: String,
    var head: String,
    var icon: Int,
    var count: Int,
    var onClick: Runnable
)