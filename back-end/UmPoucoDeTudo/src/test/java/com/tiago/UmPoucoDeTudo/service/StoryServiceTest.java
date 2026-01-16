package com.tiago.UmPoucoDeTudo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.repository.StoryRepository;
import com.tiago.UmPoucoDeTudo.util.StoryPostRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.StoryPutRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.StoryTesterCreator;
import com.tiago.UmPoucoDeTudo.util.TagTesterCreator;

@ExtendWith(MockitoExtension.class)
@DisplayName("Teste do service StoryService")
public class StoryServiceTest {

    @InjectMocks
    private StoryService storyService;

    @Mock
    private StoryRepository storyRepositoryMock;

    @Test
    @DisplayName("Teste: pegar todas as stories existentes")
    void getAll_ReturnListOfStories_WhenSuccessful(){

        BDDMockito.when(storyRepositoryMock.findAll()).thenReturn(List.of(StoryTesterCreator.createStory()));

        List<Story> stories = storyService.getAll();

        Assertions.assertThat(stories)
                .isNotNull()
                .isNotEmpty();

        Assertions.assertThat(stories.get(0))
                .isNotNull()
                .isEqualTo(StoryTesterCreator.createStory());

    }

    @Test
    @DisplayName("Teste: pegar tag pelo id")
    void getById_ReturnStory_WhenSuccessful(){

        BDDMockito.when(storyRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(StoryTesterCreator.createStory()));

        Story story = storyService.getById(StoryTesterCreator.getDefaultId());
        
        Assertions.assertThat(story)
                .isNotNull()
                .isEqualTo(StoryTesterCreator.createStory());

    }

    @Test
    @DisplayName("Teste: criar nova story")
    void createStory_ReturnStory_WhenSuccessful(){

        BDDMockito.when(storyRepositoryMock.save(ArgumentMatchers.any(Story.class)))
                        .thenReturn(StoryTesterCreator.createStory());

        Story story = storyService.createStory(StoryPostRequestBodyTesterCreator.createStoryPostRequestBody());

        Assertions.assertThat(story)
                .isNotNull()
                .isEqualTo(StoryTesterCreator.createStory());

    }

    @Test
    @DisplayName("Teste: atualizar story")
    void update_returnVoid_WhenSuccessful(){

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
    void deleteStoryById_returnVoid_WhenSuccessful(){

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
