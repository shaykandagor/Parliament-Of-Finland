package fi.shaynek.parliamentfinland.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(
    tableName = "member_extra_data",
    foreignKeys = [
        ForeignKey(
            entity = MembersBasicData::class,
            parentColumns = ["heteka_id"],
            childColumns = ["heteka_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MembersExtraData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int = 0,
    @Json(name = "hetekaId")
    @ColumnInfo(name = "heteka_id", index = true)
    val hetekaId: Int,
    @Json(name = "twitter")
    @ColumnInfo(name = "twitter_handle")
    val twitterHandle:String="",
    @Json(name = "bornYear")
    @ColumnInfo(name = "born_year")
    val bornYear:Int,
    @Json(name = "constituency")
    @ColumnInfo(name = "constituency")
    val constituency:String=""

)