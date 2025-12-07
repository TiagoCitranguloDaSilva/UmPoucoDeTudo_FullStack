package com.tiago.UmPoucoDeTudo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.service.TagService;

@RestController
@RequestMapping("/tags")
public class TagController {
    
    private final TagService tagService;
    public TagController(TagService tagService){
        this.tagService = tagService;
    }

    @GetMapping(path = "/getAll")
    public List<Tag> getAllTags(){
        return tagService.getAll();
    }

    @GetMapping(path = "/getById/{id}")
    public Optional<Tag> getTagById(@PathVariable Long id){
        return tagService.getById(id);
    }

    @PostMapping(path = "/new")
    public Tag createNewTag(@RequestBody Tag tag){
        return tagService.save(tag);
    }

    @PutMapping(path = "/update")
    public Tag updateTag(@RequestBody Tag tag){
        return tagService.save(tag);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteTag(@PathVariable Long id){
        tagService.deleteTagById(id);
    }

}
