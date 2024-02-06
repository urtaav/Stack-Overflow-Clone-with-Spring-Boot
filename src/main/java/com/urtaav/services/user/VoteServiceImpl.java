package com.urtaav.services.user;

import com.urtaav.dtos.AnswerVoteDto;
import com.urtaav.dtos.QuestionVoteDto;
import com.urtaav.entities.*;
import com.urtaav.enums.VoteType;
import com.urtaav.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private QuestionVoteRepository questionVoteRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerVoteRepository answerVoteRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Override
    public QuestionVoteDto addVoteToQuestion(QuestionVoteDto questionVoteDto) {
        Optional<User> optionalUser = userRepository.findById(questionVoteDto.getUserId());
        Optional<Question> optionalQuestion = questionRepository.findById(questionVoteDto.getQuestionId());
        if (optionalQuestion.isPresent() && optionalUser.isPresent()) {
            QuestionVote questionVote = new QuestionVote();
            Question existingQuestion = optionalQuestion.get();
            questionVote.setVoteType(questionVoteDto.getVoteType());
            if (questionVote.getVoteType() == VoteType.UPVOTE) {
                existingQuestion.setVoteCount(existingQuestion.getVoteCount() + 1);
            } else {
                existingQuestion.setVoteCount(existingQuestion.getVoteCount() - 1);
            }

            questionVote.setQuestion(optionalQuestion.get());
            questionVote.setUser(optionalUser.get());
            questionRepository.save(existingQuestion);
            QuestionVote votedQuestion = questionVoteRepository.save(questionVote);
            QuestionVoteDto questionVotedDto = new QuestionVoteDto();
            questionVotedDto.setId(votedQuestion.getId());
            return questionVotedDto;
        }
        return null;
    }

    @Override
    public AnswerVoteDto addVoteToAnswer(AnswerVoteDto answerVoteDto) {

        Optional<User> optionalUser = userRepository.findById(answerVoteDto.getUserId());
        Optional<Answers> optionalAnswer = answerRepository.findById(answerVoteDto.getAnswerId());

        if (optionalUser.isPresent() && optionalAnswer.isPresent()) {
            Answers existingAnswer = optionalAnswer.get();
            User existingUser = optionalUser.get();
            AnswerVote answerVote = new AnswerVote();
            answerVote.setVoteType(answerVoteDto.getVoteType());
            answerVote.setAnswer(existingAnswer);
            answerVote.setUser(existingUser);
            if (answerVoteDto.getVoteType() == VoteType.UPVOTE) {
                existingAnswer.setVoteCount(existingAnswer.getVoteCount() + 1);
            } else {
                existingAnswer.setVoteCount(existingAnswer.getVoteCount() - 1);
            }
            answerRepository.save(existingAnswer);
            AnswerVote createdAnswerVote = answerVoteRepository.save(answerVote);
            AnswerVoteDto createdAnswerVoteDto = new AnswerVoteDto();
            createdAnswerVoteDto.setId(createdAnswerVote.getId());
            return createdAnswerVoteDto;
        }
        return null;
    }
}
