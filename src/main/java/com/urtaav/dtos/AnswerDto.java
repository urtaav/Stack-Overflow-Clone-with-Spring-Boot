package com.urtaav.dtos;

import com.urtaav.entities.Image;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AnswerDto {
    private Long id;
    private String body;
    private Long questionId;
    private Long userId;
    private String username;
    private Image file;
    private Date createDate;
    private String base64;
    private boolean approved;
    private int voted;
    private Integer voteCount = 0;
    private List<CommentDto> commentDtoList;
}
