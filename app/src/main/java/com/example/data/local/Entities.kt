package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class ChannelEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val description: String,
    val niche: String,
    val targetCountry: String, // "USA", "India", "Both"
    val primaryLanguage: String, // "English", "Hindi", "Hinglish"
    val uploadFrequency: String, // "1/day", "2/day", "3/day"
    val preferredUploadTime: String, // e.g., "18:30 EST / 20:00 IST"
    val targetDurationSec: Int, // e.g., 28
    val contentStyle: String, // e.g., "Fast-Paced Curiosity + Data Reveal"
    val autoModeEnabled: Boolean = true,
    val youtubeChannelId: String = "",
    val oauthConnected: Boolean = false,
    val activeStrategySummary: String = "Prioritize 22-30s curiosity hooks with visual cut at 0.8s; dual-localize US/India cultural references.",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "trend_topics")
data class TrendTopicEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val channelId: Long,
    val title: String,
    val niche: String,
    val targetCountry: String, // "USA", "India", "Both"
    val source: String, // "YouTube Data API", "Google News RSS", "Reddit Hot", "Wikipedia Current"
    val categoryStatus: String, // "Rising", "Popular", "Content Gap", "Emerging", "Seasonal", "Repeated"
    val recencyScore: Int, // 0-20
    val searchInterestScore: Int, // 0-20
    val youtubeInterestScore: Int, // 0-20
    val competitionScore: Int, // 0-15 (higher means lower competition / better opportunity)
    val audienceRelevanceScore: Int, // 0-15
    val contentGapScore: Int, // 0-10
    val totalTrendScore: Int, // 0-100 sum
    val usaAngle: String,
    val indiaAngle: String,
    val discoveredAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "short_ideas")
data class ShortIdeaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val channelId: Long,
    val trendId: Long = 0,
    val topic: String,
    val hook: String,
    val targetAudience: String, // "USA", "India", "Universal (US+IN)", "Localized Dual (US & IN)"
    val localizationStrategy: String, // Explains US vs India localization
    val estimatedInterest: String, // "HIGH", "VERY HIGH", "MEDIUM"
    val competitionLevel: String, // "LOW", "MEDIUM", "HIGH"
    val whyItMayWork: String,
    val suggestedDurationSec: Int,
    val storyStructure: String,
    val visualRequirements: String,
    val cta: String,
    val titleOptions: String, // Pipe-separated title options
    val priorityScore: Int, // 0-100
    val status: String = "READY", // "READY", "SCRIPTED", "RENDERED", "PUBLISHED"
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "scripts")
data class ScriptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val channelId: Long,
    val ideaId: Long,
    val title: String,
    val targetAudience: String,
    val language: String,
    val hookAlt1: String,
    val hookAlt2: String,
    val hookAlt3: String,
    val selectedHookIndex: Int, // 1, 2, or 3
    val hook0to2Sec: String,
    val curiosity2to5Sec: String,
    val body5to20Sec: String,
    val payoffFinalSec: String,
    val ctaLine: String,
    val descriptionText: String,
    val hashtags: String,
    val suggestedDurationSec: Int,
    val voiceEngine: String, // "Android Neural TTS (en-US)", "Android Neural TTS (hi-IN)", "Edge/Piper Hinglish"
    val hookStrengthScore: Int,
    val curiosityScore: Int,
    val retentionPotentialScore: Int,
    val clarityScore: Int,
    val originalityScore: Int,
    val emotionalImpactScore: Int,
    val shareabilityScore: Int,
    val overallScore: Int,
    val isRejected: Boolean = false,
    val rejectionReason: String = "",
    val probabilityStatement: String, // Measurable optimization statement (NO fake virality claims)
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "short_videos")
data class ShortVideoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val channelId: Long,
    val scriptId: Long,
    val title: String,
    val description: String,
    val hashtags: String,
    val targetAudience: String,
    val language: String,
    val filePath: String, // Path to rendered 1080x1920 H.264 MP4
    val audioPath: String, // Path to synthesized WAV/AAC audio
    val srtCaptions: String, // Timed SRT captions
    val resolution: String = "1080x1920 (9:16)",
    val durationSec: Int,
    val voiceUsed: String,
    val bgMusicTrack: String,
    val qcPassed: Boolean,
    val qcChecklistJson: String, // Detailed 12-point QC report
    val uploadStatus: String, // "QUEUED_APPROVAL", "SCHEDULED", "PUBLISHED", "FAILED_QC"
    val scheduledPublishTime: String,
    val youtubeVideoId: String = "",
    val privacyStatus: String = "public",
    // Analytics metrics
    val views: Int = 0,
    val likes: Int = 0,
    val comments: Int = 0,
    val shares: Int = 0,
    val subscribersGained: Int = 0,
    val avgViewDurationSec: Float = 0f,
    val retentionPercent: Float = 0f,
    val viewedVsSwipedPercent: Float = 0f,
    val swipeAwayPercent: Float = 0f,
    val engagementRatePercent: Float = 0f,
    val usaViewsPercent: Float = 50f,
    val indiaViewsPercent: Float = 50f,
    val returningViewersPercent: Float = 24f,
    val shortsFeedTrafficPercent: Float = 86f,
    // Failure diagnosis & auto-correction
    val analysisInterval: String = "24 hours", // 1h, 6h, 24h, 48h, 7d
    val performanceVerdict: String = "PENDING", // "SUCCESS", "UNDERPERFORMING", "PENDING"
    val failureCode: String = "NONE", // e.g., "HIGH SWIPE RATE", "LOW VIEWED VS SWIPED", etc.
    val failurePlainExplanation: String = "",
    val autoCorrectionPlan: String = "",
    val abTestGroup: String = "Variant A (Curiosity Hook)",
    val abTestComparisonNote: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "content_learnings")
data class ContentLearningEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val channelId: Long,
    val category: String, // successful_topics, failed_topics, successful_hooks, failed_hooks, successful_lengths, successful_styles, successful_upload_times, audience_preferences, country_performance
    val patternName: String,
    val metricSummary: String,
    val avgRetentionPercent: Float,
    val avgViewedVsSwipedPercent: Float,
    val confidenceScore: Int,
    val actionableRule: String,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "schedule_tasks")
data class ScheduleTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val channelId: Long,
    val timeSlot: String, // e.g. "06:00", "07:00", "08:00"
    val taskTitle: String,
    val stageCode: String, // RESEARCH, IDEAS, SELECT, SCRIPT, ASSETS, RENDER, QC, UPLOAD, ANALYTICS
    val isEnabled: Boolean = true,
    val lastStatus: String = "COMPLETED",
    val lastRunSummary: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "system_logs")
data class SystemLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val level: String, // "INFO", "WARN", "RECOVERY", "SUCCESS"
    val subsystem: String, // "AI_ENGINE", "TREND_ENGINE", "VIDEO_RENDERER", "QC_GATE", "YOUTUBE_API", "FAILURE_ANALYZER"
    val message: String,
    val details: String = ""
)
