package fi.shaynek.parliamentfinland.data.network

/**
 * This class defines the current status of fetching data from web Api and caching in the database
 * It ensures all the basic details fetched from the network are stored in the database
 * before extra details are fetched and stored in the database.
 * This is essential for avoiding SQLite constraint exception that may arise
 * due to missing related data parent table(basic details table)
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 1.0
 * @since 26.09.2022
 */
enum class ParliamentApiStatus {
    LOADING, ERROR,DONE
}