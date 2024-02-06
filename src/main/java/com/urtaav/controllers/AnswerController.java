package com.urtaav.controllers;

import com.urtaav.dtos.AnswerDto;
import com.urtaav.dtos.CommentDto;
import com.urtaav.services.user.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @PostMapping
    public ResponseEntity<?> addAnswer(@RequestBody AnswerDto answerDto) {
        AnswerDto createdAnswerDto = answerService.postAnswer(answerDto);
        if (createdAnswerDto == null) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnswerDto);
    }

    @GetMapping("/approved/{answerId}")
    public ResponseEntity<AnswerDto> approveAnswer(@PathVariable Long answerId) {
        AnswerDto appproveAnswerDto = answerService.approveAnswer(answerId);
        if (appproveAnswerDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appproveAnswerDto);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> postCommentToAnswer(@RequestBody CommentDto commentDto){
        CommentDto postedCommentDto = answerService.postCommentToAnswer(commentDto);
        if (postedCommentDto == null) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(postedCommentDto);
    }
}
