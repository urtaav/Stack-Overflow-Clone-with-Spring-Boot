package com.urtaav.dtos;

import lombok.Data;

import java.util.List;

@Data
public class QuestionSearchResponseDto {
    private List<QuestionDTO> questionDtoList;
    private  int pageNumber;
    private  int pageSize;
    private  long totalElements;
    private  int totalPages;
    private  boolean isLastPage;
}
