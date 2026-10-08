package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.local.ChannelEntity
import com.example.data.local.ContentLearningEntity
import com.example.data.local.ScheduleTaskEntity
import com.example.data.local.ShortVideoEntity
import com.example.data.local.SystemLogEntity
import com.example.engine.FailureDiagnosticResult
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CoralAlert
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.EmeraldPass
import com.example.ui.theme.IndiaSaffron
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceHigh
import com.example.ui.theme.ShortsCrimson
import com.example.ui.theme.StrategyViolet
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.UsaBlue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun UploadQueueAndSchedulerScreen(
    channel: ChannelEntity?,
    videos: List<ShortVideoEntity>,
    scheduleTasks: List<ScheduleTaskEntity>,
    onApproveAndPublish: (ShortVideoEntity) -> Unit,
    onToggleScheduleTask: (ScheduleTaskEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("OFFICIAL YOUTUBE API UPLOAD QUEUE & SCHEDULER", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Text(
                text = if (channel?.autoModeEnabled == true) {
                    "AUTO MODE ON: Shorts passing 12/12 QC are automatically scheduled for ${channel.preferredUploadTime}."
                } else {
                    "AUTO MODE OFF: System holds rendered Shorts below for manual approval before YouTube API upload."
                },
                style = MaterialTheme.typography.bodySmall,
                color = if (channel?.autoModeEnabled == true) EmeraldPass else AmberWarning
            )
        }

        items(videos, key = { it.id }) { video ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "STATUS: ${video.uploadStatus}",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (video.uploadStatus == "PUBLISHED") EmeraldPass else AmberWarning
                        )
                        Text(video.scheduledPublishTime, style = MaterialTheme.typography.labelSmall, color = ElectricCyan)
                    }
                    Text(video.title, style = MaterialTheme.typography.titleMedium, color = Color.White)
                    Text("Hashtags: ${video.hashtags}", style = MaterialTheme.typography.labelSmall, color = TextSecondary)

                    if (video.uploadStatus != "PUBLISHED") {
                        Button(
                            onClick = { onApproveAndPublish(video) },
                            colors = ButtonDefaults.buttonColors(containerColor = ShortsCrimson),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("publish_video_${video.id}")
                        ) {
                            Icon(Icons.Default.CloudUpload, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("APPROVE & UPLOAD VIA OFFICIAL YOUTUBE API v3", style = MaterialTheme.typography.labelLarge)
                        }
                    } else {
                        Text(
                            text = "YouTube Video ID: ${video.youtubeVideoId} • Views: ${video.views} • Retention: ${video.retentionPercent}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldPass
                        )
                    }
                }
            }
        }

        // Section 12: Daily Automation Timeline (06:00 -> 18:00)
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text("DAILY AUTOMATION PIPELINE SCHEDULE", style = MaterialTheme.typography.titleLarge, color = ElectricCyan)
        }

        items(scheduleTasks, key = { it.id }) { task ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurfaceHigh),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("${task.timeSlot} • ${task.taskTitle}", style = MaterialTheme.typography.titleSmall, color = Color.White)
                        Text(task.lastRunSummary, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    }
                    Switch(
                        checked = task.isEnabled,
                        onCheckedChange = { onToggleScheduleTask(task) }
                    )
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun AnalyticsDashboardScreen(
    videos: List<ShortVideoEntity>
) {
    val published = videos.filter { it.views > 0 }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("YOUTUBE SHORTS ANALYTICS ENGINE", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Text(
                "Tracks Views, Viewed vs Swiped Away %, Average View Duration, Audience Retention, Sub Conversion, and USA vs India Geography.",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }

        items(published, key = { it.id }) { v ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(v.title, style = MaterialTheme.typography.titleMedium, color = Color.White)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Views: %,d".format(v.views), style = MaterialTheme.typography.labelLarge, color = ElectricCyan)
                        Text("Subs: +${v.subscribersGained}", style = MaterialTheme.typography.labelLarge, color = EmeraldPass)
                        Text("AVD: ${"%.1f".format(v.avgViewDurationSec)}s / ${v.durationSec}s", style = MaterialTheme.typography.labelLarge, color = Color.White)
                    }

                    // Viewed vs Swiped Bar
                    Text(
                        text = "Viewed vs Swiped Away: ${"%.1f".format(v.viewedVsSwipedPercent)}% Viewed / ${"%.1f".format(v.swipeAwayPercent)}% Swiped",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (v.viewedVsSwipedPercent >= 70f) EmeraldPass else CoralAlert
                    )
                    LinearProgressIndicator(
                        progress = { (v.viewedVsSwipedPercent / 100f).coerceIn(0f, 1f) },
                        color = if (v.viewedVsSwipedPercent >= 70f) EmeraldPass else CoralAlert,
                        trackColor = ObsidianSurfaceHigh,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )

                    // Retention Bar
                    Text(
                        text = "Audience Retention (APV): ${"%.1f".format(v.retentionPercent)}% • Engagement: ${"%.1f".format(v.engagementRatePercent)}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricCyan
                    )
                    LinearProgressIndicator(
                        progress = { (v.retentionPercent / 100f).coerceIn(0f, 1f) },
                        color = ElectricCyan,
                        trackColor = ObsidianSurfaceHigh,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )

                    Text(
                        text = "Geography: 🇺🇸 USA ${v.usaViewsPercent.toInt()}% | 🇮🇳 India ${v.indiaViewsPercent.toInt()}% • Shorts Feed Traffic: ${v.shortsFeedTrafficPercent.toInt()}% • Returning Viewers: ${v.returningViewersPercent.toInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun FailedShortsDiagnosticScreen(
    videos: List<ShortVideoEntity>,
    selectedCheckpoint: String,
    onSelectCheckpoint: (String) -> Unit,
    onDiagnoseVideo: (ShortVideoEntity) -> FailureDiagnosticResult,
    onApplyAutoCorrection: (ShortVideoEntity) -> Unit
) {
    val checkpoints = listOf("1 hour", "6 hours", "24 hours", "48 hours", "7 days")
    val underperforming = videos.filter { it.performanceVerdict == "UNDERPERFORMING" || it.viewedVsSwipedPercent < 65f || it.views < 10000 }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("\"WHY DID THIS SHORT FAIL?\" ENGINE", style = MaterialTheme.typography.headlineMedium, color = CoralAlert)
            Text(
                "Compares every uploaded Short against channel averages across configurable intervals and automatically generates a correction plan + improved Short.",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                checkpoints.forEach { cp ->
                    FilterChip(
                        selected = selectedCheckpoint == cp,
                        onClick = { onSelectCheckpoint(cp) },
                        label = { Text(cp, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
        }

        items(underperforming, key = { it.id }) { video ->
            val diag = onDiagnoseVideo(video)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, CoralAlert.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "DETECTED: ${diag.failureCode}",
                            style = MaterialTheme.typography.labelLarge,
                            color = CoralAlert
                        )
                        Text("Checkpoint: $selectedCheckpoint", style = MaterialTheme.typography.labelSmall, color = AmberWarning)
                    }

                    Text(video.title, style = MaterialTheme.typography.titleMedium, color = Color.White)
                    Text(
                        text = "Metrics: ${video.views} views • ${video.viewedVsSwipedPercent}% Viewed vs Swiped • ${video.retentionPercent}% Retention (${video.durationSec}s)",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )

                    // Plain language explanation (Section 14)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(ObsidianSurfaceHigh)
                            .padding(12.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("PROBABLE CAUSE (PLAIN LANGUAGE):", style = MaterialTheme.typography.labelSmall, color = AmberWarning)
                            Text(diag.plainLanguageReason, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                        }
                    }

                    // Automatic Correction Plan (Section 15)
                    Text("AUTOMATIC CORRECTION PLAN:", style = MaterialTheme.typography.labelSmall, color = EmeraldPass)
                    diag.automaticCorrectionSteps.forEach { step ->
                        Text("• $step", style = MaterialTheme.typography.bodySmall, color = Color.White)
                    }

                    Text(
                        text = "Improved Hook Ready (${diag.improvedDurationSec}s): \"${diag.improvedHookSuggestion}\"",
                        style = MaterialTheme.typography.bodySmall,
                        color = ElectricCyan
                    )

                    Button(
                        onClick = { onApplyAutoCorrection(video) },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPass, contentColor = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("apply_autocorrection_${video.id}")
                    ) {
                        Icon(Icons.Default.AutoFixHigh, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("APPLY CORRECTION & SPAWN IMPROVED SHORT", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun SuccessfulShortsScreen(
    videos: List<ShortVideoEntity>,
    onReplicateSuccessPattern: (ShortVideoEntity) -> Unit
) {
    val winners = videos.filter { it.performanceVerdict == "SUCCESS" || it.retentionPercent >= 80f }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("SUCCESSFUL SHORTS & A/B WINNERS", style = MaterialTheme.typography.headlineMedium, color = EmeraldPass)
            Text(
                "High-retention Shorts that beat channel baselines. Replicate winning duration, hook cadence, and localization patterns.",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }

        items(winners, key = { it.id }) { v ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, EmeraldPass.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("WINNER BENCHMARK • ${v.views} VIEWS", style = MaterialTheme.typography.labelLarge, color = EmeraldPass)
                    Text(v.title, style = MaterialTheme.typography.titleMedium, color = Color.White)
                    Text(
                        text = "Retention: ${v.retentionPercent}% • Viewed vs Swiped: ${v.viewedVsSwipedPercent}% • Duration: ${v.durationSec}s",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricCyan
                    )
                    Text("Why It Worked: ${v.failurePlainExplanation}", style = MaterialTheme.typography.bodySmall, color = Color.White)
                    Text("A/B Test Telemetry: ${v.abTestGroup} — ${v.abTestComparisonNote}", style = MaterialTheme.typography.bodySmall, color = StrategyViolet)

                    Button(
                        onClick = { onReplicateSuccessPattern(v) },
                        colors = ButtonDefaults.buttonColors(containerColor = StrategyViolet),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("SCALE THIS PATTERN INTO NEW SHORT IDEA", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun AiStrategyAndLearningScreen(
    channel: ChannelEntity?,
    learnings: List<ContentLearningEntity>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("CONTENT LEARNING SYSTEM & A/B STRATEGY", style = MaterialTheme.typography.headlineMedium, color = StrategyViolet)
            Text(
                "SQLite Memory Tables: successful_topics, failed_topics, successful_hooks, failed_hooks, successful_lengths, successful_styles, successful_upload_times, audience_preferences, country_performance.",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurfaceHigh),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("ACTIVE CHANNEL OPTIMIZATION DIRECTIVE", style = MaterialTheme.typography.labelLarge, color = EmeraldPass)
                    Text(channel?.activeStrategySummary ?: "", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    Text(
                        text = stringResource(R.string.disclaimer_no_fake_virality),
                        style = MaterialTheme.typography.labelSmall,
                        color = AmberWarning
                    )
                }
            }
        }

        items(learnings, key = { it.id }) { item ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.category.uppercase(), style = MaterialTheme.typography.labelSmall, color = ElectricCyan)
                        Text("CONFIDENCE: ${item.confidenceScore}%", style = MaterialTheme.typography.labelSmall, color = EmeraldPass)
                    }
                    Text(item.patternName, style = MaterialTheme.typography.titleMedium, color = Color.White)
                    Text(
                        text = "${item.metricSummary} • Avg Retention: ${item.avgRetentionPercent}% • Viewed vs Swiped: ${item.avgViewedVsSwipedPercent}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                    Text("Rule Applied to New Shorts: ${item.actionableRule}", style = MaterialTheme.typography.bodySmall, color = EmeraldPass)
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun SettingsAndSecurityScreen(
    activeAiProvider: String,
    isYouTubeOAuthReady: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("PROVIDER ABSTRACTION & SECURITY", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Text(
                "Free-First Architecture: OpenRouter + Gemini REST fallback + Local SQLite + Android Hardware MediaCodec H.264.",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("ACTIVE AI PROVIDER ABSTRACTION", style = MaterialTheme.typography.labelLarge, color = ElectricCyan)
                    Text(activeAiProvider, style = MaterialTheme.typography.titleMedium, color = EmeraldPass)
                    Text(
                        "Configured via .env / AI Studio Secrets panel (OPENROUTER_API_KEY, GEMINI_API_KEY). Zero hardcoded credentials in source code.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("OFFICIAL YOUTUBE DATA API v3 & OAUTH 2.0", style = MaterialTheme.typography.labelLarge, color = ShortsCrimson)
                    Text(
                        text = if (isYouTubeOAuthReady) "Connected via YOUTUBE_ACCESS_TOKEN" else "Local Queue Ready • Set YOUTUBE_ACCESS_TOKEN & YOUTUBE_API_KEY in AI Studio Secrets Panel for live channel upload",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isYouTubeOAuthReady) EmeraldPass else AmberWarning
                    )
                    Text(
                        "Policy Compliance: Uses Official YouTube Data API v3 resumable upload & YouTube Analytics API v2. Browser automation is strictly prohibited.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurfaceHigh),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, AmberWarning.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = AmberWarning)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("PROTOTYPE APK SECURITY NOTICE", style = MaterialTheme.typography.labelLarge, color = AmberWarning)
                    }
                    Text(
                        text = stringResource(R.string.security_warning_apk),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun SystemLogsScreen(
    logs: List<SystemLogEntity>,
    onClearLogs: () -> Unit
) {
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.US)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("FAULT-TOLERANT JOB QUEUE LOGS", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                    Text("Retries, API fallbacks, QC gate audits & scheduler telemetry.", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
                OutlinedButton(onClick = onClearLogs) {
                    Icon(Icons.Default.DeleteSweep, contentDescription = null, tint = ElectricCyan)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("CLEAR", style = MaterialTheme.typography.labelSmall, color = ElectricCyan)
                }
            }
        }

        items(logs, key = { it.id }) { log ->
            val badgeColor = when (log.level) {
                "SUCCESS" -> EmeraldPass
                "WARN" -> AmberWarning
                "RECOVERY" -> StrategyViolet
                else -> ElectricCyan
            }
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("[${log.level}] ${log.subsystem}", style = MaterialTheme.typography.labelSmall, color = badgeColor)
                        Text(formatter.format(Date(log.timestamp)), style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    }
                    Text(log.message, style = MaterialTheme.typography.bodyMedium, color = Color.White)
                    if (log.details.isNotBlank()) {
                        Text(log.details, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
