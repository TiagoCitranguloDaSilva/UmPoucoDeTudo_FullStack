package com.tiago.UmPoucoDeTudo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.repository.StoryRepository;

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

    public Story save(Story story) {
        return storyRepository.save(story);
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
