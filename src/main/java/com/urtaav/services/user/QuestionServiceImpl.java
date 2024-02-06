package com.urtaav.services.user;

import com.urtaav.dtos.*;
import com.urtaav.entities.*;
import com.urtaav.enums.VoteType;
import com.urtaav.repositories.AnswerRepository;
import com.urtaav.repositories.ImageRepository;
import com.urtaav.repositories.QuestionRepository;
import com.urtaav.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    public static final int SEARCH_RESULT_PER_PAGE = 5;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public QuestionDTO addQuestion(QuestionDTO questionDTO) {

        Optional<User> optionalUser = userRepository.findById(questionDTO.getUserId());
        if (optionalUser.isPresent()) {
            Question question = new Question();
            question.setTitle(questionDTO.getTitle());
            question.setBody(questionDTO.getBody());
            question.setTags(questionDTO.getTags());
            question.setCreatedDate(new Date());
            question.setUser(optionalUser.get());
            Question createdQuestion = questionRepository.save(question);
            QuestionDTO createdQuestionDto = new QuestionDTO();
            createdQuestionDto.setId(createdQuestion.getId());
            createdQuestionDto.setTitle(createdQuestion.getTitle());
            return createdQuestionDto;
        }

        return null;
    }

    @Override
    public AllQuestionResponseDto getAllQuestions(PageRequest pageable) {
        Page<Question> questionPage = questionRepository.findAll(pageable);

        AllQuestionResponseDto allQuestionResponseDto = new AllQuestionResponseDto();
        allQuestionResponseDto.setQuestionDTOList(questionPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList()));
        allQuestionResponseDto.setPageNumber(questionPage.getNumber() + 1);
        allQuestionResponseDto.setPageSize(questionPage.getSize());
        allQuestionResponseDto.setTotalElements(questionPage.getTotalElements());
        allQuestionResponseDto.setTotalPages(questionPage.getTotalPages());
        allQuestionResponseDto.setLastPage(questionPage.isLast());
        return allQuestionResponseDto;
    }

    @Override
    public SingleQuestionDto getQuestionById(Long questionId, Long userId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        if (optionalQuestion.isPresent()) {
            SingleQuestionDto singleQuestionDto = new SingleQuestionDto();
            List<AnswerDto> answerDtoList = new ArrayList<>();
            Question existingQuestion = optionalQuestion.get();
            Optional<QuestionVote> optionalQuestionVote = existingQuestion.getQuestionVoteList().stream().filter(vote -> vote.getUser().getId().equals(userId)).findFirst();
            QuestionDTO questionDTO = optionalQuestion.get().getQuestionDto();
            questionDTO.setVoted(0);
            if (optionalQuestionVote.isPresent()) {
                if (optionalQuestionVote.get().getVoteType().equals(VoteType.UPVOTE)) {
                    questionDTO.setVoted(1);
                } else {
                    questionDTO.setVoted(-1);
                }
            }
            singleQuestionDto.setQuestionDTO(questionDTO);
            List<Answers> answersList = answerRepository.findAllByQuestionId(questionId);
            for (Answers answer : answersList) {
                if (answer.isApproved()) {
                    singleQuestionDto.getQuestionDTO().setHasApprovedAnswer(true);
                }
                AnswerDto answerDto = answer.getAnswerDto();
                Optional<AnswerVote> optionalAnswerVote = answer.getAnswerVoteList().stream().filter(answerVote -> answerVote.getUser().getId().equals(userId)).findFirst();
                if (optionalAnswerVote.isPresent()) {
                    if (optionalAnswerVote.get().getVoteType().equals(VoteType.UPVOTE)) {
                        answerDto.setVoted(1);
                    } else {
                        answerDto.setVoted(-1);
                    }
                }
                answerDto.setFile(imageRepository.findByAnswer(answer));
                answerDto.setBase64(getBase64Image(answer));
                answerDtoList.add(answerDto);

                //Get Comment Dto List
                List<CommentDto> commentDtoList = new ArrayList<>();
                answer.getCommentList().forEach(comment -> {
                    CommentDto commentDto = comment.getCommentDto();
                    commentDtoList.add(commentDto);
                });
                answerDto.setCommentDtoList(commentDtoList);
            }
            singleQuestionDto.setAnswerDtoList(answerDtoList);
            return singleQuestionDto;
        }
        return null;
    }

    @Override
    public AllQuestionResponseDto getAllQuestionsByUserId(Long userId, PageRequest pageRequestData) {
        Page<Question> questionPage = questionRepository.findAllByUserId(userId, pageRequestData);

        AllQuestionResponseDto allQuestionResponseDto = new AllQuestionResponseDto();
        allQuestionResponseDto.setQuestionDTOList(questionPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList()));
        allQuestionResponseDto.setPageNumber(questionPage.getNumber() + 1);
        allQuestionResponseDto.setPageSize(questionPage.getSize());
        allQuestionResponseDto.setTotalElements(questionPage.getTotalElements());
        allQuestionResponseDto.setTotalPages(questionPage.getTotalPages());
        allQuestionResponseDto.setLastPage(questionPage.isLast());
        return allQuestionResponseDto;
    }

    @Override
    public QuestionSearchResponseDto searchQuestionByTitle(String title, PageRequest pageRequestData) {
        Page<Question> questionPage;
        if (title == null || title.isEmpty()) {
            questionPage = questionRepository.findAll(pageRequestData);
        } else {
            questionPage = questionRepository.findAllByTitleContaining(title, pageRequestData);
        }
        QuestionSearchResponseDto questionSearchResponseDto = new QuestionSearchResponseDto();
        questionSearchResponseDto.setQuestionDtoList(questionPage.getContent().stream().map(Question::getQuestionDto).collect(Collectors.toList()));
        questionSearchResponseDto.setPageNumber(questionPage.getNumber() + 1);
        questionSearchResponseDto.setPageSize(questionPage.getSize());
        questionSearchResponseDto.setTotalElements(questionPage.getTotalElements());
        questionSearchResponseDto.setTotalPages(questionPage.getTotalPages());
        questionSearchResponseDto.setLastPage(questionPage.isLast());
        return questionSearchResponseDto;

    }

    private String getBase64Image(Answers answer) {
        Optional<Image> optionalImage = Optional.ofNullable(imageRepository.findByAnswer(answer));

        if (optionalImage.isPresent()) {
            Image image = optionalImage.get();
            byte[] imageData = image.getData();
            return Base64.getEncoder().encodeToString(imageData);
        } else {
            return null;
        }
    }
}
