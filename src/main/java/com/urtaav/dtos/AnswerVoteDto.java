package com.urtaav.dtos;

import com.urtaav.enums.VoteType;
import lombok.Data;

@Data
public class AnswerVoteDto {
    private Long id;
    private VoteType voteType;
    private Long userId;
    private Long answerId;
}
