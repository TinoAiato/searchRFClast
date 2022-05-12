package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.entity.TokenEntity;
import org.example.entity.UserEntity;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepository {
   private final Jdbi jdbi;

   public Optional<UserEntity> findByLogin(String login) {
      return jdbi.withHandle(handle -> handle.createQuery(
               // language=PostgreSQL
               "SELECT login, password FROM users WHERE login = :login"
            )
            .bind("login", login)
            .mapToBean(UserEntity.class)
            .findOne()
      );
   }

   public UserEntity save(UserEntity entity) {
      return jdbi.withHandle(handle -> handle.createQuery(
               // language=PostgreSQL
               """
                       INSERT INTO users(login, password) VALUES (:login, :password)
                       RETURNING login, password
                       """
            )
            .bind("login", entity.getLogin())
            .bind("password", entity.getPassword())
            .mapToBean(UserEntity.class)
            .one()
      );
   }

   public TokenEntity save(TokenEntity entity) {
      return jdbi.withHandle(handle -> handle.createQuery(
               // language=PostgreSQL
               """
                       INSERT INTO tokens (value, user_login) VALUES (:value, :user_login)
                       RETURNING value, user_login
                       """
            )
            .bind("value", entity.getValue())
            .bind("user_login", entity.getUserLogin())
            .mapToBean(TokenEntity.class)
            .one()
      );
   }

   public Optional<UserEntity> findByToken(String token) {
      return jdbi.withHandle(handle -> handle.createQuery(
               // language=PostgreSQL
               """
               SELECT u.login, NULL
               FROM tokens t
               JOIN users u ON t.user_login = u.login
               WHERE t.value = :token
               """
            )
            .bind("token", token)
            .mapToBean(UserEntity.class)
            .findOne()
      );
   }
}
