package com.urtaav.repositories;

import com.urtaav.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository  extends PagingAndSortingRepository<Question, Long>, JpaRepository<Question,Long> {
    Page<Question> findAllByUserId(Long userId,PageRequest pageRequestData);

    Page<Question> findAllByTitleContaining(String title, PageRequest pageRequestData);
}
