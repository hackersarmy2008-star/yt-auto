package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AutopilotViewModel
import com.example.ui.DashboardPage
import com.example.ui.screens.AiStrategyAndLearningScreen
import com.example.ui.screens.AnalyticsDashboardScreen
import com.example.ui.screens.ChannelsWizardScreen
import com.example.ui.screens.DashboardOverviewScreen
import com.example.ui.screens.FailedShortsDiagnosticScreen
import com.example.ui.screens.IdeasGeneratorScreen
import com.example.ui.screens.NicheSettingsScreen
import com.example.ui.screens.ScriptsStudioScreen
import com.example.ui.screens.SettingsAndSecurityScreen
import com.example.ui.screens.SuccessfulShortsScreen
import com.example.ui.screens.SystemLogsScreen
import com.example.ui.screens.TrendResearchScreen
import com.example.ui.screens.UploadQueueAndSchedulerScreen
import com.example.ui.screens.VideoGeneratorScreen
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.EmeraldPass
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceHigh
import com.example.ui.theme.ShortsCrimson
import com.example.ui.theme.TextSecondary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ShortsPilotApp()
            }
        }
    }
}

@Composable
fun ShortsPilotApp(viewModel: AutopilotViewModel = viewModel()) {
    val currentPage by viewModel.currentPage.collectAsStateWithLifecycle()
    val channels by viewModel.channels.collectAsStateWithLifecycle()
    val selectedChannelId by viewModel.selectedChannelId.collectAsStateWithLifecycle()
    val trends by viewModel.trends.collectAsStateWithLifecycle()
    val ideas by viewModel.ideas.collectAsStateWithLifecycle()
    val scripts by viewModel.scripts.collectAsStateWithLifecycle()
    val videos by viewModel.videos.collectAsStateWithLifecycle()
    val learnings by viewModel.learnings.collectAsStateWithLifecycle()
    val scheduleTasks by viewModel.scheduleTasks.collectAsStateWithLifecycle()
    val logs by viewModel.logs.collectAsStateWithLifecycle()
    val pipelineStatus by viewModel.pipelineStatus.collectAsStateWithLifecycle()
    val selectedCheckpoint by viewModel.selectedCheckpoint.collectAsStateWithLifecycle()

    val activeChannel = channels.find { it.id == selectedChannelId } ?: channels.firstOrNull()

    if (currentPage != DashboardPage.DASHBOARD) {
        BackHandler {
            viewModel.navigateTo(DashboardPage.DASHBOARD)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ObsidianBackground,
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ObsidianSurface)
                    .padding(top = 8.dp, bottom = 8.dp)
            ) {
                // Header Row: Brand + Channel Selector + Auto Mode Indicator
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(ShortsCrimson)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "SHORTSPILOT",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "9:16 AI",
                            style = MaterialTheme.typography.labelSmall,
                            color = ElectricCyan
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(ObsidianSurfaceHigh)
                            .border(1.dp, BorderSubtle, RoundedCornerShape(50))
                            .clickable { viewModel.navigateTo(DashboardPage.CHANNELS) }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .testTag("header_channel_selector")
                    ) {
                        Text(
                            text = activeChannel?.name ?: "Select Channel",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "• ${activeChannel?.targetCountry ?: "Both"}",
                            style = MaterialTheme.typography.labelSmall,
                            color = ElectricCyan
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 14-Page Workstation Ribbon
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(DashboardPage.entries) { page ->
                        val selected = page == currentPage
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(if (selected) ShortsCrimson else ObsidianSurfaceHigh)
                                .clickable { viewModel.navigateTo(page) }
                                .padding(horizontal = 14.dp, vertical = 7.dp)
                                .testTag("ribbon_tab_${page.name.lowercase()}")
                        ) {
                            Text(
                                text = page.title,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (selected) Color.White else TextSecondary
                            )
                        }
                    }
                }

                // Live Pipeline Progress Bar & Status Banner
                AnimatedVisibility(visible = pipelineStatus.isRunning || pipelineStatus.lastResultBanner.isNotBlank()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(ObsidianSurfaceHigh)
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = pipelineStatus.currentStage,
                                style = MaterialTheme.typography.labelSmall,
                                color = ElectricCyan
                            )
                            if (pipelineStatus.isRunning) {
                                Text(
                                    text = "${(pipelineStatus.progress * 100).toInt()}%",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = EmeraldPass
                                )
                            }
                        }
                        if (pipelineStatus.isRunning) {
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { pipelineStatus.progress },
                                color = ShortsCrimson,
                                trackColor = ObsidianBackground,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                                    .clip(RoundedCornerShape(3.dp))
                            )
                        } else if (pipelineStatus.lastResultBanner.isNotBlank()) {
                            Text(
                                text = pipelineStatus.lastResultBanner,
                                style = MaterialTheme.typography.bodySmall,
                                color = EmeraldPass
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = ObsidianSurface,
                tonalElevation = 8.dp
            ) {
                val primaryTabs = listOf(
                    Triple(DashboardPage.DASHBOARD, "Command", Icons.Default.Dashboard),
                    Triple(DashboardPage.IDEAS, "Ideas", Icons.Default.AutoAwesome),
                    Triple(DashboardPage.VIDEO_GENERATOR, "9:16 Studio", Icons.Default.MovieFilter),
                    Triple(DashboardPage.FAILED_SHORTS, "Why Failed?", Icons.Default.BugReport),
                    Triple(DashboardPage.AI_STRATEGY, "Strategy", Icons.Default.Psychology)
                )
                primaryTabs.forEach { (page, label, icon) ->
                    NavigationBarItem(
                        selected = currentPage == page,
                        onClick = { viewModel.navigateTo(page) },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = ShortsCrimson,
                            indicatorColor = ShortsCrimson,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("bottom_nav_${page.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentPage) {
                DashboardPage.DASHBOARD -> DashboardOverviewScreen(
                    channel = activeChannel,
                    trends = trends,
                    ideas = ideas,
                    videos = videos,
                    onToggleAutoMode = { enabled ->
                        if (activeChannel != null) viewModel.toggleAutoMode(activeChannel, enabled)
                    },
                    onRunFullAutopilot = { viewModel.runFullAutopilotCycleNow() },
                    onNavigate = { viewModel.navigateTo(it) }
                )

                DashboardPage.CHANNELS -> ChannelsWizardScreen(
                    channels = channels,
                    selectedChannelId = selectedChannelId,
                    onSelectChannel = { viewModel.selectChannel(it) },
                    onCreateChannel = { name, desc, niche, country, lang, freq, time, dur, style ->
                        viewModel.createNewChannel(name, desc, niche, country, lang, freq, time, dur, style)
                    }
                )

                DashboardPage.NICHE_SETTINGS -> NicheSettingsScreen(
                    channel = activeChannel,
                    onUpdateChannel = { viewModel.updateChannelSettings(it) }
                )

                DashboardPage.TREND_RESEARCH -> TrendResearchScreen(
                    channel = activeChannel,
                    trends = trends,
                    onScanLiveTrends = { viewModel.runTrendResearchCycle() },
                    onGenerateIdeasFromTrends = {
                        viewModel.generateIdeasFromCurrentTrends()
                        viewModel.navigateTo(DashboardPage.IDEAS)
                    }
                )

                DashboardPage.IDEAS -> IdeasGeneratorScreen(
                    ideas = ideas,
                    onGenerateMoreIdeas = { viewModel.generateIdeasFromCurrentTrends() },
                    onGenerateScriptForIdea = { viewModel.generateScriptForIdea(it) }
                )

                DashboardPage.SCRIPTS -> ScriptsStudioScreen(
                    scripts = scripts,
                    onSelectHookVariant = { script, idx -> viewModel.switchSelectedHookOnScript(script, idx) },
                    onSpeakScript = { viewModel.speakScriptPreview(it) },
                    onStopSpeaking = { viewModel.stopAudioPreview() },
                    onRenderShortMp4 = { viewModel.renderVideoForScript(it) }
                )

                DashboardPage.VIDEO_GENERATOR -> VideoGeneratorScreen(
                    channel = activeChannel,
                    scripts = scripts,
                    videos = videos,
                    onRenderPreviewBitmap = { script, chName, sceneIdx ->
                        viewModel.renderPreviewFrame(script, chName, sceneIdx)
                    },
                    onRenderFullShort = { viewModel.renderVideoForScript(it) },
                    onSpeakScript = { viewModel.speakScriptPreview(it) },
                    onStopAudio = { viewModel.stopAudioPreview() },
                    onShareMp4 = { viewModel.shareRenderedMp4(it) }
                )

                DashboardPage.UPLOAD_QUEUE -> UploadQueueAndSchedulerScreen(
                    channel = activeChannel,
                    videos = videos,
                    scheduleTasks = scheduleTasks,
                    onApproveAndPublish = { viewModel.approveAndPublishVideo(it) },
                    onToggleScheduleTask = { viewModel.toggleScheduleTask(it) }
                )

                DashboardPage.ANALYTICS -> AnalyticsDashboardScreen(
                    videos = videos
                )

                DashboardPage.FAILED_SHORTS -> FailedShortsDiagnosticScreen(
                    videos = videos,
                    selectedCheckpoint = selectedCheckpoint,
                    onSelectCheckpoint = { viewModel.setAnalysisCheckpoint(it) },
                    onDiagnoseVideo = { viewModel.diagnoseAndAutoCorrectVideo(it) },
                    onApplyAutoCorrection = { viewModel.triggerAutoCorrectionPlan(it) }
                )

                DashboardPage.SUCCESSFUL_SHORTS -> SuccessfulShortsScreen(
                    videos = videos,
                    onReplicateSuccessPattern = {
                        viewModel.triggerAutoCorrectionPlan(it)
                        viewModel.navigateTo(DashboardPage.IDEAS)
                    }
                )

                DashboardPage.AI_STRATEGY -> AiStrategyAndLearningScreen(
                    channel = activeChannel,
                    learnings = learnings
                )

                DashboardPage.SETTINGS -> SettingsAndSecurityScreen(
                    activeAiProvider = viewModel.aiEngine.getActiveProviderStatus(),
                    isYouTubeOAuthReady = viewModel.youtubeEngine.isYouTubeOAuthConfigured()
                )

                DashboardPage.LOGS -> SystemLogsScreen(
                    logs = logs,
                    onClearLogs = { viewModel.clearSystemLogs() }
                )
            }
        }
    }
}
