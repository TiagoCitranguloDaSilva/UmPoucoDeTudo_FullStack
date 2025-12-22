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

import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPostRequestBody;
import com.tiago.UmPoucoDeTudo.requests.tagRequests.TagPutRequestBody;
import com.tiago.UmPoucoDeTudo.service.TagService;
import com.tiago.UmPoucoDeTudo.util.TagPostRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.TagPutRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.TagTesterCreator;

@ExtendWith(MockitoExtension.class)
public class TagControllerTest {

    @InjectMocks
    private TagController tagController;

    @Mock
    private TagService tagServiceMock;

    @Test
    @DisplayName("Teste: teste do endpoint '/getAll'")
    void getAllTags_ReturnListOfTags_WhenSuccessful() {

        ArrayList<Tag> tagList = new ArrayList<Tag>();

        tagList.add(TagTesterCreator.createTag());

        BDDMockito.when(tagServiceMock.getAll())
                .thenReturn(tagList);

        List<Tag> tags = tagController.getAllTags().getBody();

        Assertions.assertThat(tags)
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(tags.get(0).getName())
                .isNotNull()
                .isNotBlank()
                .isEqualTo(TagTesterCreator.getDefaultName());

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/getById/{id}'")
    void getTagById_ReturnTag_WhenSuccessful() {

        BDDMockito.when(tagServiceMock.getById(ArgumentMatchers.anyLong()))
                .thenReturn(TagTesterCreator.createTagWithId());

        Tag tag = tagController.getTagById(TagTesterCreator.getDefaultId()).getBody();

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

        BDDMockito.when(tagServiceMock.createTag(ArgumentMatchers.any(TagPostRequestBody.class)))
                .thenReturn(TagTesterCreator.createTag());

        Tag tagSaved = tagController.createNewTag(TagPostRequestBodyTesterCreator.createTagPostRequestBody()).getBody();

        Assertions.assertThat(tagSaved).isNotNull();
        Assertions.assertThat(tagSaved.getName())
                .isNotNull()
                .isNotBlank()
                .isEqualTo(TagTesterCreator.getDefaultName());

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/update'")
    void updateTag_ReturnNoContent_WhenSuccessful() {

        ResponseEntity<Void> response = tagController.updateTag(TagPutRequestBodyTesterCreator.createTagPutRequestBody());

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        BDDMockito.verify(tagServiceMock).replace(ArgumentMatchers.any(TagPutRequestBody.class));

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/delete/{id}'")
    void deleteTag_ReturnVoid_WhenSuccessful() {

        tagController.deleteTag(1L);

        BDDMockito.verify(tagServiceMock).deleteTagById(1L);

    }

}
