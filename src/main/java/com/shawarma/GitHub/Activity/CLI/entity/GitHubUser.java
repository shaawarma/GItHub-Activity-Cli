package com.shawarma.GitHub.Activity.CLI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "github_users")
@Getter @Setter
public class GitHubUser {
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "recent_activity")
    @Lob
    private String recentActivity;
}
