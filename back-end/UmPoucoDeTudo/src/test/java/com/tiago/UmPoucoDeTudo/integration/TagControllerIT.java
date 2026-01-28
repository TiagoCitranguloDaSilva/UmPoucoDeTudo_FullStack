package com.tiago.UmPoucoDeTudo.integration;

import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.model.User;
import com.tiago.UmPoucoDeTudo.repository.TagRepository;
import com.tiago.UmPoucoDeTudo.repository.UserRepository;
import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPostRequestBody;
import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPutRequestBody;
import com.tiago.UmPoucoDeTudo.responses.TagResponse;
import com.tiago.UmPoucoDeTudo.service.TokenService;
import com.tiago.UmPoucoDeTudo.util.*;
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

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureTestRestTemplate
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Teste: integração do TagController")
public class TagControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Test
    @DisplayName("Teste: integração do endpoint '/tags/getAll'")
    void getAllTags_returnListOfTags_WhenSuccessful() {

        User createdUser = userRepository.save(UserTesterCreator.createUser());

        Tag tag = TagTesterCreator.createTag(createdUser, new ArrayList<>());
        tagRepository.save(tag);

        HttpHeaders header = createAuthorizationHeader(createdUser);

        HttpEntity<Void> entity = new HttpEntity<>(header);

        ResponseEntity<List<TagResponse>> response = testRestTemplate.exchange(
                "/tags/getAll",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                });

        List<TagResponse> tagList = response.getBody();

        Assertions.assertThat(tagList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(tagList.getFirst()).isEqualTo(TagResponseTesterCreator.convertToTagResponse(tag));

    }

    @Test
    @DisplayName("Teste: integração do endpoint '/tags/getById/{id}'")
    void getTagById_ReturnTag_WhenSuccessful() {

        User user = userRepository.save(UserTesterCreator.createUser());

        Tag createdTag = TagTesterCreator.createTag(user, new ArrayList<>());

        tagRepository.save(createdTag);

        HttpHeaders header = createAuthorizationHeader(user);

        HttpEntity<Void> entity = new HttpEntity<>(header);

        ResponseEntity<TagResponse> response = testRestTemplate.exchange(
                "/tags/getById/{id}",
                HttpMethod.GET,
                entity,
                TagResponse.class,
                createdTag.getId());

        TagResponse tag = response.getBody();
        Assertions.assertThat(tag)
                .isNotNull()
                .isEqualTo(TagResponseTesterCreator.convertToTagResponse(createdTag));

    }

    @Test
    @DisplayName("Teste: integração do endpoint '/tags/new'")
    void createNewTag_returnTag_WhenSuccessful() {

        User user = userRepository.save(UserTesterCreator.createUser());

        HttpHeaders header = createAuthorizationHeader(user);
        header.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TagPostRequestBody> entity = new HttpEntity<>(TagPostRequestBodyTesterCreator.createTagPostRequestBody(), header);

        ResponseEntity<TagResponse> tagResponseEntity = testRestTemplate.exchange(
                "/tags/new",
                HttpMethod.POST,
                entity,
                TagResponse.class
        );

        Assertions.assertThat(tagResponseEntity).isNotNull();
        Assertions.assertThat(tagResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(tagResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("Teste: integração do endpoint '/tags/update'")
    void updateTag_ReturnVoid_WhenSuccessful() {

        User user = userRepository.save(UserTesterCreator.createUser());

        HttpHeaders header = createAuthorizationHeader(user);
        header.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TagPutRequestBody> entity = new HttpEntity<>(TagPutRequestBodyTesterCreator.createTagPutRequestBody(), header);

        Tag createdTag = tagRepository.save(TagTesterCreator.createTag(user));

        createdTag.setName("updatedTag");
        ResponseEntity<Void> tagResponseEntity = testRestTemplate.exchange(
                "/tags/update",
                HttpMethod.PUT,
                entity,
                Void.class
        );

        Assertions.assertThat(tagResponseEntity).isNotNull();
        Assertions.assertThat(tagResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("Teste: integração do endpoint '/tags/delete/{id}'")
    void deleteTag_ReturnVoid_WhenSuccessful() {

        User user = userRepository.save(UserTesterCreator.createUser());

        HttpHeaders header = createAuthorizationHeader(user);

        HttpEntity<Void> entity = new HttpEntity<>(header);

        Tag createdTag = tagRepository.save(TagTesterCreator.createTag(user));
        ResponseEntity<Void> tagResponseEntity = testRestTemplate.exchange(
                "/tags/delete/{id}",
                HttpMethod.DELETE,
                entity,
                Void.class,
                createdTag.getId());

        Assertions.assertThat(tagResponseEntity).isNotNull();
        Assertions.assertThat(tagResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private HttpHeaders createAuthorizationHeader(User user) {

        String token = tokenService.generateToken(user);

        HttpHeaders header = new HttpHeaders();

        header.setBearerAuth(token);

        return header;
    }
}
