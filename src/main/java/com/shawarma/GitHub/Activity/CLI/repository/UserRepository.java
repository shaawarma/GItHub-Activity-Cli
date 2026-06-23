package com.shawarma.GitHub.Activity.CLI.repository;

import com.shawarma.GitHub.Activity.CLI.entity.GitHubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<GitHubUser,Long> {
}