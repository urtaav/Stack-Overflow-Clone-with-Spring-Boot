package com.urtaav.controllers;

import com.urtaav.dtos.AnswerVoteDto;
import com.urtaav.dtos.QuestionVoteDto;
import com.urtaav.services.user.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vote")
public class VoteController {

    @Autowired
    VoteService voteService;

    @PostMapping
    public ResponseEntity<?> addVoteQuestion(@RequestBody QuestionVoteDto questionVoteDto) {
        QuestionVoteDto questionVotedDto = voteService.addVoteToQuestion(questionVoteDto);
        if (questionVotedDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(questionVotedDto);
    }

    @PostMapping("/answer-vote")
    public ResponseEntity<?> addVoteToAnswer(@RequestBody AnswerVoteDto answerVoteDto) {
        AnswerVoteDto answerVotedDto = voteService.addVoteToAnswer(answerVoteDto);
        if(answerVotedDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(answerVotedDto);
    }
}
