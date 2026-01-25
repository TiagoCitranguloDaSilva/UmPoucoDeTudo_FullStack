package com.tiago.UmPoucoDeTudo.repository;

import com.tiago.UmPoucoDeTudo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiago.UmPoucoDeTudo.model.Tag;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
    List<Tag> findByUser(User user);

     Optional<Tag> findByIdAndUser(Long id, User user);
}
