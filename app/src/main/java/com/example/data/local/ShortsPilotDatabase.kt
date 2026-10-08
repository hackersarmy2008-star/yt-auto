package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ChannelEntity::class,
        TrendTopicEntity::class,
        ShortIdeaEntity::class,
        ScriptEntity::class,
        ShortVideoEntity::class,
        ContentLearningEntity::class,
        ScheduleTaskEntity::class,
        SystemLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ShortsPilotDatabase : RoomDatabase() {
    abstract fun dao(): ShortsPilotDao

    companion object {
        @Volatile
        private var INSTANCE: ShortsPilotDatabase? = null

        fun getInstance(context: Context): ShortsPilotDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShortsPilotDatabase::class.java,
                    "shortspilot_autopilot.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
