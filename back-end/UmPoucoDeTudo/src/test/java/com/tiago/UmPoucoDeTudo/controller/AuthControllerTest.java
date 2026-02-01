package com.tiago.UmPoucoDeTudo.controller;

import com.tiago.UmPoucoDeTudo.model.User;
import com.tiago.UmPoucoDeTudo.repository.UserRepository;
import com.tiago.UmPoucoDeTudo.requests.AuthRequests.LoginRequest;
import com.tiago.UmPoucoDeTudo.service.TokenService;
import com.tiago.UmPoucoDeTudo.util.user.LoginRequestTesterCreator;
import com.tiago.UmPoucoDeTudo.util.user.RegisterRequestTesterCreator;
import com.tiago.UmPoucoDeTudo.util.user.UserTesterCreator;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private AuthenticationManager authenticationManagerMock;

    @Mock
    private TokenService tokenServiceMock;

    @Test
    @DisplayName("Teste: teste do endpoint '/register'")
    void register_ReturnString_WhenSuccessful() {

        BDDMockito.when(passwordEncoderMock.encode(ArgumentMatchers.anyString()))
                .thenReturn("hashedPassword");

        BDDMockito.when(userRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = authController.register(RegisterRequestTesterCreator.createRegisterRequest());

        BDDMockito.verify(userRepositoryMock).save(ArgumentMatchers.any(User.class));

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getBody())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("Usuário criado com sucesso!");

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/login'")
    void login_ReturnString_WhenSuccessful() {

        LoginRequest loginRequest = LoginRequestTesterCreator.createLoginRequest();

        BDDMockito.when(authenticationManagerMock.authenticate(ArgumentMatchers.any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(
                        UserTesterCreator.createUser(),
                        loginRequest.getPassword(),
                        Collections.emptyList()
                ));

        BDDMockito.when(tokenServiceMock.generateToken(ArgumentMatchers.any(User.class)))
                .thenReturn("token");

        ResponseEntity<String> response = authController.login(loginRequest);

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getBody())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("token");

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("Teste: teste do endpoint '/validate'")
    void validate_ReturnVoid_WhenSuccessful() {

        Authentication authentication = new TestingAuthenticationToken("principal", "credentials");
        authentication.setAuthenticated(true);

        ResponseEntity<Void> response = authController.validateToken(authentication);

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}
