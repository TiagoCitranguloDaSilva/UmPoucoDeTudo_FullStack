package com.tiago.UmPoucoDeTudo.integration;

import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.repository.TagRepository;
import com.tiago.UmPoucoDeTudo.util.TagPostRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.TagTesterCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

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

    @Test
    @DisplayName("Teste: integração do endpoint '/tags/getAll'")
    void getAllTags_returnListOfTags_WhenSuccessful() {

        tagRepository.save(TagTesterCreator.createTag());

        List<Tag> tagsList = testRestTemplate.exchange("/tags/getAll", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Tag>>() {

                }).getBody();

        Assertions.assertThat(tagsList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(tagsList.getFirst()).isEqualTo(TagTesterCreator.createTagWithId());

    }

    @Test
    @DisplayName("Teste: integração do endpoint '/tags/getById/{id}'")
    void getTagById_ReturnTag_WhenSuccessful(){

        Tag createdTag = TagTesterCreator.createTag();

        tagRepository.save(createdTag);

        Tag tag = testRestTemplate.getForObject("/tags/getById/{id}", Tag.class, createdTag.getId());

        Assertions.assertThat(tag)
                .isNotNull()
                .isEqualTo(createdTag);

    }

    @Test
    @DisplayName("Teste: integração do endpoint '/tags/new'")
    void createNewTag_returnTag_WhenSuccessful(){

        ResponseEntity<Tag> tagResponseEntity = testRestTemplate.postForEntity("/tags/new", TagPostRequestBodyTesterCreator.createTagPostRequestBody(), Tag.class);
        Assertions.assertThat(tagResponseEntity).isNotNull();
        Assertions.assertThat(tagResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(tagResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("Teste: integração do endpoint '/tags/update'")
    void updateTag_ReturnVoid_WhenSuccessful(){

        Tag createdTag = tagRepository.save(TagTesterCreator.createTag());

        createdTag.setName("updatedTag");
        ResponseEntity<Void> tagResponseEntity = testRestTemplate.exchange("/tags/update", HttpMethod.PUT, new HttpEntity<>(createdTag), Void.class);

        Assertions.assertThat(tagResponseEntity).isNotNull();
        Assertions.assertThat(tagResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("Teste: integração do endpoint '/tags/delete/{id}'")
    void deleteTag_ReturnVoid_WhenSuccessful(){
        Tag createdTag = tagRepository.save(TagTesterCreator.createTag());
        ResponseEntity<Void> tagResponseEntity = testRestTemplate.exchange("/tags/delete/{id}", HttpMethod.DELETE, null, Void.class, createdTag.getId());

        Assertions.assertThat(tagResponseEntity).isNotNull();
        Assertions.assertThat(tagResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
