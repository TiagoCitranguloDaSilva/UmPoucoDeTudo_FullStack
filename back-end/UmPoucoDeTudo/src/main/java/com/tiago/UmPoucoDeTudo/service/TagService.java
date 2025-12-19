package com.tiago.UmPoucoDeTudo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.repository.TagRepository;
import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPostRequestBody;
import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPutRequestBody;

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

    public Tag createTag(TagPostRequestBody requestTag){
        Tag tag = Tag.builder()
            .name(requestTag.getName())
        .build();
        return tagRepository.save(tag);
    }

    public void replace(TagPutRequestBody requestTag){
        Tag tag = Tag.builder()
            .id(requestTag.getId())
            .name(requestTag.getName())
            .created_at(requestTag.getCreated_at())
        .build();
        tagRepository.save(tag);
    }

    public void deleteTagById(Long id){
        tagRepository.deleteById(id);
    }


}
