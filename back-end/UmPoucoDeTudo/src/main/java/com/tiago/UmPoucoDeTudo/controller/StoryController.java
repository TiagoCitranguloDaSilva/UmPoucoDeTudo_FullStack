package com.tiago.UmPoucoDeTudo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPostRequestBody;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPutRequestBody;
import com.tiago.UmPoucoDeTudo.service.StoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/stories")
@CrossOrigin(origins = "http://localhost:5173")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<Story>> getAllStories() {
        return ResponseEntity.ok(storyService.getAll());
    }

    @GetMapping(path = "/getById/{id}")
    public ResponseEntity<Story> getStoryById(@PathVariable Long id) {
        return ResponseEntity.ok(storyService.getById(id));
    }

    @PostMapping(path = "/new")
    public ResponseEntity<Story> createNewStory(@RequestBody @Valid StoryPostRequestBody story) {
        return ResponseEntity.ok(storyService.createStory(story));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Void> updateStory(@RequestBody @Valid StoryPutRequestBody story) {
        storyService.replace(story);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteStory(@PathVariable Long id) {
        storyService.deleteStoryById(id);
    }

}
