package com.example.data.local

object DatabaseSeed {

    val SUPPORTED_NICHES = listOf(
        "Technology",
        "AI",
        "Cybersecurity",
        "Gaming",
        "Cars",
        "Facts",
        "Science",
        "Finance education",
        "Motivation",
        "History",
        "Space",
        "Interesting discoveries",
        "Entertainment"
    )

    val TARGET_COUNTRIES = listOf("USA", "India", "Both")
    val LANGUAGES = listOf("English", "Hindi", "Hinglish")
    val FREQUENCIES = listOf("1/day", "2/day", "3/day")
    val CONTENT_STYLES = listOf(
        "Fast-Paced Curiosity + Data Reveal",
        "Myth-Busting Counter-Intuitive Hook",
        "Visual Case Study & Micro-Story",
        "Rapid-Fire 3-Point Breakdown"
    )

    suspend fun ensureSeeded(dao: ShortsPilotDao) {
        val existing = dao.getChannelsOnce()
        if (existing.isNotEmpty()) return

        val ch1Id = dao.insertChannel(
            ChannelEntity(
                name = "QuantumPulse AI",
                description = "Daily high-retention Shorts on AI breakthroughs, open-source models, and consumer tech shifts for US & Indian builders.",
                niche = "AI",
                targetCountry = "Both",
                primaryLanguage = "English",
                uploadFrequency = "2/day",
                preferredUploadTime = "08:30 EST / 18:00 IST",
                targetDurationSec = 26,
                contentStyle = "Fast-Paced Curiosity + Data Reveal",
                autoModeEnabled = true,
                youtubeChannelId = "UC_QUANTUMPULSE_SHORTS",
                oauthConnected = false,
                activeStrategySummary = "24–28s Shorts with 0.8s visual hook cut; dual-localized US ($/Silicon Valley) & India (₹/UPI/Bengaluru) examples."
            )
        )

        dao.insertChannel(
            ChannelEntity(
                name = "BharatFin & Tech",
                description = "Hinglish & English finance + cybersecurity literacy Shorts tailored for India & diaspora viewers.",
                niche = "Finance education",
                targetCountry = "India",
                primaryLanguage = "Hinglish",
                uploadFrequency = "1/day",
                preferredUploadTime = "20:00 IST",
                targetDurationSec = 28,
                contentStyle = "Myth-Busting Counter-Intuitive Hook",
                autoModeEnabled = false,
                youtubeChannelId = "UC_BHARATFIN_SHORTS",
                oauthConnected = false,
                activeStrategySummary = "Hinglish curiosity hooks referencing UPI, RBI & real purchasing power; avoid slow definitions."
            )
        )

        // Seed Trend Topics for Channel 1
        val trends = listOf(
            TrendTopicEntity(
                channelId = ch1Id,
                title = "On-Device Local AI Models Running on 4GB RAM Phones Without Internet",
                niche = "AI",
                targetCountry = "Both",
                source = "YouTube Data API + Reddit r/LocalLLaMA",
                categoryStatus = "Content Gap",
                recencyScore = 19,
                searchInterestScore = 18,
                youtubeInterestScore = 19,
                competitionScore = 14,
                audienceRelevanceScore = 15,
                contentGapScore = 10,
                totalTrendScore = 95,
                usaAngle = "Focus on iPhone / Pixel privacy & zero monthly $20 cloud subscription fees.",
                indiaAngle = "Focus on running AI offline on budget ₹12,000 Android phones with zero mobile data cost."
            ),
            TrendTopicEntity(
                channelId = ch1Id,
                title = "Why AI Voice Cloning Scams Are Bypassing Two-Factor Bank Calls",
                niche = "Cybersecurity",
                targetCountry = "Both",
                source = "Google News RSS (US & IN)",
                categoryStatus = "Rising",
                recencyScore = 18,
                searchInterestScore = 19,
                youtubeInterestScore = 18,
                competitionScore = 12,
                audienceRelevanceScore = 15,
                contentGapScore = 9,
                totalTrendScore = 91,
                usaAngle = "FTC alert on 3-second family voice clones targeting US Zelle/wire transfers.",
                indiaAngle = "UPI & WhatsApp voice-note impersonation scams and the 1 family codeword defense."
            ),
            TrendTopicEntity(
                channelId = ch1Id,
                title = "Open-Source Coding Agents vs $200/Month Enterprise AI Subscriptions",
                niche = "AI",
                targetCountry = "USA",
                source = "Reddit Hot + HackerNews RSS",
                categoryStatus = "Emerging",
                recencyScore = 17,
                searchInterestScore = 17,
                youtubeInterestScore = 17,
                competitionScore = 13,
                audienceRelevanceScore = 14,
                contentGapScore = 9,
                totalTrendScore = 87,
                usaAngle = "US indie devs replacing $200/mo SaaS stacks with free open-weight models.",
                indiaAngle = "College students & freelancers in Bengaluru/Pune building full-stack apps at ₹0 API cost."
            ),
            TrendTopicEntity(
                channelId = ch1Id,
                title = "The Hidden GPU Power Inside Old Gaming Consoles Used for AI",
                niche = "Technology",
                targetCountry = "Both",
                source = "Wikipedia Current + YouTube Trends",
                categoryStatus = "Popular",
                recencyScore = 16,
                searchInterestScore = 16,
                youtubeInterestScore = 18,
                competitionScore = 11,
                audienceRelevanceScore = 13,
                contentGapScore = 8,
                totalTrendScore = 82,
                usaAngle = "Repurposing used $150 hardware clusters in home labs.",
                indiaAngle = "Jugaad compute clusters built from second-hand gaming GPUs."
            ),
            TrendTopicEntity(
                channelId = ch1Id,
                title = "Generic ChatGPT Prompt Lists for Beginners",
                niche = "AI",
                targetCountry = "Both",
                source = "YouTube Data API",
                categoryStatus = "Repeated",
                recencyScore = 9,
                searchInterestScore = 12,
                youtubeInterestScore = 10,
                competitionScore = 3,
                audienceRelevanceScore = 8,
                contentGapScore = 2,
                totalTrendScore = 44,
                usaAngle = "Oversaturated topic; high swipe-away rate in US feed.",
                indiaAngle = "Oversaturated topic; viewers skip generic prompt listicles."
            )
        )
        dao.insertTrends(trends)

        // Seed Content Ideas
        val ideas = listOf(
            ShortIdeaEntity(
                channelId = ch1Id,
                trendId = 1,
                topic = "Running a Full AI Assistant Offline in Airplane Mode",
                hook = "Turn on Airplane Mode right now—this AI inside your phone still answers everything.",
                targetAudience = "Universal (US+IN)",
                localizationStrategy = "Universal visual proof (Airplane mode toggle) + dual currency callout ($0 / ₹0 cloud fee).",
                estimatedInterest = "VERY HIGH",
                competitionLevel = "LOW",
                whyItMayWork = "Strong visual contradiction in the first 1.2 seconds (Airplane mode ON + live AI generation) stops the swipe reflex.",
                suggestedDurationSec = 26,
                storyStructure = "0-2s: Airplane Mode shock hook -> 2-5s: Why cloud AI tracks you -> 5-20s: 3-step quantized model demo -> 20-26s: Loop payoff",
                visualRequirements = "Split-screen 9:16 phone telemetry, RAM usage bar animation, bold yellow/cyan captions.",
                cta = "Comment 'LOCAL' and check the pinned benchmark list.",
                titleOptions = "This AI Runs With ZERO Internet! #Shorts|Why Pay $20/Mo When Your Phone Does This Offline? #Shorts|Turn On Airplane Mode and Try This AI Trick #Shorts",
                priorityScore = 96,
                status = "PUBLISHED"
            ),
            ShortIdeaEntity(
                channelId = ch1Id,
                trendId = 2,
                topic = "3 Seconds of Your Voice Is All Scammers Need (And How to Block It)",
                hook = "If an unknown number calls and asks 'Can you hear me?'—do NOT say yes.",
                targetAudience = "Localized Dual (US & IN)",
                localizationStrategy = "Generate two localized hooks: US version references bank wire/Zelle; India version references UPI/WhatsApp family call.",
                estimatedInterest = "VERY HIGH",
                competitionLevel = "LOW",
                whyItMayWork = "Immediate personal utility and fear-curiosity gap in 1.5 seconds; high shareability in family groups.",
                suggestedDurationSec = 25,
                storyStructure = "0-2s: Warning hook -> 2-5s: Audio waveform clone demo -> 5-20s: Acoustic watermark & family safe-word fix -> 20-25s: Share CTA",
                visualRequirements = "Incoming call red pulse animation, live voice spectrogram comparison.",
                cta = "Send this to one family member to set your safe word today.",
                titleOptions = "Never Answer 'Can You Hear Me?' On Unknown Calls #Shorts|How 3 Seconds of Audio Clones Any Voice #Shorts|The 1 Word That Stops AI Voice Scams #Shorts",
                priorityScore = 93,
                status = "READY"
            ),
            ShortIdeaEntity(
                channelId = ch1Id,
                trendId = 3,
                topic = "Why 90% of AI Wrappers Die in 30 Days (Architecture Breakdown)",
                hook = "This $10,000 AI startup was replaced by 14 lines of free Python code.",
                targetAudience = "USA",
                localizationStrategy = "US SaaS pricing & Silicon Valley indie hacker terminology.",
                estimatedInterest = "HIGH",
                competitionLevel = "MEDIUM",
                whyItMayWork = "Specific dollar vs. line-of-code contrast hooks developers and tech enthusiasts immediately.",
                suggestedDurationSec = 28,
                storyStructure = "0-2s: $10k vs 14 lines hook -> 2-5s: Wrapper architecture exposed -> 5-22s: OpenRouter + SQLite stack reveal -> 22-28s: Payoff",
                visualRequirements = "Code diff overlay, cost comparison bar chart.",
                cta = "Subscribe for real system architecture breakdowns.",
                titleOptions = "14 Lines of Python Replaced a $10K AI App #Shorts|Stop Paying for Thin AI Wrappers #Shorts|The Free Stack Behind Top AI Tools #Shorts",
                priorityScore = 89,
                status = "READY"
            ),
            ShortIdeaEntity(
                channelId = ch1Id,
                trendId = 4,
                topic = "India's Sovereign AI Compute Push vs Global GPU Shortage",
                hook = "India just found a smarter way to train AI without buying $40,000 chips.",
                targetAudience = "India",
                localizationStrategy = "Localized Hinglish/English terminology referencing Indian startups, Bhashini voice datasets, and rupee efficiency.",
                estimatedInterest = "HIGH",
                competitionLevel = "LOW",
                whyItMayWork = "High national pride + technical curiosity for Indian tech audience.",
                suggestedDurationSec = 27,
                storyStructure = "0-2s: $40k chip contrast hook -> 2-5s: Small language model breakthrough -> 5-21s: 22-language Indic voice efficiency -> 21-27s: Loop question",
                visualRequirements = "India compute map nodes, token-cost per Lakh chart.",
                cta = "Which Indian language should we benchmark next? Comment below.",
                titleOptions = "How India Is Building AI At 1/10th The Cost #Shorts|Small Models vs Giant GPUs: India's AI Strategy #Shorts|Why Indic AI Models Are Surprising Silicon Valley #Shorts",
                priorityScore = 88,
                status = "READY"
            ),
            ShortIdeaEntity(
                channelId = ch1Id,
                trendId = 5,
                topic = "How Small Quantization Shrinks 70B AI Models by 75%",
                hook = "You can shrink a 140-gigabyte AI brain onto a USB stick without making it dumb.",
                targetAudience = "Universal (US+IN)",
                localizationStrategy = "Visual size analogy (server rack vs pocket drive) works across both US and India.",
                estimatedInterest = "HIGH",
                competitionLevel = "LOW",
                whyItMayWork = "Visual compression metaphor makes complex 4-bit quantization effortless to grasp.",
                suggestedDurationSec = 24,
                storyStructure = "0-2s: 140GB to USB hook -> 2-5s: What is 4-bit quantization -> 5-19s: Accuracy vs RAM chart -> 19-24s: Seamless loop",
                visualRequirements = "3D block compression animation, FPS token counter.",
                cta = "Save this Short for your next local AI setup.",
                titleOptions = "Shrinking a 140GB AI Onto a USB Stick #Shorts|How 4-Bit Quantization Actually Works #Shorts|Run Giant AI Models on Cheap Hardware #Shorts",
                priorityScore = 85,
                status = "READY"
            )
        )
        dao.insertIdeas(ideas)

        // Seed Scripts (1 Strong Approved + 1 Auto-Rejected weak script for transparency)
        val script1Id = dao.insertScript(
            ScriptEntity(
                channelId = ch1Id,
                ideaId = 1,
                title = "This AI Runs With ZERO Internet! #Shorts",
                targetAudience = "Universal (US+IN)",
                language = "English",
                hookAlt1 = "Turn on Airplane Mode right now—this AI inside your phone still answers everything.",
                hookAlt2 = "Most people think AI needs a cloud server, but your phone can run it offline.",
                hookAlt3 = "Stop paying $20 every month for AI when this open-source model runs offline.",
                selectedHookIndex = 1,
                hook0to2Sec = "Turn on Airplane Mode right now—this AI inside your phone still answers everything.",
                curiosity2to5Sec = "Every cloud prompt you send is logged on a remote server, costing $20 or ₹1,700 a month.",
                body5to20Sec = "By using 4-bit weight quantization, a 3-billion parameter open-source model compresses into just 1.8 gigabytes of RAM. Your phone's neural processor calculates 35 tokens per second completely offline—zero latency, zero subscription, and zero data leaves your device.",
                payoffFinalSec = "And the craziest part? It uses less battery than scrolling for 2 minutes—which is why you should...",
                ctaLine = "Comment 'LOCAL' for the free open-source model list.",
                descriptionText = "How 4-bit quantized open-source AI models run 100% offline on mobile NPU hardware without cloud subscriptions.\n\nBased on channel telemetry, 26-second technical breakdowns with a 0-2s visual contradiction hook achieve higher viewed-vs-swiped retention.",
                hashtags = "#Shorts #AI #TechFacts #OpenSource #LocalAI #MobileTech",
                suggestedDurationSec = 26,
                voiceEngine = "Android Neural TTS (en-US)",
                hookStrengthScore = 94,
                curiosityScore = 91,
                retentionPotentialScore = 92,
                clarityScore = 95,
                originalityScore = 90,
                emotionalImpactScore = 86,
                shareabilityScore = 91,
                overallScore = 91,
                isRejected = false,
                rejectionReason = "",
                probabilityStatement = "Based on current channel data, this version has a +21.4% stronger probability of passing the 2-second swipe gate than standard question hooks."
            )
        )

        dao.insertScript(
            ScriptEntity(
                channelId = ch1Id,
                ideaId = 2,
                title = "Welcome Back To My Channel AI News #Shorts",
                targetAudience = "USA",
                language = "English",
                hookAlt1 = "Hey guys welcome back to the channel, today we will talk about AI voice tools.",
                hookAlt2 = "In this video I want to explain what text to speech is.",
                hookAlt3 = "Hello everyone, let's look at AI voices.",
                selectedHookIndex = 1,
                hook0to2Sec = "Hey guys welcome back to the channel, today we will talk about AI voice tools.",
                curiosity2to5Sec = "Before we start make sure to like and subscribe to our channel.",
                body5to20Sec = "AI voices are becoming very popular on the internet and many people use them.",
                payoffFinalSec = "Thanks for watching, see you next time!",
                ctaLine = "Please subscribe.",
                descriptionText = "Rejected draft.",
                hashtags = "#Shorts #AI",
                suggestedDurationSec = 45,
                voiceEngine = "Android Neural TTS (en-US)",
                hookStrengthScore = 28,
                curiosityScore = 31,
                retentionPotentialScore = 25,
                clarityScore = 60,
                originalityScore = 30,
                emotionalImpactScore = 24,
                shareabilityScore = 20,
                overallScore = 31,
                isRejected = true,
                rejectionReason = "AUTO-REJECTED BY QUALITY GATE: Contains banned slow intro ('Hey guys welcome back'), early begging CTA at 2s, and hook strength score (28/100) is below the 72/100 threshold.",
                probabilityStatement = "Historical telemetry shows slow greetings in 0-2s suffer a 68% swipe-away rate. Script automatically rejected."
            )
        )

        // Seed Published Videos (1 High-Performing Winner + 2 Underperforming Shorts with "Why Did This Short Fail?" diagnosis)
        dao.insertVideo(
            ShortVideoEntity(
                channelId = ch1Id,
                scriptId = script1Id,
                title = "This AI Runs With ZERO Internet! #Shorts",
                description = "How 4-bit quantized open-source AI models run 100% offline on mobile hardware.",
                hashtags = "#Shorts #AI #OpenSource #TechFacts",
                targetAudience = "Universal (US+IN)",
                language = "English",
                filePath = "/data/user/0/com.aistudio.shortspilot.vtxkqp/files/shorts/short_offline_ai_1080x1920.mp4",
                audioPath = "/data/user/0/com.aistudio.shortspilot.vtxkqp/files/shorts/short_offline_ai_voice.wav",
                srtCaptions = "1\n00:00:00,000 --> 00:00:02,000\nTurn on AIRPLANE MODE right now—\n\n2\n00:00:02,000 --> 00:00:05,000\nEvery cloud prompt costs $20 or ₹1,700/mo.\n\n3\n00:00:05,000 --> 00:00:20,000\n4-bit quantization fits a 3B model into 1.8GB RAM at 35 tok/s offline.\n\n4\n00:00:20,000 --> 00:00:26,000\nComment LOCAL for the free benchmark list!",
                durationSec = 26,
                voiceUsed = "Android Neural TTS (en-US)",
                bgMusicTrack = "Cyber Pulse 118BPM (Procedural Synth)",
                qcPassed = true,
                qcChecklistJson = "12/12 PASSED: 9:16 1080x1920 verified | Audio RMS -14dB | Safe-margin captions | 0-2s Hook verified | Zero black frames | Copyright-safe",
                uploadStatus = "PUBLISHED",
                scheduledPublishTime = "Published 48h ago • 08:30 EST",
                youtubeVideoId = "yt_short_99281a",
                views = 48920,
                likes = 3410,
                comments = 418,
                shares = 890,
                subscribersGained = 312,
                avgViewDurationSec = 24.2f,
                retentionPercent = 93.1f,
                viewedVsSwipedPercent = 78.4f,
                swipeAwayPercent = 21.6f,
                engagementRatePercent = 9.6f,
                usaViewsPercent = 54f,
                indiaViewsPercent = 41f,
                returningViewersPercent = 31f,
                shortsFeedTrafficPercent = 91f,
                analysisInterval = "48 hours",
                performanceVerdict = "SUCCESS",
                failureCode = "NONE",
                failurePlainExplanation = "Outperformed channel view average by +142%. The 0.8s Airplane Mode visual contradiction achieved a 78.4% Viewed-vs-Swiped rate across both USA and India.",
                autoCorrectionPlan = "Scale pattern: Replicate 24-28s duration + contrarian hardware demo hook for next 3 Shorts.",
                abTestGroup = "Variant A (Contrarian Imperative Hook)",
                abTestComparisonNote = "Variant A beat Variant B (Question Hook) by +16.2% Viewed vs Swiped."
            )
        )

        dao.insertVideo(
            ShortVideoEntity(
                channelId = ch1Id,
                scriptId = script1Id,
                title = "History of Neural Networks Explained in 58 Seconds #Shorts",
                description = "From 1958 perceptrons to modern transformers.",
                hashtags = "#Shorts #AIHistory #MachineLearning",
                targetAudience = "USA",
                language = "English",
                filePath = "/data/user/0/com.aistudio.shortspilot.vtxkqp/files/shorts/short_nn_history_1080x1920.mp4",
                audioPath = "/data/user/0/com.aistudio.shortspilot.vtxkqp/files/shorts/short_nn_history_voice.wav",
                srtCaptions = "1\n00:00:00,000 --> 00:00:04,000\nIn 1958, Frank Rosenblatt invented the perceptron...",
                durationSec = 58,
                voiceUsed = "Android Neural TTS (en-US)",
                bgMusicTrack = "Ambient Documentary Pad (Procedural)",
                qcPassed = true,
                qcChecklistJson = "12/12 PASSED",
                uploadStatus = "PUBLISHED",
                scheduledPublishTime = "Published 7d ago • 11:00 EST",
                youtubeVideoId = "yt_short_77412b",
                views = 2140,
                likes = 64,
                comments = 5,
                shares = 8,
                subscribersGained = 4,
                avgViewDurationSec = 19.1f,
                retentionPercent = 32.9f,
                viewedVsSwipedPercent = 41.2f,
                swipeAwayPercent = 58.8f,
                engagementRatePercent = 3.6f,
                usaViewsPercent = 68f,
                indiaViewsPercent = 22f,
                returningViewersPercent = 12f,
                shortsFeedTrafficPercent = 79f,
                analysisInterval = "7 days",
                performanceVerdict = "UNDERPERFORMING",
                failureCode = "HIGH SWIPE RATE + LOW RETENTION",
                failurePlainExplanation = "58.8% of viewers swiped away in the first 2 seconds because opening with a textbook date ('In 1958...') lacked immediate personal stakes. Additionally, the 58-second runtime caused a steep retention drop at 0:19.",
                autoCorrectionPlan = "1. Replace chronological date opening with a modern impact hook.\n2. Cut target duration from 58s down to 26s.\n3. Add visual scene transition at 0.8s.",
                abTestGroup = "Variant B (58s Chronological Style)",
                abTestComparisonNote = "58s runtime underperformed 26s channel benchmark by -60.2% average percentage viewed."
            )
        )

        dao.insertVideo(
            ShortVideoEntity(
                channelId = ch1Id,
                scriptId = script1Id,
                title = "How Browser Cookies Track Your Price Sensitivity #Shorts",
                description = "Dynamic pricing algorithms explained.",
                hashtags = "#Shorts #Cybersecurity #TechTips",
                targetAudience = "Both",
                language = "English",
                filePath = "/data/user/0/com.aistudio.shortspilot.vtxkqp/files/shorts/short_cookies_1080x1920.mp4",
                audioPath = "/data/user/0/com.aistudio.shortspilot.vtxkqp/files/shorts/short_cookies_voice.wav",
                srtCaptions = "1\n00:00:00,000 --> 00:00:02,000\nWhy does the same flight cost $80 more on your second search?",
                durationSec = 27,
                voiceUsed = "Android Neural TTS (en-US)",
                bgMusicTrack = "Tension Pulse 120BPM (Procedural)",
                qcPassed = true,
                qcChecklistJson = "12/12 PASSED",
                uploadStatus = "PUBLISHED",
                scheduledPublishTime = "Published 24h ago • 03:00 EST",
                youtubeVideoId = "yt_short_88104c",
                views = 4320,
                likes = 295,
                comments = 14,
                shares = 31,
                subscribersGained = 9,
                avgViewDurationSec = 23.4f,
                retentionPercent = 86.7f,
                viewedVsSwipedPercent = 71.0f,
                swipeAwayPercent = 29.0f,
                engagementRatePercent = 7.8f,
                usaViewsPercent = 49f,
                indiaViewsPercent = 46f,
                returningViewersPercent = 19f,
                shortsFeedTrafficPercent = 64f,
                analysisInterval = "24 hours",
                performanceVerdict = "UNDERPERFORMING",
                failureCode = "GOOD RETENTION BUT LOW IMPRESSIONS",
                failurePlainExplanation = "Retention is strong (86.7% APV) and Viewed-vs-Swiped is healthy (71%), but total impressions stalled at 4.3K because the title used technical jargon ('Browser Cookies Track Price Sensitivity') and was published at 03:00 EST off-peak.",
                autoCorrectionPlan = "1. Repackage topic with high-curiosity consumer phrasing ('Why Flights Cost More 5 Minutes Later').\n2. Shift upload slot from 03:00 EST to 08:30 EST / 18:00 IST prime window.\n3. Add interactive pinned comment question to boost comment velocity.",
                abTestGroup = "Variant A (Technical Title)",
                abTestComparisonNote = "Consumer-angle title variant predicted to increase search + browse seed impressions by +28%."
            )
        )

        // Seed Content Learning System (All 9 required categories)
        val learnings = listOf(
            ContentLearningEntity(
                channelId = ch1Id,
                category = "successful_hooks",
                patternName = "Contrarian Action Imperative (0–1.8s)",
                metricSummary = "78.4% Viewed vs Swiped (+19% vs avg)",
                avgRetentionPercent = 91.5f,
                avgViewedVsSwipedPercent = 78.4f,
                confidenceScore = 94,
                actionableRule = "Start with a direct physical action or counter-intuitive proof in the first 6 words; never start with dates or greetings."
            ),
            ContentLearningEntity(
                channelId = ch1Id,
                category = "failed_hooks",
                patternName = "Chronological / Textbook Definition Opening",
                metricSummary = "41.2% Viewed vs Swiped (58.8% Swipe Away)",
                avgRetentionPercent = 32.9f,
                avgViewedVsSwipedPercent = 41.2f,
                confidenceScore = 92,
                actionableRule = "Automatically block scripts starting with 'In [Year]', 'Today we will', or 'Did you know that the history of...'."
            ),
            ContentLearningEntity(
                channelId = ch1Id,
                category = "successful_lengths",
                patternName = "24–28 Seconds High-Density Bucket",
                metricSummary = "90.4% Avg Percentage Viewed",
                avgRetentionPercent = 90.4f,
                avgViewedVsSwipedPercent = 75.2f,
                confidenceScore = 91,
                actionableRule = "Prioritize 24–28s scripts (65–75 spoken words) over 50–60s scripts to maximize loop completion rate."
            ),
            ContentLearningEntity(
                channelId = ch1Id,
                category = "successful_topics",
                patternName = "Zero-Cost Offline AI & Consumer Privacy Hacks",
                metricSummary = "48.9K Avg Views • 1.8% Sub Conversion",
                avgRetentionPercent = 93.1f,
                avgViewedVsSwipedPercent = 78.4f,
                confidenceScore = 89,
                actionableRule = "Double weight on topics showing free/open-source replacements for paid subscriptions."
            ),
            ContentLearningEntity(
                channelId = ch1Id,
                category = "failed_topics",
                patternName = "Generic Prompt Listicles & Pure History Recaps",
                metricSummary = "2.1K Avg Views • High Saturation",
                avgRetentionPercent = 38.0f,
                avgViewedVsSwipedPercent = 44.5f,
                confidenceScore = 87,
                actionableRule = "Deprioritize topics with Competition Score < 6/15 unless paired with a novel benchmark."
            ),
            ContentLearningEntity(
                channelId = ch1Id,
                category = "successful_styles",
                patternName = "Split-Screen Telemetry + Word-Emphasized Captions",
                metricSummary = "+22% Mid-Video Retention (5–20s)",
                avgRetentionPercent = 89.2f,
                avgViewedVsSwipedPercent = 74.8f,
                confidenceScore = 88,
                actionableRule = "Trigger visual layout shift every 3.5 seconds with highlighted yellow/cyan keyword captions."
            ),
            ContentLearningEntity(
                channelId = ch1Id,
                category = "successful_upload_times",
                patternName = "08:30 EST / 18:00 IST Dual-Peak Window",
                metricSummary = "+34% Initial 1h Seed Velocity",
                avgRetentionPercent = 88.0f,
                avgViewedVsSwipedPercent = 74.0f,
                confidenceScore = 86,
                actionableRule = "Schedule universal US+India Shorts at 08:30 EST (18:00 IST) to capture US morning commute + India evening prime time."
            ),
            ContentLearningEntity(
                channelId = ch1Id,
                category = "audience_preferences",
                patternName = "Concrete Numbers ($/₹, RAM GB, Tokens/sec)",
                metricSummary = "9.6% Engagement Rate",
                avgRetentionPercent = 90.1f,
                avgViewedVsSwipedPercent = 76.0f,
                confidenceScore = 90,
                actionableRule = "Include at least 2 concrete benchmark numbers in the 5–15s body section."
            ),
            ContentLearningEntity(
                channelId = ch1Id,
                category = "country_performance",
                patternName = "US SaaS/Privacy Angle vs India Value/Mobile Angle",
                metricSummary = "US 54% / IN 41% Balanced Feed Distribution",
                avgRetentionPercent = 91.2f,
                avgViewedVsSwipedPercent = 77.1f,
                confidenceScore = 93,
                actionableRule = "When 'Both' is active, include both USD ($) and INR (₹) or create localized dual variants for culturally specific hooks."
            )
        )
        dao.insertLearnings(learnings)

        // Seed Configurable Daily Automation Schedule (Section 12)
        val tasks = listOf(
            ScheduleTaskEntity(
                channelId = ch1Id,
                timeSlot = "06:00",
                taskTitle = "Research Live Trends (YouTube + News RSS + Reddit)",
                stageCode = "RESEARCH",
                isEnabled = true,
                lastStatus = "COMPLETED",
                lastRunSummary = "Scored 5 niche trends; top score 95/100 (Offline Local AI)."
            ),
            ScheduleTaskEntity(
                channelId = ch1Id,
                timeSlot = "07:00",
                taskTitle = "Generate 5–20 High-Interest Shorts Ideas",
                stageCode = "IDEAS",
                isEnabled = true,
                lastStatus = "COMPLETED",
                lastRunSummary = "Generated prioritized ideas with US + India localization matrices."
            ),
            ScheduleTaskEntity(
                channelId = ch1Id,
                timeSlot = "08:00",
                taskTitle = "Select Highest-Probability Idea (High Interest + Low Comp)",
                stageCode = "SELECT",
                isEnabled = true,
                lastStatus = "COMPLETED",
                lastRunSummary = "Selected 'Running a Full AI Assistant Offline' (Priority 96/100)."
            ),
            ScheduleTaskEntity(
                channelId = ch1Id,
                timeSlot = "08:10",
                taskTitle = "Generate & Score Viral Script (3 Hooks + Quality Gate)",
                stageCode = "SCRIPT",
                isEnabled = true,
                lastStatus = "COMPLETED",
                lastRunSummary = "Selected Hook #1 (94/100); rejected weak intro variants automatically."
            ),
            ScheduleTaskEntity(
                channelId = ch1Id,
                timeSlot = "08:20",
                taskTitle = "Synthesize TTS Voiceover, Captions & Procedural Audio",
                stageCode = "ASSETS",
                isEnabled = true,
                lastStatus = "COMPLETED",
                lastRunSummary = "Generated neural voice track, SRT word timings & copyright-safe synth bed."
            ),
            ScheduleTaskEntity(
                channelId = ch1Id,
                timeSlot = "09:00",
                taskTitle = "Render 1080x1920 (9:16) H.264 MP4 Short",
                stageCode = "RENDER",
                isEnabled = true,
                lastStatus = "COMPLETED",
                lastRunSummary = "Hardware MediaCodec H.264 render complete (1080x1920, 30fps)."
            ),
            ScheduleTaskEntity(
                channelId = ch1Id,
                timeSlot = "09:15",
                taskTitle = "Run 12-Point Automated Quality Control Check",
                stageCode = "QC",
                isEnabled = true,
                lastStatus = "COMPLETED",
                lastRunSummary = "12/12 checks passed: 9:16 verified, audio normalized, no black frames."
            ),
            ScheduleTaskEntity(
                channelId = ch1Id,
                timeSlot = "09:30",
                taskTitle = "Schedule Official YouTube Data API v3 Upload",
                stageCode = "UPLOAD",
                isEnabled = true,
                lastStatus = "COMPLETED",
                lastRunSummary = "Queued for prime window publishAt (08:30 EST / 18:00 IST)."
            ),
            ScheduleTaskEntity(
                channelId = ch1Id,
                timeSlot = "18:00",
                taskTitle = "Collect Analytics, Diagnose Failures & Update Strategy",
                stageCode = "ANALYTICS",
                isEnabled = true,
                lastStatus = "COMPLETED",
                lastRunSummary = "Evaluated 1h/6h/24h/48h/7d checkpoints; updated learning weights."
            )
        )
        dao.insertScheduleTasks(tasks)

        // Seed System Logs
        dao.insertLog(
            SystemLogEntity(
                level = "SUCCESS",
                subsystem = "AUTOPILOT_CORE",
                message = "ShortsPilot SQLite Engine initialized with US + India audience intelligence.",
                details = "Database: shortspilot_autopilot.db | Free-First Architecture Active"
            )
        )
        dao.insertLog(
            SystemLogEntity(
                level = "INFO",
                subsystem = "QC_GATE",
                message = "Auto-rejected draft script #2 due to weak 0-2s greeting hook (Score 31/100 < 72).",
                details = "Rule: Never allow 'Hey guys welcome back' in first 2 seconds."
            )
        )
        dao.insertLog(
            SystemLogEntity(
                level = "RECOVERY",
                subsystem = "FAILURE_ANALYZER",
                message = "Detected HIGH SWIPE RATE (58.8%) on 58s History Short; updated target duration rule to 24-28s.",
                details = "Strategy History updated automatically."
            )
        )
    }
}
