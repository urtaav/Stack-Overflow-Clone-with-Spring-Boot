package com.urtaav.services.user;

import com.urtaav.dtos.AnswerDto;
import com.urtaav.dtos.CommentDto;
import com.urtaav.entities.Answers;
import com.urtaav.entities.Comment;
import com.urtaav.entities.Question;
import com.urtaav.entities.User;
import com.urtaav.repositories.AnswerRepository;
import com.urtaav.repositories.CommentRepository;
import com.urtaav.repositories.QuestionRepository;
import com.urtaav.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public AnswerDto postAnswer(AnswerDto answerDto) {
        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        Optional<Question> optionalQuestion = questionRepository.findById(answerDto.getQuestionId());
        if (optionalUser.isPresent() && optionalQuestion.isPresent()) {
            Answers answer = new Answers();
            answer.setBody(answerDto.getBody());
            answer.setCreatedDate(new Date());
            answer.setUser(optionalUser.get());
            answer.setQuestion(optionalQuestion.get());

            Answers createdAnswer = answerRepository.save(answer);
            AnswerDto createdAnswerDto = new AnswerDto();
            createdAnswerDto.setId(createdAnswer.getId());
            return createdAnswerDto;
        }
        return null;
    }

    public AnswerDto approveAnswer(Long answerId) {
        Optional<Answers> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isPresent()) {
            Answers answers = optionalAnswer.get();
            answers.setApproved(true);
            Answers updateAnswer = answerRepository.save(answers);
            AnswerDto updatedAnswerDto = new AnswerDto();
            updatedAnswerDto.setId(updateAnswer.getId());
            return updatedAnswerDto;
        }
        return null;
    }

    @Override
    public CommentDto postCommentToAnswer(CommentDto commentDto) {
        Optional<Answers> optionalAnswer = answerRepository.findById(commentDto.getAnswerId());
        Optional<User> optionalUser = userRepository.findById(commentDto.getUserId());
        if(optionalAnswer.isPresent() && optionalUser.isPresent()){
            Comment comment =  new Comment();
            comment.setBody(commentDto.getBody());
            comment.setCreatedDate(new Date());
            comment.setAnswer(optionalAnswer.get());
            comment.setUser(optionalUser.get());
            Comment postedComment = commentRepository.save(comment);
            CommentDto postedCommentDto = new CommentDto();
            postedCommentDto.setId(postedComment.getId());
            return postedCommentDto;
        }
        return null;
    }
}
