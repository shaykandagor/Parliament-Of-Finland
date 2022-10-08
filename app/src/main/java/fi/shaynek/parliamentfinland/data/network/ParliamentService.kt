package fi.shaynek.parliamentfinland.data.network

import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData
import fi.shaynek.parliamentfinland.data.database.entity.MembersExtraData
import retrofit2.http.GET

/**
 * This interface defines how Retrofit communicates with the web server using HTTP requests.
 * It defines fetching pf the mebers basic and extra data
 */

interface ParliamentService {
    @GET("seating.json")
    suspend fun getBasicData(): List<MembersBasicData>


    @GET("extras.json")
    suspend fun getExtraData():List<MembersExtraData>

}