package com.urtaav.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AllQuestionResponseDto {
    private List<QuestionDTO> questionDTOList;
    private  int pageNumber;
    private  int pageSize;
    private  long totalElements;
    private  int totalPages;
    private  boolean isLastPage;
}
