package com.tiago.UmPoucoDeTudo.service;

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
import org.mockito.junit.jupiter.MockitoExtension;

import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.repository.TagRepository;
import com.tiago.UmPoucoDeTudo.util.TagPostRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.TagPutRequestBodyTesterCreator;
import com.tiago.UmPoucoDeTudo.util.TagTesterCreator;

@ExtendWith(MockitoExtension.class)
@DisplayName("Teste do service TagService")
public class TagServiceTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepositoryMock;

    @Test
    @DisplayName("Teste: pegar todas as tags existentes")
    void getAll_ReturnListOfTags_WhenSuccessful() {

        BDDMockito.when(tagRepositoryMock.findAll())
                .thenReturn(List.of(TagTesterCreator.createTag()));

        List<Tag> tagList = tagService.getAll();

        Assertions.assertThat(tagList)
                .isNotNull()
                .isNotEmpty();

        Assertions.assertThat(tagList.get(0))
                .isNotNull()
                .isEqualTo(TagTesterCreator.createTag());

    }

    @Test
    @DisplayName("Teste: pegar tag pelo id")
    void getById_ReturnTag_WhenSuccessful() {

        BDDMockito.when(tagRepositoryMock.findById(TagTesterCreator.getDefaultId()))
                .thenReturn(Optional.of(TagTesterCreator.createTag()));

        Tag tag = tagService.getById(TagTesterCreator.getDefaultId());

        Assertions.assertThat(tag)
                .isNotNull()
                .isEqualTo(TagTesterCreator.createTag());

    }

    @Test
    @DisplayName("Teste: criar nova tag")
    void createTag_ReturnTag_WhenSuccessful() {

        BDDMockito.when(tagRepositoryMock.save(TagTesterCreator.createTag()))
                .thenReturn(TagTesterCreator.createTag());

        Tag tag = tagService.createTag(TagPostRequestBodyTesterCreator.createTagPostRequestBody());

        Assertions.assertThat(tag)
                .isNotNull()
                .isEqualTo(TagTesterCreator.createTag());

    }

    @Test
    @DisplayName("Teste: atualizar tag")
    void replace_ReturnVoid_WhenSuccessful() {

        BDDMockito.when(tagRepositoryMock.findById(TagTesterCreator.getDefaultId()))
                .thenReturn(Optional.of(TagTesterCreator.createTagWithId()));

        tagService.replace(TagPutRequestBodyTesterCreator.createTagPutRequestBody(TagTesterCreator.getDefaultId(),
                "tagUpdatedTest"));

        BDDMockito.verify(tagRepositoryMock)
                .save(ArgumentMatchers.any(Tag.class));

    }

    @Test
    @DisplayName("Teste: apaga tag pelo id")
    void deleteTagById_ReturnVoid_WhenSuccessful() {

        BDDMockito.when(tagRepositoryMock.findById(TagTesterCreator.getDefaultId()))
                .thenReturn(Optional.of(TagTesterCreator.createTag()));

        tagService.deleteTagById(TagTesterCreator.getDefaultId());

        BDDMockito.verify(tagRepositoryMock).deleteById(TagTesterCreator.getDefaultId());

    }

}
