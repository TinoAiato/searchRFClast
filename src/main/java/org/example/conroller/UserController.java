package org.example.conroller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.attribute.RequestAttributes;
import org.example.dto.*;
import org.example.mime.ContentTypes;
import org.example.security.Authentication;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class UserController {
   private final UserService service;
   private final Gson gson;

   public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
      // TODO:
      //  + 0. вычитываем DTO
      final UserRegisterRequestDTO requestData = gson.fromJson(
         request.getReader(),
         UserRegisterRequestDTO.class
      );
      final UserRegisterResponseDTO responseData = service.register(requestData);
      // application/json
      response.setContentType(ContentTypes.APPLICATION_JSON);
      response.getWriter().write(gson.toJson(responseData));
   }

   public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
      final UserLoginRequestDTO requestData = gson.fromJson(
         request.getReader(),
         UserLoginRequestDTO.class
      );
      final UserLoginResponseDTO responseData = service.login(requestData);
      response.setContentType(ContentTypes.APPLICATION_JSON);
      response.getWriter().write(gson.toJson(responseData));
   }

   public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
      final Authentication auth = (Authentication) request.getAttribute(
         RequestAttributes.AUTH_ATTR
      );


      final UserCreateRequestDTO requestData = gson.fromJson(
         request.getReader(),
         UserCreateRequestDTO.class
      );
      final UserCreateResponseDTO responseData = service.create(auth, requestData);
      // application/json
      response.setContentType(ContentTypes.APPLICATION_JSON);
      response.getWriter().write(gson.toJson(responseData));
   }
}
