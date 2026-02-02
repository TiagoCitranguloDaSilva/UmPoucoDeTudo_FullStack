package com.tiago.UmPoucoDeTudo.repository;

import com.tiago.UmPoucoDeTudo.model.User;
import com.tiago.UmPoucoDeTudo.util.user.UserTesterCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayName("Teste do UserRepository")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Teste: criar usuário")
    void save_PersistUser_WhenSuccessful() {

        User userToBeSaved = UserTesterCreator.createUser();

        User savedUser = this.userRepository.save(userToBeSaved);

        Assertions.assertThat(savedUser).isNotNull();

        Assertions.assertThat(savedUser.getId()).isNotNull();

    }

    @Test
    @DisplayName("Teste: pegar pelo email")
    void findByEmail_GetUser_WhenSuccessful() {

        User userToBeSaved = UserTesterCreator.createUser();
        User savedUser = userRepository.save(userToBeSaved);

        Optional<User> user = userRepository.findByEmail(savedUser.getEmail());

        Assertions.assertThat(user).isNotNull().isNotEmpty();

        Assertions.assertThat(user).isEqualTo(Optional.of(savedUser));

    }

}
