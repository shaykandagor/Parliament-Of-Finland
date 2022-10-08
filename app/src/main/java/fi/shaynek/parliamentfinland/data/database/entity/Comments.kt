package fi.shaynek.parliamentfinland.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.Date

/**
 * This class defines comments from the users that can be fetched from database
 * @author Shayne Kandagor
 * @version 1.0
 * @since 26.09.2022
 */


@Entity(
    tableName = "comments",

    foreignKeys = [
        ForeignKey(
            entity = MembersBasicData::class,
            parentColumns = ["heteka_id"],
            childColumns = ["heteka_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]

)
data class Comments(
    @Json(name = "hetekaId")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int = 0,
    @ColumnInfo(name = "heteka_id", index = true)
    val hetekaId: Int,
    @ColumnInfo(name = "comment")
    val comment: String="",
    @ColumnInfo(name = "created")
    val timeStamp: Long = Date().time
)