package fi.shaynek.parliamentfinland.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


/**
 * This class defines Member basic details whose information can be fetched from database
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 26.09.2022
 */

@Entity(
    tableName = "members_basic_data"
)
data class MembersBasicData(
    @Json(name = "hetekaId")
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "heteka_id")
    val hetekaId: Int = 0,
    @Json(name = "seatNumber")
    @ColumnInfo(name = "seat_no")
    val seatNo: Int,
    @Json(name = "lastname")
    @ColumnInfo(name = "last_name")
    val lastName:String = "",
    @Json(name = "firstname")
    @ColumnInfo(name = "first_name")
    val firstName:String="",
    @Json(name = "minister")
    @ColumnInfo(name="minister")
    val minister: Boolean,
    @Json(name = "pictureUrl")
    @ColumnInfo(name="photo_url")
    val photoUrl:String="",
    @Json(name = "party")
    @ColumnInfo(name = "party")
    val party:String

)