package com.tiago.UmPoucoDeTudo.controller;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPutRequestBody;
import com.tiago.UmPoucoDeTudo.service.StoryService;
import com.tiago.UmPoucoDeTudo.service.TagService;
import com.tiago.UmPoucoDeTudo.util.StoryPostRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.StoryPutRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.StoryTesterCreator;

@ExtendWith(MockitoExtension.class)
public class StoryControllerTest {

    @InjectMocks
    private StoryController storyController;

    @Mock
    private StoryService storyServiceMock;

    @Mock
    private TagService tagServiceMock;

    @Test
    @DisplayName("Teste: teste do endpoint '/getAll'")
    void getAllStories_ReturnAListOfStories_WhenSuccessful() {

        ArrayList<Story> storyList = new ArrayList<Story>();
        storyList.add(StoryTesterCreator.createStory());

        BDDMockito.when(storyServiceMock.getAll())
                .thenReturn(storyList);

        List<Story> stories = storyController.getAllStories().getBody();

        Assertions.assertThat(stories)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(stories.get(0))
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(StoryTesterCreator.createStory());

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/getById/{id}'")
    void getStoryById_ReturnStory_WhenSuccessful() {

        BDDMockito.when(storyServiceMock.getById(StoryTesterCreator.getDefaultId()))
                .thenReturn(StoryTesterCreator.createStory());

        Story story = storyController.getStoryById(StoryTesterCreator.getDefaultId()).getBody();

        Assertions.assertThat(story)
                .isNotNull()
                .usingRecursiveComparison() 
                .isEqualTo(StoryTesterCreator.createStory());


    }

    @Test
    @DisplayName("Teste: teste do endpoint '/new'")
    void createNewTag_ReturnTagWhenSuccessful() {

        BDDMockito.when(storyServiceMock.createStory(StoryPostRequestBodyTesterCreator.createStoryPostRequestBody()))
                .thenReturn(StoryTesterCreator.createStory());

        Story story = storyController.createNewStory(StoryPostRequestBodyTesterCreator.createStoryPostRequestBody())
                .getBody();

        Assertions.assertThat(story)
                .isNotNull()
                .usingRecursiveComparison() 
                .isEqualTo(StoryTesterCreator.createStory());

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/update'")
    void updateStory_ReturnNoContent_WhenSuccessful(){

        ResponseEntity<Void> response = storyController.updateStory(StoryPutRequestBodyTesterCreator.createStoryPutRequestBody());

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        BDDMockito.verify(storyServiceMock).replace(ArgumentMatchers.any(StoryPutRequestBody.class));

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/delete/{id}'")
    void deleteTag_ReturnVoid_WhenSuccessful(){

        storyController.deleteStory(StoryTesterCreator.getDefaultId());

        BDDMockito.verify(storyServiceMock).deleteStoryById(ArgumentMatchers.anyLong());

    }

}
