package com.tiago.UmPoucoDeTudo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:5173")
public class TagController {
    
    private final TagService tagService;
    public TagController(TagService tagService){
        this.tagService = tagService;
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<Tag>> getAllTags(){
        return ResponseEntity.ok(tagService.getAll());
    }

    @GetMapping(path = "/getById/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id){
        return ResponseEntity.ok(tagService.getById(id));
    }

    @PostMapping(path = "/new")
    public ResponseEntity<Tag> createNewTag(@RequestBody Tag tag){
        return ResponseEntity.ok(tagService.save(tag));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Tag> updateTag(@RequestBody Tag tag){
        return ResponseEntity.ok(tagService.save(tag));
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteTag(@PathVariable Long id){
        tagService.deleteTagById(id);
    }

}
