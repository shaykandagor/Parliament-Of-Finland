package fi.shaynek.parliamentfinland.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fi.shaynek.parliamentfinland.utils.Shared.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()



object ParliamentApiClient {
    val retrofitService: ParliamentService by lazy{
        retrofit.create(ParliamentService::class.java)
    }

}