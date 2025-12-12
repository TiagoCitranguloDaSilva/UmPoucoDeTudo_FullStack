package com.tiago.UmPoucoDeTudo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Tag getById(Long id){
        return tagRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Etiqueta não encontrada!"));
    }

    public Tag save(Tag tag){
        return tagRepository.save(tag);
    }

    public void deleteTagById(Long id){
        tagRepository.deleteById(id);
    }


}
