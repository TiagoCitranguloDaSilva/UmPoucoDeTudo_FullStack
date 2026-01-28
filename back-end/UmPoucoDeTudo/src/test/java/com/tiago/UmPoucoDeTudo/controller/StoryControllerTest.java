package com.tiago.UmPoucoDeTudo.controller;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.repository.UserRepository;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPutRequestBody;
import com.tiago.UmPoucoDeTudo.responses.StoryResponse;
import com.tiago.UmPoucoDeTudo.service.StoryService;
import com.tiago.UmPoucoDeTudo.util.StoryPostRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.StoryPutRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.StoryResponseTesterCreator;
import com.tiago.UmPoucoDeTudo.util.StoryTesterCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StoryControllerTest {

    @InjectMocks
    private StoryController storyController;

    @Mock
    private StoryService storyServiceMock;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Teste: teste do endpoint '/getAll'")
    void getAllStories_ReturnAListOfStories_WhenSuccessful() {

        ArrayList<Story> storyList = new ArrayList<>();
        storyList.add(StoryTesterCreator.createStory());

        BDDMockito.when(storyServiceMock.getAll())
                .thenReturn(StoryResponseTesterCreator.convertToStoryResponse(storyList));

        List<StoryResponse> stories = storyController.getAllStories().getBody();

        Assertions.assertThat(stories)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(stories.getFirst())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(StoryTesterCreator.createStory());

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/getById/{id}'")
    void getStoryById_ReturnStory_WhenSuccessful() {

        BDDMockito.when(storyServiceMock.getById(StoryTesterCreator.getDefaultId()))
                .thenReturn(StoryResponseTesterCreator.convertToStoryResponse(StoryTesterCreator.createStory()));

        StoryResponse story = storyController.getStoryById(StoryTesterCreator.getDefaultId()).getBody();

        Assertions.assertThat(story)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(StoryTesterCreator.createStory());


    }

    @Test
    @DisplayName("Teste: teste do endpoint '/new'")
    void createNewStory_ReturnStoryWhenSuccessful() {

        BDDMockito.when(storyServiceMock.createStory(StoryPostRequestBodyTesterCreator.createStoryPostRequestBody()))
                .thenReturn(StoryResponseTesterCreator.convertToStoryResponse(StoryTesterCreator.createStory()));

        StoryResponse story = storyController.createNewStory(StoryPostRequestBodyTesterCreator.createStoryPostRequestBody())
                .getBody();

        Assertions.assertThat(story)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(StoryTesterCreator.createStory());

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/update'")
    void updateStory_ReturnNoContent_WhenSuccessful() {

        ResponseEntity<Void> response = storyController.updateStory(StoryPutRequestBodyTesterCreator.createStoryPutRequestBody());

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        BDDMockito.verify(storyServiceMock).replace(ArgumentMatchers.any(StoryPutRequestBody.class));

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/delete/{id}'")
    void deleteTag_ReturnVoid_WhenSuccessful() {

        ResponseEntity<Void> response = storyController.deleteStory(StoryTesterCreator.getDefaultId());

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        BDDMockito.verify(storyServiceMock).deleteStoryById(ArgumentMatchers.anyLong());

    }

}
