package com.tiago.UmPoucoDeTudo.controller;

import java.util.List;
import java.util.Optional;

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
import com.tiago.UmPoucoDeTudo.service.StoryService;

@RestController
@RequestMapping("/stories")
@CrossOrigin(origins = "http://localhost:5173")
public class StoryController {
    
    private final StoryService storyService;
    public StoryController(StoryService storyService){
        this.storyService = storyService;
    }

    @GetMapping(path = "/getAll")
    public List<Story> getAllStories(){
        return storyService.getAll();
    }

    @GetMapping(path = "/getById/{id}")
    public Optional<Story> getStoryById(@PathVariable Long id){
        return storyService.getById(id);
    }

    @PostMapping(path = "/new")
    public Story createNewStory(@RequestBody Story story){
        return storyService.save(story);
    }

    @PutMapping(path = "/update")
    public Story updateStory(@RequestBody Story story){
        return storyService.save(story);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteStory(@PathVariable Long id){
        storyService.deleteStoryById(id);
    }

}
