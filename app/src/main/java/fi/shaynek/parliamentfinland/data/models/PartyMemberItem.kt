package dev.vstec.parliament2.data.models

data class PartyMemberItem(
    var name: String,
    var image: Int,
    var constituency: String,
    var seatNumber: Int,
    var onClick:Runnable
)