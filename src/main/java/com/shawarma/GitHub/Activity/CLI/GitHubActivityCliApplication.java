package com.shawarma.GitHub.Activity.CLI;

import com.shawarma.GitHub.Activity.CLI.entity.GitHubUser;
import com.shawarma.GitHub.Activity.CLI.service.GitHubService;
import com.shawarma.GitHub.Activity.CLI.service.GitHubUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

@SpringBootApplication
@RequiredArgsConstructor
public class GitHubActivityCliApplication implements CommandLineRunner {

	private final GitHubService githubService;
	private final GitHubUserService gitHubUserService;

	public static void main(String[] args) {
		SpringApplication.run(GitHubActivityCliApplication.class, args);
	}

	@Override
	public void run(String... args) {

		if (args.length == 0) {
			System.out.println("Please provide a GitHub username.");
			return;
		}

		String username = args[0];

		String recentActivity = githubService.fetchingRecentActivity(username);

		GitHubUser gitHubUser = new GitHubUser();
		gitHubUser.setUserName(username);
		gitHubUser.setCreatedAt(Instant.now());
		gitHubUser.setRecentActivity(recentActivity);

		gitHubUserService.addingSessionData(gitHubUser);

		System.out.println(recentActivity);
	}
}