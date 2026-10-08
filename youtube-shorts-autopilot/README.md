# ShortsPilot — YouTube Shorts Autopilot System (Android Native + Python/FFmpeg Termux & Cloud Suite)

ShortsPilot is a complete, production-ready **YouTube Shorts Autopilot System** designed around a **Free-First Architecture** (SQLite, OpenRouter free models / Gemini REST API, public RSS/Reddit/YouTube trend feeds, hardware 1080x1920 9:16 H.264 rendering, FFmpeg, Neural TTS, and Official YouTube Data API v3).

> **Important Optimization Principle:** ShortsPilot never claims to "guarantee viral videos." Instead, it operates a closed-loop **Telemetry & Probability Optimization System** that continuously analyzes 0–2s Viewed-vs-Swiped survival, Average Percentage Viewed (APV), and USA + India localization performance to increase the probability of retention and distribution.

---

## 1. Dual Execution Modes Included in This Project

1. **Native Android App (`app/` module)**:
   - Runs directly on Android devices or the AI Studio Streaming Emulator.
   - Uses **Room (SQLite)** (`shortspilot_autopilot.db`) for zero-cost local persistence.
   - Renders real **1080x1920 (9:16) H.264 MP4** Shorts on-device using Android `MediaCodec` + `MediaMuxer`, procedural 118BPM copyright-safe WAV synthesis, timed `.srt` captions, and Android `TextToSpeech` (`en-US`, `en-IN`, `hi-IN`).
   - Enforces a **12-Point Automated Quality Control Gate** and features the **"Why Did This Short Fail?" Diagnostic Engine** across `1h`, `6h`, `24h`, `48h`, and `7d` intervals.

2. **Companion Python + FFmpeg CLI & Termux/Cloud Suite (`youtube-shorts-autopilot/`)**:
   - Designed for low-cost Linux VPS, free-tier cloud hosting, or **Android Termux**.
   - Uses Python 3, `sqlite3`, `ffmpeg`, `edge-tts` / `piper-tts`, `whisper` / SRT generator, OpenRouter API, and the Official YouTube Data API v3.

---

## 2. Environment Variables & Credentials Setup

Configure your keys in the **Secrets panel in AI Studio** (for Android APK builds) or copy `.env.example` to `.env` (for Termux/Linux server execution):

```ini
AI_PROVIDER=openrouter
OPENROUTER_API_KEY=your_openrouter_key_here
GEMINI_API_KEY=your_gemini_key_here
YOUTUBE_API_KEY=your_youtube_data_api_v3_key
YOUTUBE_CLIENT_ID=your_google_oauth_client_id
YOUTUBE_ACCESS_TOKEN=your_youtube_oauth_access_token
```

---

## 3. Running on Android / Termux or Linux Cloud Server

### A. Build & Run the Native Android App
```bash
gradle :app:assembleDebug
gradle :app:testDebugUnitTest
```

### B. Run the Companion Python + FFmpeg Pipeline (Termux / Linux)
```bash
# Install free system packages (Ubuntu/Debian or Android Termux)
# On Termux: pkg install python ffmpeg
pip install -r youtube-shorts-autopilot/requirements.txt

# Initialize SQLite database & run an end-to-end Shorts Autopilot cycle
python3 youtube-shorts-autopilot/backend/autopilot_cli.py --niche "AI" --country "Both" --language "English" --auto-mode
```
