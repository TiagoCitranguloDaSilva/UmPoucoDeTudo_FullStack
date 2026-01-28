package com.tiago.UmPoucoDeTudo.service;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.repository.StoryRepository;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPostRequestBody;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPutRequestBody;
import com.tiago.UmPoucoDeTudo.responses.StoryResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StoryService {

    private final StoryRepository storyRepository;

    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    public List<StoryResponse> getAll() {
        return storyRepository.findAll().stream().map(this::toDTO).toList();
    }

    public StoryResponse getById(Long id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "História não encontrada!"));
        return this.toDTO(story);
    }

    public StoryResponse createStory(StoryPostRequestBody requestStory) {
        Story story = Story.builder()
                .title(requestStory.getTitle())
                .story(requestStory.getStory())
                .tag(requestStory.getTag())
                .build();
        return this.toDTO(storyRepository.save(story));
    }

    public void replace(StoryPutRequestBody requestStory) {

        getById(requestStory.getId());

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
        Story story = storyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "História não encontrada!"));

        Tag tag = story.getTag();
        tag.getStories().remove(story);

        storyRepository.delete(story);
    }

    public StoryResponse toDTO(Story story) {
        return new StoryResponse(story.getId(), story.getTitle(), story.getStory(), story.getTag().getId(), story.getCreated_at());
    }

}
