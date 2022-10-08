package fi.shaynek.parliamentfinland.data.models

import android.graphics.drawable.Icon

data class PartyItem(
    var title: String,
    var head: String,
    var icon: Int,
    var count: Int,
    var onClick: Runnable
)