package com.tiago.UmPoucoDeTudo.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.tiago.UmPoucoDeTudo.model.Tag;

@DataJpaTest
@DisplayName("Teste do TagRepository")
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("Teste: criar tag")
    void save_PersistTag_WhenSuccessful() {

        Tag tagToBeSaved = createTag();
        Tag tagSaved = this.tagRepository.save(tagToBeSaved);

        Assertions.assertThat(tagSaved).isNotNull();
        Assertions.assertThat(tagSaved.getId()).isNotNull();
        Assertions.assertThat(tagSaved.getName())
            .isNotBlank()
            .isEqualTo(tagToBeSaved.getName());
    }

    @Test
    @DisplayName("Teste: atualizar tag")
    void save_UpdateTag_WhenSuccessful() {

        Tag tagToBeSaved = createTag();
        Tag tagSaved = this.tagRepository.save(tagToBeSaved);

        tagSaved.setName("updatedTag");

        Tag tagUpdated = this.tagRepository.save(tagSaved);

        Assertions.assertThat(tagUpdated).isNotNull();
        Assertions.assertThat(tagUpdated.getId()).isNotNull();
        Assertions.assertThat(tagUpdated.getName()).isEqualTo(tagSaved.getName());

    }

    @Test
    @DisplayName("Teste: deletar tag")
    void delete_RemoveTag_WhenSuccessful(){

        Tag tagToBeSaved = createTag();
        Tag tagSaved = this.tagRepository.save(tagToBeSaved);

        this.tagRepository.delete(tagSaved);

        Optional<Tag> tapOptional = this.tagRepository.findById(tagSaved.getId());

        Assertions.assertThat(tapOptional).isEmpty();

    }

    @Test
    @DisplayName("Teste: erro ao não passar nome obrigatório ao criar tag (DataIntegrityViolationException)")
    void save_ThrowDataIntegrityViolationException_WhenNameIsEmpty(){

        Tag tagToBeSaved = new Tag();
        Assertions.assertThatThrownBy(() -> this.tagRepository.save(tagToBeSaved))
            .isInstanceOf(DataIntegrityViolationException.class);
            
    }

    private Tag createTag() {
        return Tag.builder().name("tagTest").build();
    }

}
