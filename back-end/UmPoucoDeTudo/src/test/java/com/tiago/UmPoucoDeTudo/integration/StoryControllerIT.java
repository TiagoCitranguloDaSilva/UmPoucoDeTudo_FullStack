package com.tiago.UmPoucoDeTudo.integration;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.model.User;
import com.tiago.UmPoucoDeTudo.repository.StoryRepository;
import com.tiago.UmPoucoDeTudo.repository.TagRepository;
import com.tiago.UmPoucoDeTudo.repository.UserRepository;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPostRequestBody;
import com.tiago.UmPoucoDeTudo.requests.storyRequests.StoryPutRequestBody;
import com.tiago.UmPoucoDeTudo.responses.StoryResponse;
import com.tiago.UmPoucoDeTudo.service.TokenService;
import com.tiago.UmPoucoDeTudo.util.story.StoryPostRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.story.StoryPutRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.story.StoryResponseTesterCreator;
import com.tiago.UmPoucoDeTudo.util.story.StoryTesterCreator;
import com.tiago.UmPoucoDeTudo.util.tag.TagTesterCreator;
import com.tiago.UmPoucoDeTudo.util.user.UserTesterCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureTestRestTemplate
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Teste: integração do StoryController")
public class StoryControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("Teste: integração do endpoint '/stories/getAll'")
    void getAllStories_ReturnListOfStories_WhenSuccessful() {

        User user = userRepository.save(UserTesterCreator.createUser());

        HttpHeaders header = createAuthorizationHeader(user);

        Story story = createStoryWithTagAndUserSaved(user);

        storyRepository.save(story);

        ResponseEntity<List<StoryResponse>> response = testRestTemplate.exchange(
                "/stories/getAll",
                HttpMethod.GET,
                new HttpEntity<>(header),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getBody())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(List.of(StoryResponseTesterCreator.convertToStoryResponse(story)));

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("Teste: integração do endpoint '/stories/getById/{id}'")
    void getStoryById_ReturnStory_WhenSuccessful() {

        User user = userRepository.save(UserTesterCreator.createUser());

        Story story = createStoryWithTagAndUserSaved(user);

        storyRepository.save(story);

        HttpHeaders header = createAuthorizationHeader(user);

        ResponseEntity<StoryResponse> response = testRestTemplate.exchange(
                "/stories/getById/{id}",
                HttpMethod.GET,
                new HttpEntity<>(header),
                StoryResponse.class,
                story.getId()
        );

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getBody())
                .isNotNull()
                .isEqualTo(StoryResponseTesterCreator.convertToStoryResponse(story));

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("Teste: integração do endpoint '/stories/new'")
    void createNewStory_ReturnStory_WhenSuccessful() {

        User user = userRepository.save(UserTesterCreator.createUser());

        HttpHeaders header = createAuthorizationHeader(user);

        Tag tag = tagRepository.save(TagTesterCreator.createTag(user));

        header.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<StoryPostRequestBody> entity = new HttpEntity<>(StoryPostRequestBodyTesterCreator.createStoryPostRequestBody(tag), header);

        ResponseEntity<StoryResponse> response = testRestTemplate.exchange(
                "/stories/new",
                HttpMethod.POST,
                entity,
                StoryResponse.class
        );

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getBody()).isNotNull();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    @DisplayName("Teste: integração do endpoint '/stories/update'")
    void updateStory_ReturnVoid_WhenSuccessful() {

        User user = userRepository.save(UserTesterCreator.createUser());

        Tag tag = tagRepository.save(TagTesterCreator.createTag(user));

        HttpHeaders header = createAuthorizationHeader(user);

        header.setContentType(MediaType.APPLICATION_JSON);

        Story story = storyRepository.save(StoryTesterCreator.createStory(tag));

        StoryPutRequestBody storyPutRequestBody = StoryPutRequestBodyTesterCreator.createStoryPutRequestBody(tag);

        storyPutRequestBody.setCreated_at(story.getCreated_at());

        storyPutRequestBody.setTitle("UpdatedStory");

        HttpEntity<StoryPutRequestBody> entity = new HttpEntity<>(storyPutRequestBody, header);

        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/stories/update",
                HttpMethod.PUT,
                entity,
                Void.class
        );

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("Teste: integração do endpoint '/stories/delete/{id}'")
    void deleteStory_ReturnVoid_WhenSuccessful() {

        User user = userRepository.save(UserTesterCreator.createUser());

        Story story = storyRepository.save(createStoryWithTagAndUserSaved(user));

        HttpHeaders header = createAuthorizationHeader(user);

        HttpEntity<Void> entity = new HttpEntity<>(header);

        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/stories/delete/{id}",
                HttpMethod.DELETE,
                entity,
                Void.class,
                story.getId()
        );

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    private HttpHeaders createAuthorizationHeader(User user) {

        String token = tokenService.generateToken(user);

        HttpHeaders header = new HttpHeaders();

        header.setBearerAuth(token);

        return header;
    }

    private Story createStoryWithTagAndUserSaved(User user) {

        Tag tag = tagRepository.save(TagTesterCreator.createTag(user));

        return StoryTesterCreator.createStory(tag);

    }

}
