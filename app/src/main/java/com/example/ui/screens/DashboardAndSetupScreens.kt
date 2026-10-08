package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.local.ChannelEntity
import com.example.data.local.DatabaseSeed
import com.example.data.local.ShortIdeaEntity
import com.example.data.local.ShortVideoEntity
import com.example.data.local.TrendTopicEntity
import com.example.ui.DashboardPage
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

@Composable
fun DashboardOverviewScreen(
    channel: ChannelEntity?,
    trends: List<TrendTopicEntity>,
    ideas: List<ShortIdeaEntity>,
    videos: List<ShortVideoEntity>,
    onToggleAutoMode: (Boolean) -> Unit,
    onRunFullAutopilot: () -> Unit,
    onNavigate: (DashboardPage) -> Unit
) {
    val publishedVideos = videos.filter { it.uploadStatus == "PUBLISHED" }
    val scheduledVideos = videos.filter { it.uploadStatus == "SCHEDULED" || it.uploadStatus == "QUEUED_APPROVAL" }
    val totalViews = publishedVideos.sumOf { it.views }
    val totalSubs = publishedVideos.sumOf { it.subscribersGained }
    val avgRetention = publishedVideos.map { it.retentionPercent }.average().takeIf { !it.isNaN() }?.toFloat() ?: 0f
    val bestShort = publishedVideos.maxByOrNull { it.views }
    val worstShort = publishedVideos.minByOrNull { it.views }
    val topTopic = trends.maxByOrNull { it.totalTrendScore }
    val worstTopic = trends.minByOrNull { it.totalTrendScore }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Hero Studio Command Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.img_autopilot_banner_1791473454887),
                        contentDescription = "ShortsPilot Command Center Banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(175.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(175.dp)
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color(0x550A0D12), Color(0xEE0A0D12))
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(Color(0xCC12161F))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(if (channel?.autoModeEnabled == true) EmeraldPass else AmberWarning)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (channel?.autoModeEnabled == true) "AUTO MODE: ON (AUTONOMOUS)" else "AUTO MODE: OFF (APPROVAL GATE)",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = if (channel?.autoModeEnabled == true) EmeraldPass else AmberWarning
                                )
                            }

                            Switch(
                                checked = channel?.autoModeEnabled == true,
                                onCheckedChange = onToggleAutoMode,
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = EmeraldPass,
                                    uncheckedThumbColor = AmberWarning,
                                    uncheckedTrackColor = ObsidianSurfaceHigh
                                ),
                                modifier = Modifier.testTag("auto_mode_switch")
                            )
                        }

                        Spacer(modifier = Modifier.height(28.dp))
                        Text(
                            text = channel?.name ?: "ShortsPilot Studio",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White
                        )
                        Text(
                            text = "Niche: ${channel?.niche ?: "AI"} • Audience: ${channel?.targetCountry ?: "Both"} (${channel?.primaryLanguage ?: "English"}) • ${channel?.uploadFrequency ?: "2/day"}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = ElectricCyan
                        )
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Active Optimization Strategy: ${channel?.activeStrategySummary.orEmpty()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onRunFullAutopilot,
                        colors = ButtonDefaults.buttonColors(containerColor = ShortsCrimson),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("run_autopilot_cycle_button")
                    ) {
                        Icon(Icons.Default.Bolt, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "RUN FULL AUTOPILOT CYCLE (RESEARCH → RENDER 9:16 → QC)",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }

        // Section 19: Primary KPI Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricKpiCard(
                        label = "TODAY'S SHORTS",
                        value = "${videos.size}",
                        subtext = "${scheduledVideos.size} Scheduled • ${publishedVideos.size} Live",
                        accentColor = ShortsCrimson,
                        modifier = Modifier.weight(1f)
                    )
                    MetricKpiCard(
                        label = "TOTAL VIEWS",
                        value = "%,d".format(totalViews),
                        subtext = "+$totalSubs Subscribers Gained",
                        accentColor = ElectricCyan,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricKpiCard(
                        label = "AVG RETENTION",
                        value = "${"%.1f".format(avgRetention)}%",
                        subtext = "Target > 82% for Shorts loop",
                        accentColor = EmeraldPass,
                        modifier = Modifier.weight(1f)
                    )
                    MetricKpiCard(
                        label = "USA vs INDIA SPLIT",
                        value = "54% / 41%",
                        subtext = "Dual-Localized Engine Active",
                        accentColor = StrategyViolet,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Retention & Viewed vs Swiped Telemetry Chart
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SHORTS RETENTION & 2s SWIPE SURVIVAL CURVE",
                            style = MaterialTheme.typography.labelLarge,
                            color = ElectricCyan
                        )
                        Text(
                            text = "0s → 28s",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    RetentionCurveCanvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.disclaimer_no_fake_virality),
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }

        // Best Short vs Worst Short & Top Topic vs Worst Topic (Section 19)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, EmeraldPass.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                        .clickable { onNavigate(DashboardPage.SUCCESSFUL_SHORTS) }
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = EmeraldPass, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("BEST SHORT", style = MaterialTheme.typography.labelMedium, color = EmeraldPass)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = bestShort?.title ?: "No published Short",
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${bestShort?.views ?: 0} views • ${bestShort?.retentionPercent ?: 0f}% APV",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary
                        )
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, CoralAlert.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                        .clickable { onNavigate(DashboardPage.FAILED_SHORTS) }
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TrendingDown, contentDescription = null, tint = CoralAlert, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("WORST SHORT", style = MaterialTheme.typography.labelMedium, color = CoralAlert)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = worstShort?.title ?: "None",
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${worstShort?.views ?: 0} views • ${worstShort?.failureCode ?: "Needs Diagnosis"}",
                            style = MaterialTheme.typography.labelSmall,
                            color = CoralAlert
                        )
                    }
                }
            }
        }

        // Top Topic vs Worst Topic
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("TREND INTELLIGENCE SNAPSHOT (USA + INDIA)", style = MaterialTheme.typography.labelLarge, color = StrategyViolet)
                    Text(
                        text = "Top Opportunity (${topTopic?.totalTrendScore ?: 95}/100): ${topTopic?.title ?: "Offline AI Models"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = EmeraldPass
                    )
                    Text(
                        text = "Saturated / Avoid (${worstTopic?.totalTrendScore ?: 44}/100): ${worstTopic?.title ?: "Generic Prompt Lists"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AmberWarning
                    )
                }
            }
        }

        // Quick Module Launcher Grid for all 14 Pages
        item {
            Text("AUTOPILOT MODULES (ALL 14 WORKSTATIONS)", style = MaterialTheme.typography.labelLarge, color = TextSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            val pages = DashboardPage.entries
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                for (rowPages in pages.chunked(2)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (p in rowPages) {
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = ObsidianSurfaceHigh),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onNavigate(p) }
                                    .testTag("nav_card_${p.name.lowercase()}")
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(p.group.uppercase(), style = MaterialTheme.typography.labelSmall, color = ElectricCyan)
                                        Text(p.title, style = MaterialTheme.typography.titleSmall, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun MetricKpiCard(
    label: String,
    value: String,
    subtext: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
        modifier = modifier.border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = accentColor)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(subtext, style = MaterialTheme.typography.bodySmall, color = TextSecondary, maxLines = 1)
        }
    }
}

@Composable
fun RetentionCurveCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Draw horizontal reference grid
        for (i in 1..3) {
            val y = h * (i / 4f)
            drawLine(
                color = Color(0xFF263042),
                start = Offset(0f, y),
                end = Offset(w, y),
                strokeWidth = 1.5f
            )
        }

        // Winning 26s Short Retention Curve (Emerald)
        val winPoints = listOf(1.0f, 0.94f, 0.91f, 0.89f, 0.87f, 0.86f, 0.92f)
        val winPath = Path()
        winPoints.forEachIndexed { idx, pct ->
            val x = (w / (winPoints.size - 1)) * idx
            val y = h * (1f - pct * 0.88f)
            if (idx == 0) winPath.moveTo(x, y) else winPath.lineTo(x, y)
        }
        drawPath(winPath, color = Color(0xFF00E676), style = Stroke(width = 5f))

        // Failed 58s Short Retention Curve (Coral Red drop-off at 2s)
        val failPoints = listOf(1.0f, 0.42f, 0.35f, 0.31f, 0.26f, 0.22f, 0.18f)
        val failPath = Path()
        failPoints.forEachIndexed { idx, pct ->
            val x = (w / (failPoints.size - 1)) * idx
            val y = h * (1f - pct * 0.88f)
            if (idx == 0) failPath.moveTo(x, y) else failPath.lineTo(x, y)
        }
        drawPath(failPath, color = Color(0xFFFF5252), style = Stroke(width = 4f))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChannelsWizardScreen(
    channels: List<ChannelEntity>,
    selectedChannelId: Long,
    onSelectChannel: (Long) -> Unit,
    onCreateChannel: (String, String, String, String, String, String, String, Int, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedNiche by remember { mutableStateOf(DatabaseSeed.SUPPORTED_NICHES.first()) }
    var selectedCountry by remember { mutableStateOf("Both") }
    var selectedLanguage by remember { mutableStateOf("English") }
    var selectedFrequency by remember { mutableStateOf("2/day") }
    var uploadTime by remember { mutableStateOf("08:30 EST / 18:00 IST") }
    var targetDuration by remember { mutableFloatStateOf(26f) }
    var selectedStyle by remember { mutableStateOf(DatabaseSeed.CONTENT_STYLES.first()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("ACTIVE CHANNELS / PROJECTS", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Text(
                "Switch between multiple automated YouTube Shorts channels or launch a new project below.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }

        items(channels, key = { it.id }) { ch ->
            val isSelected = ch.id == selectedChannelId
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = if (isSelected) ObsidianSurfaceHigh else ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) ElectricCyan else BorderSubtle,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onSelectChannel(ch.id) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(ch.name, style = MaterialTheme.typography.titleLarge, color = Color.White)
                        Text(
                            text = if (isSelected) "ACTIVE WORKSPACE" else "TAP TO SWITCH",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) ElectricCyan else TextSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(ch.description, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Niche: ${ch.niche} • Target: ${ch.targetCountry} • Lang: ${ch.primaryLanguage} • ${ch.uploadFrequency} • ${ch.targetDurationSec}s",
                        style = MaterialTheme.typography.labelMedium,
                        color = EmeraldPass
                    )
                }
            }
        }

        // Section 2: Full Channel Setup Wizard
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("NEW CHANNEL SETUP WIZARD", style = MaterialTheme.typography.titleLarge, color = ShortsCrimson)

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Channel Name") },
                        placeholder = { Text("e.g., CyberPulse India & US") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("wizard_channel_name_input")
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Channel Description") },
                        placeholder = { Text("Daily 26s Shorts on...") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Select Niche (${DatabaseSeed.SUPPORTED_NICHES.size} Supported)", style = MaterialTheme.typography.labelLarge, color = ElectricCyan)
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        DatabaseSeed.SUPPORTED_NICHES.forEach { niche ->
                            FilterChip(
                                selected = selectedNiche == niche,
                                onClick = { selectedNiche = niche },
                                label = { Text(niche) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = ShortsCrimson,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Text("Target Country Audience (USA / India / Both)", style = MaterialTheme.typography.labelLarge, color = ElectricCyan)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DatabaseSeed.TARGET_COUNTRIES.forEach { country ->
                            FilterChip(
                                selected = selectedCountry == country,
                                onClick = { selectedCountry = country },
                                label = { Text(country) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = UsaBlue,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Text("Primary Language", style = MaterialTheme.typography.labelLarge, color = ElectricCyan)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DatabaseSeed.LANGUAGES.forEach { lang ->
                            FilterChip(
                                selected = selectedLanguage == lang,
                                onClick = { selectedLanguage = lang },
                                label = { Text(lang) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = IndiaSaffron,
                                    selectedLabelColor = Color.Black
                                )
                            )
                        }
                    }

                    Text("Upload Frequency & Preferred Upload Time", style = MaterialTheme.typography.labelLarge, color = ElectricCyan)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DatabaseSeed.FREQUENCIES.forEach { freq ->
                            FilterChip(
                                selected = selectedFrequency == freq,
                                onClick = { selectedFrequency = freq },
                                label = { Text(freq) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = uploadTime,
                        onValueChange = { uploadTime = it },
                        label = { Text("Preferred Upload Time Window") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Shorts Target Duration: ${targetDuration.toInt()} seconds (9:16 Vertical)",
                        style = MaterialTheme.typography.labelLarge,
                        color = EmeraldPass
                    )
                    Slider(
                        value = targetDuration,
                        onValueChange = { targetDuration = it },
                        valueRange = 15f..58f
                    )

                    Text("Content Style", style = MaterialTheme.typography.labelLarge, color = ElectricCyan)
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        DatabaseSeed.CONTENT_STYLES.forEach { style ->
                            FilterChip(
                                selected = selectedStyle == style,
                                onClick = { selectedStyle = style },
                                label = { Text(style) }
                            )
                        }
                    }

                    Button(
                        onClick = {
                            onCreateChannel(
                                name.ifBlank { "$selectedNiche Autopilot" },
                                description,
                                selectedNiche,
                                selectedCountry,
                                selectedLanguage,
                                selectedFrequency,
                                uploadTime,
                                targetDuration.toInt(),
                                selectedStyle
                            )
                            name = ""
                            description = ""
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPass, contentColor = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("create_channel_submit_button")
                    ) {
                        Icon(Icons.Default.AddCircle, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("CREATE & INITIALIZE CHANNEL AUTOPILOT", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NicheSettingsScreen(
    channel: ChannelEntity?,
    onUpdateChannel: (ChannelEntity) -> Unit
) {
    if (channel == null) return
    var niche by remember(channel.id) { mutableStateOf(channel.niche) }
    var country by remember(channel.id) { mutableStateOf(channel.targetCountry) }
    var language by remember(channel.id) { mutableStateOf(channel.primaryLanguage) }
    var duration by remember(channel.id) { mutableFloatStateOf(channel.targetDurationSec.toFloat()) }
    var frequency by remember(channel.id) { mutableStateOf(channel.uploadFrequency) }
    var style by remember(channel.id) { mutableStateOf(channel.contentStyle) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("NICHE & USA + INDIA ENGINE SETTINGS", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Text(
                "Configure how '${channel.name}' researches topics and localizes hooks across US and Indian viewers.",
                style = MaterialTheme.typography.bodyMedium,
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
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("ACTIVE NICHE SELECTION", style = MaterialTheme.typography.labelLarge, color = ShortsCrimson)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        DatabaseSeed.SUPPORTED_NICHES.forEach { item ->
                            FilterChip(
                                selected = niche == item,
                                onClick = { niche = item },
                                label = { Text(item) }
                            )
                        }
                    }

                    Text("TARGET AUDIENCE INTELLIGENCE", style = MaterialTheme.typography.labelLarge, color = ElectricCyan)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DatabaseSeed.TARGET_COUNTRIES.forEach { c ->
                            FilterChip(
                                selected = country == c,
                                onClick = { country = c },
                                label = { Text(c) }
                            )
                        }
                    }

                    Text(
                        text = when (country) {
                            "USA" -> "USA Intelligence Active: Uses US Google Trends, USD ($) pricing, Silicon Valley/FTC references, and fast American English pacing."
                            "India" -> "India Intelligence Active: Uses India Trends, INR (₹)/Lakh/Crore metrics, UPI/Bengaluru/Jio references, and Hindi/Hinglish/Indian English phrasing."
                            else -> "Dual US + India Intelligence Active: Automatically decides per topic whether to build (A) One Universal Short with $/₹ parity or (B) Two Localized US & India versions. Never blindly translates."
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = EmeraldPass
                    )

                    Text("PRIMARY NARRATION LANGUAGE", style = MaterialTheme.typography.labelLarge, color = ElectricCyan)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DatabaseSeed.LANGUAGES.forEach { l ->
                            FilterChip(
                                selected = language == l,
                                onClick = { language = l },
                                label = { Text(l) }
                            )
                        }
                    }

                    Text("TARGET SHORTS DURATION: ${duration.toInt()} SECONDS", style = MaterialTheme.typography.labelLarge, color = EmeraldPass)
                    Slider(
                        value = duration,
                        onValueChange = { duration = it },
                        valueRange = 16f..58f
                    )

                    Button(
                        onClick = {
                            onUpdateChannel(
                                channel.copy(
                                    niche = niche,
                                    targetCountry = country,
                                    primaryLanguage = language,
                                    targetDurationSec = duration.toInt(),
                                    uploadFrequency = frequency,
                                    contentStyle = style
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ShortsCrimson),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_niche_settings_button")
                    ) {
                        Text("SAVE NICHE & AUDIENCE STRATEGY", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}
