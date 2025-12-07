package com.tiago.UmPoucoDeTudo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.repository.TagRepository;

@Service
public class TagService {
    
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAll(){
        return tagRepository.findAll();
    }

    public Optional<Tag> getById(Long id){
        return tagRepository.findById(id);
    }

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }

    public void deleteTagById(Long id){
        tagRepository.deleteById(id);
    }


}
