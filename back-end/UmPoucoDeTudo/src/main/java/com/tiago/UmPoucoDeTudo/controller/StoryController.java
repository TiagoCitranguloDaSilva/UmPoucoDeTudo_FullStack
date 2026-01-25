package com.tiago.UmPoucoDeTudo.controller;

import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPostRequestBody;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPutRequestBody;
import com.tiago.UmPoucoDeTudo.responses.StoryResponse;
import com.tiago.UmPoucoDeTudo.service.StoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stories")
@CrossOrigin(origins = "http://localhost:5173")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<List<StoryResponse>> getAllStories() {
        return ResponseEntity.ok(storyService.getAll());
    }

    @GetMapping(path = "/getById/{id}")
    public ResponseEntity<StoryResponse> getStoryById(@PathVariable Long id) {
        return ResponseEntity.ok(storyService.getById(id));
    }

    @PostMapping(path = "/new")
    public ResponseEntity<StoryResponse> createNewStory(@RequestBody @Valid StoryPostRequestBody story) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storyService.createStory(story));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Void> updateStory(@RequestBody @Valid StoryPutRequestBody story) {
        storyService.replace(story);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long id) {
        storyService.deleteStoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
