package fi.shaynek.parliamentfinland.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fi.shaynek.parliamentfinland.utils.Shared.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * This class is used to enhance communication between the viewModel and the web service
 * Implementation of Retrofit service API and Moshi
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 27.09.2022
 */


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */

private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */

object ParliamentApiClient {
    val retrofitService: ParliamentService by lazy{
        retrofit.create(ParliamentService::class.java)
    }

}