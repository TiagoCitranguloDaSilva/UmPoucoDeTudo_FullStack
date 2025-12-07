package com.tiago.UmPoucoDeTudo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiago.UmPoucoDeTudo.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
    
}
