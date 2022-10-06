package fi.shaynek.parliamentfinland.data.network

import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData
import fi.shaynek.parliamentfinland.data.database.entity.MembersExtraData
import retrofit2.http.GET


interface ParliamentService {
    @GET("seating.json")
    suspend fun getBasicData(): List<MembersBasicData>


    @GET("extras.json")
    suspend fun getExtraData():List<MembersExtraData>

}