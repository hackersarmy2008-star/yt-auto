package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShortsPilotDao {
    // Channels
    @Query("SELECT * FROM channels ORDER BY createdAt ASC")
    fun observeChannels(): Flow<List<ChannelEntity>>

    @Query("SELECT * FROM channels ORDER BY createdAt ASC")
    suspend fun getChannelsOnce(): List<ChannelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannel(channel: ChannelEntity): Long

    @Update
    suspend fun updateChannel(channel: ChannelEntity)

    @Query("DELETE FROM channels WHERE id = :channelId")
    suspend fun deleteChannel(channelId: Long)

    // Trend Topics
    @Query("SELECT * FROM trend_topics WHERE channelId = :channelId ORDER BY totalTrendScore DESC, discoveredAt DESC")
    fun observeTrends(channelId: Long): Flow<List<TrendTopicEntity>>

    @Query("SELECT * FROM trend_topics WHERE channelId = :channelId ORDER BY totalTrendScore DESC")
    suspend fun getTrendsOnce(channelId: Long): List<TrendTopicEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrends(trends: List<TrendTopicEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrend(trend: TrendTopicEntity): Long

    // Content Ideas
    @Query("SELECT * FROM short_ideas WHERE channelId = :channelId ORDER BY priorityScore DESC, createdAt DESC")
    fun observeIdeas(channelId: Long): Flow<List<ShortIdeaEntity>>

    @Query("SELECT * FROM short_ideas WHERE channelId = :channelId ORDER BY priorityScore DESC")
    suspend fun getIdeasOnce(channelId: Long): List<ShortIdeaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIdeas(ideas: List<ShortIdeaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIdea(idea: ShortIdeaEntity): Long

    @Update
    suspend fun updateIdea(idea: ShortIdeaEntity)

    // Scripts
    @Query("SELECT * FROM scripts WHERE channelId = :channelId ORDER BY createdAt DESC")
    fun observeScripts(channelId: Long): Flow<List<ScriptEntity>>

    @Query("SELECT * FROM scripts WHERE channelId = :channelId ORDER BY createdAt DESC")
    suspend fun getScriptsOnce(channelId: Long): List<ScriptEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScript(script: ScriptEntity): Long

    @Update
    suspend fun updateScript(script: ScriptEntity)

    // Short Videos
    @Query("SELECT * FROM short_videos WHERE channelId = :channelId ORDER BY createdAt DESC")
    fun observeVideos(channelId: Long): Flow<List<ShortVideoEntity>>

    @Query("SELECT * FROM short_videos WHERE channelId = :channelId ORDER BY createdAt DESC")
    suspend fun getVideosOnce(channelId: Long): List<ShortVideoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: ShortVideoEntity): Long

    @Update
    suspend fun updateVideo(video: ShortVideoEntity)

    // Content Learning Memory
    @Query("SELECT * FROM content_learnings WHERE channelId = :channelId ORDER BY confidenceScore DESC, updatedAt DESC")
    fun observeLearnings(channelId: Long): Flow<List<ContentLearningEntity>>

    @Query("SELECT * FROM content_learnings WHERE channelId = :channelId")
    suspend fun getLearningsOnce(channelId: Long): List<ContentLearningEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLearnings(learnings: List<ContentLearningEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLearning(learning: ContentLearningEntity): Long

    // Daily Automation Schedule
    @Query("SELECT * FROM schedule_tasks WHERE channelId = :channelId ORDER BY timeSlot ASC")
    fun observeSchedule(channelId: Long): Flow<List<ScheduleTaskEntity>>

    @Query("SELECT * FROM schedule_tasks WHERE channelId = :channelId ORDER BY timeSlot ASC")
    suspend fun getScheduleOnce(channelId: Long): List<ScheduleTaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScheduleTasks(tasks: List<ScheduleTaskEntity>)

    @Update
    suspend fun updateScheduleTask(task: ScheduleTaskEntity)

    // System Logs
    @Query("SELECT * FROM system_logs ORDER BY timestamp DESC LIMIT 200")
    fun observeLogs(): Flow<List<SystemLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: SystemLogEntity)

    @Query("DELETE FROM system_logs")
    suspend fun clearLogs()
}
