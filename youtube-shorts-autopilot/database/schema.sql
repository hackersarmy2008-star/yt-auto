-- ShortsPilot Free-First SQLite Schema
CREATE TABLE IF NOT EXISTS channels (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    description TEXT,
    niche TEXT NOT NULL,
    target_country TEXT NOT NULL, -- USA, India, Both
    primary_language TEXT NOT NULL, -- English, Hindi, Hinglish
    upload_frequency TEXT NOT NULL,
    preferred_upload_time TEXT NOT NULL,
    target_duration_sec INTEGER DEFAULT 26,
    content_style TEXT,
    auto_mode_enabled INTEGER DEFAULT 1,
    active_strategy_summary TEXT
);

CREATE TABLE IF NOT EXISTS trend_topics (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    channel_id INTEGER NOT NULL,
    title TEXT NOT NULL,
    source TEXT NOT NULL,
    category_status TEXT NOT NULL,
    total_trend_score INTEGER NOT NULL,
    usa_angle TEXT,
    india_angle TEXT
);

CREATE TABLE IF NOT EXISTS content_learnings (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    channel_id INTEGER NOT NULL,
    category TEXT NOT NULL, -- successful_topics, failed_topics, successful_hooks, failed_hooks, successful_lengths, successful_styles, successful_upload_times, audience_preferences, country_performance
    pattern_name TEXT NOT NULL,
    avg_retention_percent REAL,
    avg_viewed_vs_swiped_percent REAL,
    actionable_rule TEXT NOT NULL
);
