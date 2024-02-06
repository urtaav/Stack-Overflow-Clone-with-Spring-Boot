package com.urtaav.repositories;

import com.urtaav.entities.Answers;
import com.urtaav.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findByAnswer(Answers answer);
}
