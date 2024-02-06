package com.urtaav.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String body;
    private List<String> tags;
    private Long userId;
    private String username;
    private Date createdDate;
    private Integer voteCount = 0;
    public  int voted;
    private boolean hasApprovedAnswer =  false;
}
