package com.tiago.UmPoucoDeTudo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.repository.StoryRepository;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPostRequestBody;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPutRequestBody;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class StoryService {

    private final StoryRepository storyRepository;

    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    public List<Story> getAll() {
        return storyRepository.findAll();
    }

    public Story getById(Long id) {
        return storyRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public Story createStory(StoryPostRequestBody requestStory) {
        Story story = Story.builder()
            .title(requestStory.getTitle())
            .story(requestStory.getStory())
            .created_at(requestStory.getCreated_at())
            .tag(requestStory.getTag())
        .build();
        return storyRepository.save(story);
    }

    public void replace(StoryPutRequestBody requestStory){

        Story story = Story.builder()
            .id(requestStory.getId())
            .title(requestStory.getTitle())
            .story(requestStory.getStory())
            .created_at(requestStory.getCreated_at())
            .tag(requestStory.getTag())
        .build();
        storyRepository.save(story);

    }

    @Transactional
    public void deleteStoryById(Long id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Story não encontrada"));

        Tag tag = story.getTag();
        tag.getStories().remove(story);

        storyRepository.delete(story);
    }

}
