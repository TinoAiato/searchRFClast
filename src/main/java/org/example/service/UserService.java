package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.entity.TokenEntity;
import org.example.entity.UserEntity;
import org.example.exception.UsernameAlreadyRegisteredException;
import org.example.repository.UserRepository;
import org.example.security.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@RequiredArgsConstructor
@Service
public class UserService {
   private final UserRepository repository;
   private final PasswordEncoder passwordEncoder;
   private final SecureRandom random = new SecureRandom();


   public UserRegisterResponseDTO register(UserRegisterRequestDTO requestData) {
      final String hashedPassword = passwordEncoder.encode(requestData.getPassword());
      // TODO: transaction
      repository
         .findByLogin(requestData.getLogin())
         .ifPresent(o -> {
            throw new UsernameAlreadyRegisteredException(o.getLogin());
         });
      final UserEntity saved = repository.save(new UserEntity(
         requestData.getLogin(),
         hashedPassword,
         new String[]{}
      ));

      // генерация токена TODO: вынести в отдельный метод
      byte[] buffer = new byte[128];
      random.nextBytes(buffer);
      final String token = Base64.getUrlEncoder()
         .withoutPadding()
         .encodeToString(buffer);
      repository.save(new TokenEntity(saved.getLogin(), token));

      return new UserRegisterResponseDTO(
         saved.getLogin(),
         token
      );
   }

   public LoginAuthentication authenticate(String login, String password) {
      // TODO: подходы:
      //  1. безопасный
      //  +2. удобный
      final UserEntity entity = repository.findByLogin(login)
         .orElseThrow(NotFoundException::new);

      // password - незахешированный
      // entity.getPassword - захешированный
      if (!passwordEncoder.matches(password, entity.getPassword())) {
         throw new CredentialsNotMatchesException();
      }

      return new LoginAuthentication(login);
   }

   public TokenAuthentication authenticate(String token) {
      return repository.findByToken(token)
         .map(o -> new TokenAuthentication(o.getLogin(), o.getRoles()))
         .orElseThrow(TokenNotFoundException::new)
         ;
   }

   public UserLoginResponseDTO login(UserLoginRequestDTO requestData) {
      // взяли из login всё, кроме return
      final UserEntity entity = repository.findByLogin(requestData.getLogin())
         .orElseThrow(NotFoundException::new);

      // requestData.getPassword - незахешированный
      // entity.getPassword - захешированный
      if (!passwordEncoder.matches(requestData.getPassword(), entity.getPassword())) {
         throw new CredentialsNotMatchesException();
      }

         final String token = createToken(entity);


         return new UserLoginResponseDTO(
            entity.getLogin(),
            token
         );

   }

   private String createToken(UserEntity entity) {
      byte[] buffer = new byte[128];
      random.nextBytes(buffer);
      final String token = Base64.getUrlEncoder()
         .withoutPadding()
         .encodeToString(buffer);
      repository.save(new TokenEntity(entity.getLogin(), token));
      return token;
   }

   public UserCreateResponseDTO create(Authentication auth, UserCreateRequestDTO requestData) {
      if (!auth.hasRole(Roles.USERS_EDIT_ALL)) {
         throw new ForbiddenException();
      }

      // TODO: copy from register
      final String hashedPassword = passwordEncoder.encode(requestData.getPassword());

      repository
         .findByLogin(requestData.getLogin())
         .ifPresent(o -> {
            throw new UsernameAlreadyRegisteredException(o.getLogin());
         });
      final UserEntity saved = repository.save(new UserEntity(
         requestData.getLogin(),
         hashedPassword,
         requestData.getRoles()
      ));

      return new UserCreateResponseDTO(
         saved.getLogin()
      );
   }
}
