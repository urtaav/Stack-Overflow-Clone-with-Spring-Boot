package com.urtaav.controllers;

import com.urtaav.dtos.AllQuestionResponseDto;
import com.urtaav.dtos.QuestionDTO;
import com.urtaav.dtos.QuestionSearchResponseDto;
import com.urtaav.dtos.SingleQuestionDto;
import com.urtaav.services.user.QuestionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/question")
public class QuestionsController {
    private final QuestionService questionService;

    public QuestionsController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<?> postQuestion(@RequestBody QuestionDTO questionDTO){
        QuestionDTO createdQuestionDTO = questionService.addQuestion(questionDTO);
        if(createdQuestionDTO == null){
            return new ResponseEntity<>("Something sent wrong",HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDTO);
    }

    @GetMapping("/questions")
    public ResponseEntity<AllQuestionResponseDto> getAllQuestions(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "createdDate") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction
    ){
        var pageRequestData = PageRequest.of(pageNumber - 1, size, Sort.Direction.valueOf(direction), sort);
        AllQuestionResponseDto allQuestionResponseDto = questionService.getAllQuestions(pageRequestData);

        return ResponseEntity.ok(allQuestionResponseDto);
    }

    @GetMapping("/questions/user")
    public ResponseEntity<AllQuestionResponseDto> getAllQuestionsByUserId(
            @RequestParam(name = "userId", required = true) Long userId,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "createdDate") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction
    ){
        var pageRequestData = PageRequest.of(pageNumber - 1, size, Sort.Direction.valueOf(direction), sort);
        AllQuestionResponseDto allQuestionResponseDto = questionService.getAllQuestionsByUserId(userId,pageRequestData);

        return ResponseEntity.ok(allQuestionResponseDto);
    }

    @GetMapping("/{questionId}/{userId}")
    public ResponseEntity<?> getQuestionById(@PathVariable Long questionId,@PathVariable Long userId) {
        SingleQuestionDto singleQuestionDto = questionService.getQuestionById(questionId,userId);

        if (singleQuestionDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(singleQuestionDto);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchQuestionByTitle(
            @RequestParam(name = "title", required = true) String title,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "createdDate") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction
    ){
        var pageRequestData = PageRequest.of(pageNumber - 1, size, Sort.Direction.valueOf(direction), sort);
        QuestionSearchResponseDto questionSearchResponseDto = questionService.searchQuestionByTitle(title,pageRequestData);

        if(questionSearchResponseDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(questionSearchResponseDto);
    }

}
