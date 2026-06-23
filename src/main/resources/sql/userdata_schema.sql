CREATE TABLE IF NOT EXISTS github_users (
                                            user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            username VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    recent_activity CLOB
    );
