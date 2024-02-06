package com.urtaav.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SingleQuestionDto {
    private QuestionDTO questionDTO;
    private List<AnswerDto> answerDtoList;
}
