package org.example.configuration;

import com.google.gson.Gson;
import org.example.conroller.SearchRFCController;
import org.example.conroller.UserController;
import org.example.handler.Handler;
import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationConfiguration {
   @Bean
   public DataSource ds() throws NamingException {
      final InitialContext ctx = new InitialContext();
      return (DataSource) ctx.lookup("java:/comp/env/jdbc/db");
   }

   @Bean
   public Jdbi jdbi(final DataSource dataSource) {
      return Jdbi.create(dataSource);
   }

   @Bean
   public NamedParameterJdbcTemplate namedParameterJdbcTemplate(final DataSource dataSource) {
      return new NamedParameterJdbcTemplate(dataSource);
   }

   @Bean
   public Gson gson() {
      return new Gson();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new Argon2PasswordEncoder();
   }

   @Bean
   public Map<String, Handler> routes(
      final UserController userController,
      final SearchRFCController searchRFCController
   ) {
      final Map<String, Handler> routes = new HashMap<>();
      routes.put("/users.register", userController::register);
      routes.put("/users.login", userController::login);
      routes.put("/users.create", userController::create); // создание пользователя (нужны права USERS_EDIT_ALL)
      routes.put("/accounts.search", searchRFCController::search);
      routes.put("/accounts.status", searchRFCController::status);
      return Collections.unmodifiableMap(routes); // safety for multithreading
   }
}
