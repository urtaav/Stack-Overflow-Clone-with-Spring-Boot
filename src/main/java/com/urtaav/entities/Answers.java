package com.urtaav.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.urtaav.dtos.AnswerDto;
import com.urtaav.dtos.CommentDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name = "body", length = 512)
    private String body;

    private Date createdDate;
    private boolean approved = false;
    private Integer voteCount = 0;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "answer",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> commentList;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question;

    @OneToMany(mappedBy = "answer",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AnswerVote> answerVoteList;

    public AnswerDto getAnswerDto() {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(id);
        answerDto.setBody(body);
        answerDto.setCreateDate(createdDate);
        answerDto.setUserId(user.getId());
        answerDto.setQuestionId(question.getId());
        answerDto.setUsername(user.getName());
        answerDto.setApproved(approved);
        answerDto.setVoteCount(voteCount);
        return answerDto;
    }
}
