package com.urtaav.services.user;

import com.urtaav.dtos.AllQuestionResponseDto;
import com.urtaav.dtos.QuestionDTO;
import com.urtaav.dtos.QuestionSearchResponseDto;
import com.urtaav.dtos.SingleQuestionDto;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;

public interface QuestionService {
    QuestionDTO addQuestion(QuestionDTO questionDTO);

    AllQuestionResponseDto getAllQuestions(PageRequest pageable);

    SingleQuestionDto getQuestionById(Long questionId,Long userId);

    AllQuestionResponseDto getAllQuestionsByUserId(Long userId, PageRequest pageRequestData);

    QuestionSearchResponseDto searchQuestionByTitle(String title, PageRequest pageRequestData);
}
