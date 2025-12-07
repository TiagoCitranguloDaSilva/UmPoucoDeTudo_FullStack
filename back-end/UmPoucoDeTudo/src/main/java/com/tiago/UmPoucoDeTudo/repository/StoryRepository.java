package com.tiago.UmPoucoDeTudo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiago.UmPoucoDeTudo.model.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long>{
    
}
