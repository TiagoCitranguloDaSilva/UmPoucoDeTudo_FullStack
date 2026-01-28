package com.tiago.UmPoucoDeTudo.service;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.repository.StoryRepository;
import com.tiago.UmPoucoDeTudo.responses.StoryResponse;
import com.tiago.UmPoucoDeTudo.util.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Teste do service StoryService")
public class StoryServiceTest {

    @InjectMocks
    private StoryService storyService;

    @Mock
    private StoryRepository storyRepositoryMock;

    @Test
    @DisplayName("Teste: pegar todas as stories existentes")
    void getAll_ReturnListOfStories_WhenSuccessful() {

        BDDMockito.when(storyRepositoryMock.findAll()).thenReturn(List.of(StoryTesterCreator.createStory(TagTesterCreator.createTagWithId())));

        List<StoryResponse> stories = storyService.getAll();

        Assertions.assertThat(stories)
                .isNotNull()
                .isNotEmpty();

        Story story = StoryTesterCreator.createStory(TagTesterCreator.createTagWithId());

        Assertions.assertThat(stories.getFirst())
                .isNotNull()
                .isEqualTo(StoryResponseTesterCreator.convertToStoryResponse(story));

    }

    @Test
    @DisplayName("Teste: pegar tag pelo id")
    void getById_ReturnStory_WhenSuccessful() {

        BDDMockito.when(storyRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(StoryTesterCreator.createStory()));

        StoryResponse story = storyService.getById(1L);

        Assertions.assertThat(story)
                .isNotNull()
                .isEqualTo(StoryResponseTesterCreator.convertToStoryResponse(StoryTesterCreator.createStory()));

    }

    @Test
    @DisplayName("Teste: criar nova story")
    void createStory_ReturnStory_WhenSuccessful() {

        BDDMockito.when(storyRepositoryMock.save(ArgumentMatchers.any(Story.class)))
                .thenReturn(StoryTesterCreator.createStory());

        StoryResponse story = storyService.createStory(StoryPostRequestBodyTesterCreator.createStoryPostRequestBody());

        Assertions.assertThat(story)
                .isNotNull()
                .isEqualTo(StoryResponseTesterCreator.convertToStoryResponse(StoryTesterCreator.createStory()));

    }

    @Test
    @DisplayName("Teste: atualizar story")
    void update_returnVoid_WhenSuccessful() {

        Mockito.when(storyRepositoryMock.findById(StoryTesterCreator.getDefaultId()))
                .thenReturn(Optional.of(StoryTesterCreator.createStory()));

        storyService.replace(StoryPutRequestBodyTesterCreator.createStoryPutRequestBody(
                StoryTesterCreator.getDefaultId(),
                "Updated story",
                StoryTesterCreator.getDefaultStory(),
                TagTesterCreator.createTag()
        ));

        BDDMockito.verify(storyRepositoryMock).save(ArgumentMatchers.any(Story.class));

    }

    @Test
    @DisplayName("Teste: apagar story")
    void deleteStoryById_returnVoid_WhenSuccessful() {

        Tag createdTag = TagTesterCreator.createTag();
        Story createdStory = StoryTesterCreator.createStory(createdTag);

        createdTag.setStories(new ArrayList<>());
        createdTag.getStories().add(createdStory);

        Mockito.when(storyRepositoryMock.findById(StoryTesterCreator.getDefaultId()))
                .thenReturn(Optional.of(createdStory));

        storyService.deleteStoryById(StoryTesterCreator.getDefaultId());

        BDDMockito.verify(storyRepositoryMock).delete(ArgumentMatchers.any(Story.class));

    }
}
