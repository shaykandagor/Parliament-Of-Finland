package fi.shaynek.parliamentfinland.data.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fi.shaynek.parliamentfinland.data.database.dao.CommentsDao
import fi.shaynek.parliamentfinland.data.database.dao.ReactionsDao
import fi.shaynek.parliamentfinland.data.database.dao.MemberBasicDataDao
import fi.shaynek.parliamentfinland.data.database.dao.MembersExtraDataDao
import fi.shaynek.parliamentfinland.data.database.entity.Comments
import fi.shaynek.parliamentfinland.data.database.entity.MembersBasicData
import fi.shaynek.parliamentfinland.data.database.entity.MembersExtraData
import fi.shaynek.parliamentfinland.data.database.entity.Reactions

/**
 * Database entity for Member basic and extra data, comment amd reactions from users.
 * This class informs android studio that a pre-populated database is added
 * @author Shayne Kandagor
 * @studentId 2112916
 * @version 4.0
 * @since 26.09.2022
 */


@Database(entities = [MembersBasicData::class, MembersExtraData::class, Comments::class, Reactions::class], version = 4, exportSchema = false)

public abstract class ParliamentDatabase:RoomDatabase() {
    abstract val commentsDao: CommentsDao
    abstract val membersBasicDataDao: MemberBasicDataDao
    abstract val membersExtraDataDao: MembersExtraDataDao
    abstract val reactionsDao: ReactionsDao

    companion object{
        @Volatile
        private var INSTANCE: ParliamentDatabase?=null
        fun getDatabase(ctx:Context): ParliamentDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    ctx,
                    ParliamentDatabase::class.java,
                    "parliament_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                //return inst
                instance
            }
        }
    }
}