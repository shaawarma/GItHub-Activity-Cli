package com.shawarma.GitHub.Activity.CLI.service.IMPL;

import com.shawarma.GitHub.Activity.CLI.entity.GitHubUser;
import com.shawarma.GitHub.Activity.CLI.repository.UserRepository;
import com.shawarma.GitHub.Activity.CLI.service.GitHubUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GitHubUserServiceIMPL implements GitHubUserService {
    private final UserRepository userRepository;


    @Override
    public String addingSessionData(GitHubUser gitHubUser) {
        userRepository.save(gitHubUser);
        return "Successfully Added";
    }
}
