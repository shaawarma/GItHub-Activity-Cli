package com.shawarma.GitHub.Activity.CLI.service.IMPL;

import com.shawarma.GitHub.Activity.CLI.service.GitHubService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class GitHubServiceIMPL implements GitHubService {
    private final RestClient restClient = RestClient.builder().baseUrl("https://api.github.com").build();
    @Override
    public String fetchingRecentActivity(String userName) {

        String json = restClient.get()
                .uri("/users/{username}/events", userName)
                .retrieve()
                .body(String.class);

        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode events = objectMapper.readTree(json);

            StringBuilder output = new StringBuilder();

            if (!events.isArray()) {
                return "No activity found.";
            }

            for (JsonNode event : events) {

                String type = event.path("type").asText("UnknownEvent");
                String repo = event.path("repo").path("name").asText("Unknown Repository");
                JsonNode payload = event.path("payload");

                switch (type) {

                    case "PushEvent" -> {

                        int commitCount = 0;

                        JsonNode commits = payload.path("commits");

                        if (commits.isArray()) {
                            commitCount = commits.size();
                        }

                        output.append("- Pushed ")
                                .append(commitCount)
                                .append(" commit")
                                .append(commitCount == 1 ? "" : "s")
                                .append(" to ")
                                .append(repo);
                    }

                    case "IssuesEvent" -> {

                        String action = payload.path("action").asText("updated");

                        output.append("- ")
                                .append(Character.toUpperCase(action.charAt(0)))
                                .append(action.substring(1))
                                .append(" an issue in ")
                                .append(repo);
                    }

                    case "IssueCommentEvent" -> {

                        String action = payload.path("action").asText("commented on");

                        output.append("- ")
                                .append(Character.toUpperCase(action.charAt(0)))
                                .append(action.substring(1))
                                .append(" an issue comment in ")
                                .append(repo);
                    }

                    case "PullRequestEvent" -> {

                        String action = payload.path("action").asText("updated");

                        output.append("- ")
                                .append(Character.toUpperCase(action.charAt(0)))
                                .append(action.substring(1))
                                .append(" a pull request in ")
                                .append(repo);
                    }

                    case "PullRequestReviewEvent" ->
                            output.append("- Reviewed a pull request in ")
                                    .append(repo);

                    case "WatchEvent" ->
                            output.append("- Starred ")
                                    .append(repo);

                    case "ForkEvent" ->
                            output.append("- Forked ")
                                    .append(repo);

                    case "CreateEvent" -> {

                        String refType = payload.path("ref_type").asText("resource");

                        output.append("- Created ")
                                .append(refType)
                                .append(" in ")
                                .append(repo);
                    }

                    case "DeleteEvent" -> {

                        String refType = payload.path("ref_type").asText("resource");

                        output.append("- Deleted ")
                                .append(refType)
                                .append(" from ")
                                .append(repo);
                    }

                    case "ReleaseEvent" -> {

                        String action = payload.path("action").asText("published");

                        output.append("- ")
                                .append(Character.toUpperCase(action.charAt(0)))
                                .append(action.substring(1))
                                .append(" a release in ")
                                .append(repo);
                    }

                    default ->
                            output.append("- ")
                                    .append(type)
                                    .append(" on ")
                                    .append(repo);
                }

                output.append("\n");
            }

            if (output.isEmpty()) {
                return "No recent activity found.";
            }

            return output.toString();

        } catch (Exception e) {
            return "Failed to parse GitHub activity: " + e.getMessage();
        }
    }
}
