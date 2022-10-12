package fi.shaynek.parliamentfinland.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * This class defines reactions of users from members fetched from the database
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 4.0
 * @since 09.10.2022
 */

@Entity(
    tableName = "reaction",
    foreignKeys = [
        ForeignKey(
            entity = MembersBasicData::class,
            parentColumns = ["heteka_id"],
            childColumns = ["heteka_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Reactions (
    @PrimaryKey
    @ColumnInfo(name = "heteka_id", index = true)
    val hetekaId: Int=0,
    @ColumnInfo(name = "likes")
    var likes:Int = 0,
    @ColumnInfo(name = "dislikes")
    val dislikes:Int = 0
)