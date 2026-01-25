package com.tiago.UmPoucoDeTudo.service;

import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.model.User;
import com.tiago.UmPoucoDeTudo.repository.TagRepository;
import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPostRequestBody;
import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPutRequestBody;
import com.tiago.UmPoucoDeTudo.responses.TagResponse;
import com.tiago.UmPoucoDeTudo.responses.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    private final StoryService storyService;

    public TagService(TagRepository tagRepository, StoryService storyService) {
        this.tagRepository = tagRepository;
        this.storyService = storyService;
    }

    public List<TagResponse> getAll(User user) {
        return tagRepository.findByUser(user).stream()
                .map(tag -> new TagResponse(
                        tag.getId(),
                        tag.getName(),
                        tag.getCreated_at(),
                        tag.getStories().stream().map(storyService::toDTO).toList(),
                        new UserResponse(tag.getUser().getName())
                )).toList();
    }

    public TagResponse getById(Long id, User user) {
        Tag tag = tagRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Etiqueta não encontrada!"));

        return new TagResponse(
                tag.getId(),
                tag.getName(),
                tag.getCreated_at(),
                tag.getStories().stream().map(storyService::toDTO).toList(),
                new UserResponse(tag.getUser().getName())
        );
    }

    public TagResponse createTag(TagPostRequestBody requestTag, User user) {
        Tag tag = Tag.builder()
                .name(requestTag.getName())
                .user(user)
                .build();
        tagRepository.save(tag);
        return new TagResponse(
                tag.getId(),
                tag.getName(),
                tag.getCreated_at(),
                tag.getStories().stream().map(storyService::toDTO).toList(),
                new UserResponse(tag.getUser().getName())
        );
    }

    public void replace(TagPutRequestBody requestTag, User user) {

        getById(requestTag.getId(), user);

        Tag tag = Tag.builder()
                .id(requestTag.getId())
                .name(requestTag.getName())
                .user(user)
                .created_at(requestTag.getCreated_at())
                .build();
        tagRepository.save(tag);
    }

    public void deleteTagById(Long id, User user) {

        getById(id, user);

        tagRepository.deleteById(id);
    }


}
