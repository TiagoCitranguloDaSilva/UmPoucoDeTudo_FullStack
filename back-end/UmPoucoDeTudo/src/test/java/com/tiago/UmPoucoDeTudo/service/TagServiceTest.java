package com.tiago.UmPoucoDeTudo.service;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.model.User;
import com.tiago.UmPoucoDeTudo.repository.TagRepository;
import com.tiago.UmPoucoDeTudo.repository.UserRepository;
import com.tiago.UmPoucoDeTudo.responses.TagResponse;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Teste do service TagService")
public class TagServiceTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepositoryMock;

    @Mock
    private StoryService storyServiceMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Test
    @DisplayName("Teste: pegar todas as tags existentes")
    void getAll_ReturnListOfTags_WhenSuccessful() {

        Tag tag = TagTesterCreator.createTag(UserTesterCreator.createUser(), List.of(StoryTesterCreator.createStory()));

        BDDMockito.when(tagRepositoryMock.findByUser(ArgumentMatchers.any(User.class)))
                .thenReturn(List.of(tag));

        BDDMockito.when(storyServiceMock.toDTO(ArgumentMatchers.any(Story.class)))
                .thenReturn(StoryResponseTesterCreator.convertToStoryResponse(StoryTesterCreator.createStory()));

        List<TagResponse> tagList = tagService.getAll(UserTesterCreator.createUser());
        Assertions.assertThat(tagList)
                .isNotNull()
                .isNotEmpty();

        Assertions.assertThat(tagList.getFirst())
                .isNotNull()
                .isEqualTo(TagResponseTesterCreator.convertToTagResponse(tag, List.of(StoryTesterCreator.createStory())));

    }

    @Test
    @DisplayName("Teste: pegar tag pelo id")
    void getById_ReturnTag_WhenSuccessful() {

        Tag tag = TagTesterCreator.createTag(UserTesterCreator.createUser(), List.of(StoryTesterCreator.createStory()));

        BDDMockito.when(tagRepositoryMock.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any(User.class)))
                .thenReturn(Optional.of(tag));

        BDDMockito.when(storyServiceMock.toDTO(ArgumentMatchers.any(Story.class)))
                .thenReturn(StoryResponseTesterCreator.convertToStoryResponse(StoryTesterCreator.createStory()));

        TagResponse tagResponse = tagService.getById(TagTesterCreator.getDefaultId(), UserTesterCreator.createUser());

        Assertions.assertThat(tagResponse)
                .isNotNull()
                .isEqualTo(TagResponseTesterCreator.convertToTagResponse(tag, List.of(StoryTesterCreator.createStory())));

    }

    @Test
    @DisplayName("Teste: criar nova tag")
    void createTag_ReturnTag_WhenSuccessful() {

        Tag tag = TagTesterCreator.createTagWithId();

        BDDMockito.when(tagRepositoryMock.save(ArgumentMatchers.any(Tag.class)))
                .thenReturn(tag);

        TagResponse tagResponse = tagService.createTag(TagPostRequestBodyTesterCreator.createTagPostRequestBody(), UserTesterCreator.createUser());

        Assertions.assertThat(tagResponse)
                .isNotNull()
                .isEqualTo(TagResponseTesterCreator.convertToTagResponse(tag));

    }

    @Test
    @DisplayName("Teste: atualizar tag")
    void replace_ReturnVoid_WhenSuccessful() {

        BDDMockito.when(tagRepositoryMock.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any(User.class)))
                .thenReturn(Optional.of(TagTesterCreator.createTagWithId()));

        tagService.replace(TagPutRequestBodyTesterCreator.createTagPutRequestBody(TagTesterCreator.getDefaultId(),
                "tagUpdatedTest", LocalDate.now()), UserTesterCreator.createUser());

        BDDMockito.verify(tagRepositoryMock)
                .save(ArgumentMatchers.any(Tag.class));

    }

    @Test
    @DisplayName("Teste: apaga tag pelo id")
    void deleteTagById_ReturnVoid_WhenSuccessful() {

        User user = UserTesterCreator.createUser();
        user.setId(1L);
        Tag tag = TagTesterCreator.createTagWithId();
        user.getTags().add(tag);

        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(user));

        BDDMockito.when(tagRepositoryMock.findByIdAndUser(ArgumentMatchers.anyLong(), ArgumentMatchers.any(User.class)))
                .thenReturn(Optional.of(tag));

        tagService.deleteTagById(tag.getId(), user);

    }

}
