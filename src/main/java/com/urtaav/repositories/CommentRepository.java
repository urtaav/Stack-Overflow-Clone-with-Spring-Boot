package com.urtaav.repositories;

import com.urtaav.entities.Comment;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository  extends JpaRepository<Comment, Id> {
}
