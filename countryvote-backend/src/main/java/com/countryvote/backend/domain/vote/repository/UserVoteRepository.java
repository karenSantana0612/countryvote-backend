package com.countryvote.backend.domain.vote.repository;

import com.countryvote.backend.domain.vote.entity.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;

public interface UserVoteRepository extends JpaRepository<UserVote, Long> {

    boolean existsByEmailIgnoreCase(String email);

        @Query("""
        select v.countryCode, count(v)
        from UserVote v
        group by v.countryCode
        order by count(v) desc
    """)
        List<Object[]> countVotesGrouped();
}
