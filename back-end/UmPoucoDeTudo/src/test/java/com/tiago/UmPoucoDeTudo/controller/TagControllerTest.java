package com.tiago.UmPoucoDeTudo.controller;

import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.model.User;
import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPostRequestBody;
import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPutRequestBody;
import com.tiago.UmPoucoDeTudo.responses.TagResponse;
import com.tiago.UmPoucoDeTudo.service.TagService;
import com.tiago.UmPoucoDeTudo.util.*;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TagControllerTest {

    @InjectMocks
    private TagController tagController;

    @Mock
    private TagService tagServiceMock;

    @Test
    @DisplayName("Teste: teste do endpoint '/getAll'")
    void getAllTags_ReturnListOfTags_WhenSuccessful() {

        ArrayList<Tag> tagList = new ArrayList<>();

        tagList.add(TagTesterCreator.createTag());

        BDDMockito.when(tagServiceMock.getAll(ArgumentMatchers.any(User.class)))
                .thenReturn(TagResponseTesterCreator.convertToTagResponse(tagList));

        Authentication auth = createAuthentication();

        List<TagResponse> tags = tagController.getAllTags(auth).getBody();

        Assertions.assertThat(tags)
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(tags.getFirst().getName())
                .isNotNull()
                .isNotBlank()
                .isEqualTo(TagTesterCreator.getDefaultName());

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/getById/{id}'")
    void getTagById_ReturnTag_WhenSuccessful() {

        BDDMockito.when(tagServiceMock.getById(ArgumentMatchers.anyLong(), ArgumentMatchers.any(User.class)))
                .thenReturn(TagResponseTesterCreator.convertToTagResponse(TagTesterCreator.createTagWithId()));

        Authentication auth = createAuthentication();

        TagResponse tag = tagController.getTagById(TagTesterCreator.getDefaultId(), auth).getBody();

        Assertions.assertThat(tag)
                .isNotNull();

        Assertions.assertThat(tag.getName())
                .isNotNull()
                .isNotBlank()
                .isEqualTo(TagTesterCreator.getDefaultName());

        Assertions.assertThat(tag.getId())
                .isNotNull()
                .isEqualTo(TagTesterCreator.getDefaultId());

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/new'")
    void createNewTag_ReturnCreatedTag_WhenSuccessful() {

        BDDMockito.when(tagServiceMock.createTag(ArgumentMatchers.any(TagPostRequestBody.class), ArgumentMatchers.any(User.class)))
                .thenReturn(TagResponseTesterCreator.convertToTagResponse(TagTesterCreator.createTagWithId()));

        Authentication auth = createAuthentication();

        TagResponse tagSaved = tagController.createNewTag(TagPostRequestBodyTesterCreator.createTagPostRequestBody(), auth).getBody();

        Assertions.assertThat(tagSaved).isNotNull();
        Assertions.assertThat(tagSaved.getName())
                .isNotNull()
                .isNotBlank()
                .isEqualTo(TagTesterCreator.getDefaultName());

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/update'")
    void updateTag_ReturnNoContent_WhenSuccessful() {

        Authentication auth = createAuthentication();

        ResponseEntity<Void> response = tagController.updateTag(TagPutRequestBodyTesterCreator.createTagPutRequestBody(), auth);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        BDDMockito.verify(tagServiceMock).replace(ArgumentMatchers.any(TagPutRequestBody.class), ArgumentMatchers.any(User.class));

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/delete/{id}'")
    void deleteTag_ReturnVoid_WhenSuccessful() {

        Authentication auth = createAuthentication();

        tagController.deleteTag(1L, auth);

        BDDMockito.verify(tagServiceMock).deleteTagById(1L, UserTesterCreator.createUser());

    }

    private Authentication createAuthentication() {
        return new UsernamePasswordAuthenticationToken(UserTesterCreator.createUser(), null, Collections.emptyList());
    }

}
