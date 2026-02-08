package com.countryvote.backend.domain.vote.service;

import com.countryvote.backend.common.errors.DuplicateVoteException;
import com.countryvote.backend.domain.vote.dto.CreateVoteRequest;
import com.countryvote.backend.domain.vote.dto.VoteResponse;
import com.countryvote.backend.domain.vote.entity.UserVote;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final com.countryvote.backend.domain.vote.repository.UserVoteRepository userVoteRepository;

    @Transactional
    public VoteResponse createVote(CreateVoteRequest request) {
        String email = request.email().trim().toLowerCase();

        if (userVoteRepository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateVoteException(email);
        }
        UserVote saved = userVoteRepository.save(
                UserVote.builder()
                        .email(email)
                        .countryCode(request.countryCode().trim())
                        .createdAt(Instant.now())
                        .build()
        );
        return new VoteResponse(saved.getId(), saved.getEmail(), saved.getCountryCode(), saved.getCreatedAt());
    }
}
