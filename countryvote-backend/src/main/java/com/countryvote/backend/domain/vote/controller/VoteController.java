package com.countryvote.backend.domain.vote.controller;

import com.countryvote.backend.domain.vote.dto.CreateVoteRequest;
import com.countryvote.backend.domain.vote.dto.VoteResponse;
import com.countryvote.backend.domain.vote.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteResponse create(@Valid @RequestBody CreateVoteRequest request) {
        return voteService.createVote(request);
    }
}
