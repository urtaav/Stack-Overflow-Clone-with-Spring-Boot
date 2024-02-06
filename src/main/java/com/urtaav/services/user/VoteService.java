package com.urtaav.services.user;

import com.urtaav.dtos.AnswerVoteDto;
import com.urtaav.dtos.QuestionVoteDto;

public interface VoteService {

    QuestionVoteDto addVoteToQuestion(QuestionVoteDto questionVoteDto);

    AnswerVoteDto addVoteToAnswer(AnswerVoteDto answerVoteDto);
}
