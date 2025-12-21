package com.tiago.UmPoucoDeTudo.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.tiago.UmPoucoDeTudo.model.Story;
import com.tiago.UmPoucoDeTudo.model.Tag;

@DataJpaTest
@DisplayName("Teste do StoryRepository")
public class StoryRepositoryTest {

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("Teste: criar story")
    void save_PersistStory_WhenSuccessful() {

        Story storyToBeSaved = createStory();

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

        Story storyToBeSaved = createStory();

        Story storySaved = this.storyRepository.save(storyToBeSaved);

        storySaved.setTitle("storyTestUpdatedTitle");
        storySaved.setStory("storyTestUpdatedStory");
        storySaved.setTag(createTag());

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

        Story storyToBeSaved = createStory();

        Story storySaved = this.storyRepository.save(storyToBeSaved);

        this.storyRepository.delete(storySaved);

        Optional<Story> storyOptional = this.storyRepository.findById(storySaved.getId());

        Assertions.assertThat(storyOptional).isEmpty();

    }

    @Test
    @DisplayName("Teste: erro ao não passar title obrigatório ao criar story (DataIntegrityViolationException)")
    void save_ThrowDataIntegrityViolationException_WhenTitleIsEmpty() {

        Story storyToBeSaved = Story.builder()
                .story("storyTestStory")
                .tag(createTag())
                .build();

        Assertions.assertThatThrownBy(() -> this.storyRepository.save(storyToBeSaved))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    @DisplayName("Teste: erro ao não passar story obrigatório ao criar story (DataIntegrityViolationException)")
    void save_ThrowDataIntegrityViolationException_WhenStoryIsEmpty() {

        Story storyToBeSaved = Story.builder()
                .title("StoryTestTitle")
                .tag(createTag())
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

    private Tag createTag() {
        return this.tagRepository.save(Tag.builder().name("tagTest").build());
    }

    private Story createStory() {

        return Story.builder()
                .title("StoryTestTitle")
                .story("storyTestStory")
                .tag(createTag())
                .build();

    }

}
