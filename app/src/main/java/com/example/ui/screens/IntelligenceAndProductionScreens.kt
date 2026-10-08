package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.local.ChannelEntity
import com.example.data.local.ScriptEntity
import com.example.data.local.ShortIdeaEntity
import com.example.data.local.ShortVideoEntity
import com.example.data.local.TrendTopicEntity
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
fun TrendResearchScreen(
    channel: ChannelEntity?,
    trends: List<TrendTopicEntity>,
    onScanLiveTrends: () -> Unit,
    onGenerateIdeasFromTrends: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("TREND RESEARCH ENGINE (USA + INDIA)", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Text(
                "Trend Score = Recency (20) + Search Interest (20) + YouTube Interest (20) + Competition (15) + Relevance (15) + Content Gap (10)",
                style = MaterialTheme.typography.labelSmall,
                color = ElectricCyan
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onScanLiveTrends,
                    colors = ButtonDefaults.buttonColors(containerColor = ShortsCrimson),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("scan_live_trends_button")
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("SCAN LIVE TRENDS", style = MaterialTheme.typography.labelMedium)
                }
                Button(
                    onClick = onGenerateIdeasFromTrends,
                    colors = ButtonDefaults.buttonColors(containerColor = StrategyViolet),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("generate_ideas_from_trends_button")
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("GENERATE IDEAS", style = MaterialTheme.typography.labelMedium)
                }
            }
        }

        items(trends, key = { it.id }) { t ->
            val scoreColor = when {
                t.totalTrendScore >= 88 -> EmeraldPass
                t.totalTrendScore >= 75 -> ElectricCyan
                else -> AmberWarning
            }
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
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${t.categoryStatus.uppercase()} • ${t.source}",
                            style = MaterialTheme.typography.labelSmall,
                            color = scoreColor
                        )
                        Text(
                            text = "TREND SCORE: ${t.totalTrendScore}/100",
                            style = MaterialTheme.typography.labelLarge,
                            color = scoreColor
                        )
                    }
                    Text(t.title, style = MaterialTheme.typography.titleMedium, color = Color.White)

                    // 6-Factor Breakdown Bar
                    Text(
                        text = "Recency ${t.recencyScore}/20 | Search ${t.searchInterestScore}/20 | YT ${t.youtubeInterestScore}/20 | Low-Comp ${t.competitionScore}/15 | Rel ${t.audienceRelevanceScore}/15 | Gap ${t.contentGapScore}/10",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )

                    // USA vs India Localization Intelligence
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(ObsidianSurfaceHigh)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("🇺🇸 ${t.usaAngle}", style = MaterialTheme.typography.bodySmall, color = UsaBlue)
                        Text("🇮🇳 ${t.indiaAngle}", style = MaterialTheme.typography.bodySmall, color = IndiaSaffron)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun IdeasGeneratorScreen(
    ideas: List<ShortIdeaEntity>,
    onGenerateMoreIdeas: () -> Unit,
    onGenerateScriptForIdea: (ShortIdeaEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("SHORTS IDEA GENERATOR", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                    Text(
                        "Prioritized by: HIGH INTEREST + LOW/MEDIUM COMPETITION + STRONG HOOK",
                        style = MaterialTheme.typography.labelSmall,
                        color = EmeraldPass
                    )
                }
                Button(
                    onClick = onGenerateMoreIdeas,
                    colors = ButtonDefaults.buttonColors(containerColor = ShortsCrimson),
                    modifier = Modifier.testTag("generate_batch_ideas_button")
                ) {
                    Text("+ GENERATE BATCH", style = MaterialTheme.typography.labelMedium)
                }
            }
        }

        items(ideas, key = { it.id }) { idea ->
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
                            text = "PRIORITY ${idea.priorityScore}/100 • ${idea.estimatedInterest} INTEREST • ${idea.competitionLevel} COMP",
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldPass
                        )
                        Text(
                            text = "${idea.suggestedDurationSec}s • ${idea.targetAudience}",
                            style = MaterialTheme.typography.labelSmall,
                            color = ElectricCyan
                        )
                    }

                    Text(idea.topic, style = MaterialTheme.typography.titleMedium, color = Color.White)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(ObsidianSurfaceHigh)
                            .padding(10.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("0–2s HOOK:", style = MaterialTheme.typography.labelSmall, color = ShortsCrimson)
                            Text("\"${idea.hook}\"", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                        }
                    }

                    Text("Localization Strategy: ${idea.localizationStrategy}", style = MaterialTheme.typography.bodySmall, color = ElectricCyan)
                    Text("Why It May Work: ${idea.whyItMayWork}", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text("Structure: ${idea.storyStructure}", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text("Visuals: ${idea.visualRequirements}", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text(
                        text = "Title Options: ${idea.titleOptions.replace("|", "  •  ")}",
                        style = MaterialTheme.typography.labelSmall,
                        color = AmberWarning
                    )

                    Button(
                        onClick = { onGenerateScriptForIdea(idea) },
                        colors = ButtonDefaults.buttonColors(containerColor = StrategyViolet),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("build_script_idea_${idea.id}")
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("GENERATE 3-HOOK VIRAL SCRIPT & SCORE", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun ScriptsStudioScreen(
    scripts: List<ScriptEntity>,
    onSelectHookVariant: (ScriptEntity, Int) -> Unit,
    onSpeakScript: (ScriptEntity) -> Unit,
    onStopSpeaking: () -> Unit,
    onRenderShortMp4: (ScriptEntity) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("VIRAL SCRIPT ENGINE & QUALITY GATE", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Text(
                "0–2s Strong Hook (3 variants) → 2–5s Curiosity → 5–20s Value Body → Final Payoff/Loop. Auto-rejects weak scripts (<72/100).",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary
            )
        }

        items(scripts, key = { it.id }) { script ->
            val borderColor = if (script.isRejected) CoralAlert else EmeraldPass
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, borderColor.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (script.isRejected) "REJECTED BY QUALITY GATE" else "APPROVED • SCORE ${script.overallScore}/100",
                            style = MaterialTheme.typography.labelLarge,
                            color = borderColor
                        )
                        Text(
                            text = "${script.suggestedDurationSec}s • ${script.language}",
                            style = MaterialTheme.typography.labelSmall,
                            color = ElectricCyan
                        )
                    }

                    Text(script.title, style = MaterialTheme.typography.titleLarge, color = Color.White)

                    if (script.isRejected) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(CoralAlert.copy(alpha = 0.15f))
                                .padding(12.dp)
                        ) {
                            Text(script.rejectionReason, style = MaterialTheme.typography.bodySmall, color = CoralAlert)
                        }
                    }

                    // 3 Alternative Hooks Selector (Section 7 & 16 A/B Testing)
                    Text("SELECT 0–2s HOOK VARIANT (A/B TEST):", style = MaterialTheme.typography.labelSmall, color = ElectricCyan)
                    listOf(
                        1 to script.hookAlt1,
                        2 to script.hookAlt2,
                        3 to script.hookAlt3
                    ).forEach { (idx, hookText) ->
                        val active = script.selectedHookIndex == idx
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (active) ObsidianSurfaceHigh else Color.Transparent)
                                .border(
                                    1.dp,
                                    if (active) ElectricCyan else BorderSubtle,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onSelectHookVariant(script, idx) }
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "H$idx",
                                style = MaterialTheme.typography.labelMedium,
                                color = if (active) ElectricCyan else TextSecondary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = hookText,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }
                    }

                    // Time-Boxed Viral Structure
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(ObsidianSurfaceHigh)
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("0–2s HOOK: ${script.hook0to2Sec}", style = MaterialTheme.typography.bodyMedium, color = ShortsCrimson)
                        Text("2–5s CURIOSITY: ${script.curiosity2to5Sec}", style = MaterialTheme.typography.bodyMedium, color = ElectricCyan)
                        Text("5–20s BODY: ${script.body5to20Sec}", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                        Text("FINAL PAYOFF/LOOP: ${script.payoffFinalSec}", style = MaterialTheme.typography.bodyMedium, color = EmeraldPass)
                        Text("CTA: ${script.ctaLine}", style = MaterialTheme.typography.labelSmall, color = AmberWarning)
                    }

                    // 7-Metric Scorecard
                    Text(
                        text = "Hook ${script.hookStrengthScore} | Curiosity ${script.curiosityScore} | Retention ${script.retentionPotentialScore} | Clarity ${script.clarityScore} | Originality ${script.originalityScore} | Emotion ${script.emotionalImpactScore} | Share ${script.shareabilityScore}",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )

                    Text(
                        text = script.probabilityStatement,
                        style = MaterialTheme.typography.bodySmall,
                        color = EmeraldPass
                    )

                    if (!script.isRejected) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            OutlinedButton(
                                onClick = { onSpeakScript(script) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = null, tint = ElectricCyan)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("TTS VOICE", style = MaterialTheme.typography.labelMedium, color = ElectricCyan)
                            }
                            Button(
                                onClick = { onRenderShortMp4(script) },
                                colors = ButtonDefaults.buttonColors(containerColor = ShortsCrimson),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("render_mp4_script_${script.id}")
                            ) {
                                Icon(Icons.Default.MovieCreation, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("RENDER 9:16 MP4", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun VideoGeneratorScreen(
    channel: ChannelEntity?,
    scripts: List<ScriptEntity>,
    videos: List<ShortVideoEntity>,
    onRenderPreviewBitmap: (ScriptEntity, String, Int) -> android.graphics.Bitmap,
    onRenderFullShort: (ScriptEntity) -> Unit,
    onSpeakScript: (ScriptEntity) -> Unit,
    onStopAudio: () -> Unit,
    onShareMp4: (ShortVideoEntity) -> Unit
) {
    val approvedScripts = scripts.filter { !it.isRejected }
    val activeScript = approvedScripts.firstOrNull() ?: scripts.firstOrNull()
    var selectedScene by remember { mutableIntStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("9:16 VERTICAL VIDEO & CAPTION STUDIO", style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Text(
                "Output: 1080x1920 (9:16) H.264 MP4 • Neural TTS (EN/HI/Hinglish) • Safe-Margin Word Captions • 12-Point QC Gate",
                style = MaterialTheme.typography.labelSmall,
                color = ElectricCyan
            )
        }

        if (activeScript != null) {
            item {
                val bmp = remember(activeScript.id, selectedScene, activeScript.selectedHookIndex) {
                    onRenderPreviewBitmap(activeScript, channel?.name ?: "ShortsPilot", selectedScene)
                }
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "LIVE 1080x1920 (9:16) CANVAS PREVIEW",
                            style = MaterialTheme.typography.labelLarge,
                            color = EmeraldPass
                        )

                        // 9:16 Vertical Preview Frame
                        Image(
                            bitmap = bmp.asImageBitmap(),
                            contentDescription = "9:16 Vertical Short Scene Preview",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(340.dp)
                                .aspectRatio(9f / 16f)
                                .clip(RoundedCornerShape(16.dp))
                                .border(2.dp, ElectricCyan, RoundedCornerShape(16.dp))
                        )

                        // Scene Scrubber
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            listOf(
                                0 to "0-2s Hook",
                                1 to "2-5s Curiosity",
                                2 to "5-20s Body",
                                3 to "Payoff/Loop"
                            ).forEach { (idx, label) ->
                                FilterChip(
                                    selected = selectedScene == idx,
                                    onClick = { selectedScene = idx },
                                    label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            OutlinedButton(
                                onClick = { onSpeakScript(activeScript) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("PREVIEW TTS", style = MaterialTheme.typography.labelMedium)
                            }
                            OutlinedButton(
                                onClick = onStopAudio,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Stop, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("STOP AUDIO", style = MaterialTheme.typography.labelMedium)
                            }
                        }

                        Button(
                            onClick = { onRenderFullShort(activeScript) },
                            colors = ButtonDefaults.buttonColors(containerColor = ShortsCrimson),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("encode_1080x1920_mp4_button")
                        ) {
                            Icon(Icons.Default.MovieCreation, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("RENDER 1080x1920 H.264 MP4 + RUN 12-PT QC", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }

        // Rendered Videos & 12-Point QC Inspection Reports
        items(videos, key = { it.id }) { v ->
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
                        Text(v.resolution, style = MaterialTheme.typography.labelMedium, color = ElectricCyan)
                        Text(
                            text = if (v.qcPassed) "12/12 QC PASSED" else "QC FAILED",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (v.qcPassed) EmeraldPass else CoralAlert
                        )
                    }
                    Text(v.title, style = MaterialTheme.typography.titleMedium, color = Color.White)
                    Text("Voice: ${v.voiceUsed} • Audio: ${v.bgMusicTrack}", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    Text("MP4 Path: ${v.filePath}", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    Text("QC Gate Report: ${v.qcChecklistJson}", style = MaterialTheme.typography.bodySmall, color = EmeraldPass)

                    // SRT Subtitle Preview
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(ObsidianSurfaceHigh)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "SRT Subtitles:\n${v.srtCaptions.take(260)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }

                    OutlinedButton(
                        onClick = { onShareMp4(v) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = ElectricCyan)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("EXPORT / SHARE 9:16 MP4 FILE", style = MaterialTheme.typography.labelMedium, color = ElectricCyan)
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
