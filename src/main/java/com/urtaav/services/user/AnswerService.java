package com.urtaav.services.user;

import com.urtaav.dtos.AnswerDto;
import com.urtaav.dtos.CommentDto;

public interface AnswerService {
    AnswerDto postAnswer(AnswerDto answerDto);
    AnswerDto approveAnswer(Long answerId);

    CommentDto postCommentToAnswer(CommentDto commentDto);
}
