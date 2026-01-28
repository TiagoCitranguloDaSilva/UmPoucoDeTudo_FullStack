package com.tiago.UmPoucoDeTudo.repository;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.model.Tag;
import com.tiago.UmPoucoDeTudo.model.User;
import com.tiago.UmPoucoDeTudo.util.StoryTesterCreator;
import com.tiago.UmPoucoDeTudo.util.TagTesterCreator;
import com.tiago.UmPoucoDeTudo.util.UserTesterCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

@DataJpaTest
@DisplayName("Teste do StoryRepository")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StoryRepositoryTest {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Teste: criar story")
    void save_PersistStory_WhenSuccessful() {


        Story storyToBeSaved = createStoryWithTagAndUserSaved();

        Story storySaved = this.storyRepository.save(storyToBeSaved);

        Assertions.assertThat(storySaved).isNotNull();
        Assertions.assertThat(storySaved.getId()).isNotNull();
        Assertions.assertThat(storySaved.getTitle()).isNotBlank().isEqualTo(storyToBeSaved.getTitle());
        Assertions.assertThat(storySaved.getStory()).isNotBlank().isEqualTo(storyToBeSaved.getStory());
        Assertions.assertThat(storySaved.getTag()).isNotNull();

    }

    @Test
    @DisplayName("Teste: atualizar story")
    void save_UpdateStory_WhenSuccessful() {

        Story storyToBeSaved = createStoryWithTagAndUserSaved();

        Story storySaved = this.storyRepository.save(storyToBeSaved);

        User updatedUser = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Tag updatedTag = tagRepository.save(TagTesterCreator.createTag(
                "updatedTag",
                updatedUser
        ));

        storySaved.setTitle("storyTestUpdatedTitle");
        storySaved.setStory("storyTestUpdatedStory");
        storySaved.setTag(updatedTag);

        Story storyUpdated = this.storyRepository.save(storySaved);

        Assertions.assertThat(storyUpdated).isNotNull();
        Assertions.assertThat(storyUpdated.getId()).isNotNull().isEqualTo(storyToBeSaved.getId());
        Assertions.assertThat(storyUpdated.getTitle()).isNotBlank().isEqualTo(storyToBeSaved.getTitle());
        Assertions.assertThat(storyUpdated.getStory()).isNotBlank().isEqualTo(storyToBeSaved.getStory());
        Assertions.assertThat(storyUpdated.getTag()).isNotNull().isEqualTo(storyToBeSaved.getTag());

    }

    @Test
    @DisplayName("Teste: deletar story")
    void delete_RemoveStory_WhenSuccessful() {

        Story storyToBeSaved = createStoryWithTagAndUserSaved();

        Story storySaved = this.storyRepository.save(storyToBeSaved);

        this.storyRepository.delete(storySaved);

        Optional<Story> storyOptional = this.storyRepository.findById(storySaved.getId());

        Assertions.assertThat(storyOptional).isEmpty();

    }

    @Test
    @DisplayName("Teste: erro ao não passar title obrigatório ao criar story (DataIntegrityViolationException)")
    void save_ThrowDataIntegrityViolationException_WhenTitleIsEmpty() {

        User createdUser = userRepository.save(UserTesterCreator.createUser());
        Tag createdTag = tagRepository.save(TagTesterCreator.createTag(createdUser));

        Story storyToBeSaved = Story.builder()
                .story("storyTestStory")
                .tag(createdTag)
                .build();

        Assertions.assertThatThrownBy(() -> this.storyRepository.save(storyToBeSaved))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    @DisplayName("Teste: erro ao não passar story obrigatório ao criar story (DataIntegrityViolationException)")
    void save_ThrowDataIntegrityViolationException_WhenStoryIsEmpty() {

        User createdUser = userRepository.save(UserTesterCreator.createUser());
        Tag createdTag = tagRepository.save(TagTesterCreator.createTag(createdUser));

        Story storyToBeSaved = Story.builder()
                .title("StoryTestTitle")
                .tag(createdTag)
                .build();

        Assertions.assertThatThrownBy(() -> this.storyRepository.save(storyToBeSaved))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    @DisplayName("Teste: erro ao não passar tag obrigatório ao criar story (DataIntegrityViolationException)")
    void save_ThrowDataIntegrityViolationException_TagIsEmpty() {

        Story storyToBeSaved = Story.builder()
                .title("StoryTestTitle")
                .story("storyTestStory")
                .build();

        Assertions.assertThatThrownBy(() -> this.storyRepository.save(storyToBeSaved))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    private Story createStoryWithTagAndUserSaved() {

        User createdUser = this.userRepository.save(UserTesterCreator.createUser());
        Tag createdTag = this.tagRepository.save(TagTesterCreator.createTag(createdUser));

        return StoryTesterCreator.createStory(createdTag);
    }

}
