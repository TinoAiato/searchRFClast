package org.example.conroller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.attribute.RequestAttributes;
import org.example.dto.SearchTaskResponseDTO;
import org.example.dto.SearchTaskResultsResponseDTO;
import org.example.exception.MyException;
import org.example.mime.ContentTypes;
import org.example.security.Authentication;
import org.example.service.SearchRFCService;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class SearchRFCController {
   private final SearchRFCService searchRFC;
   private final Gson gson;

   public void search(HttpServletRequest request, HttpServletResponse response) throws IOException {
      final Authentication auth = (Authentication) request.getAttribute(
         RequestAttributes.AUTH_ATTR
      );
      final String q = request.getParameter("q");

      final SearchTaskResponseDTO responseData = searchRFC.search(auth, q);
      // application/json
      response.setContentType(ContentTypes.APPLICATION_JSON);
      response.getWriter().write(gson.toJson(responseData));
   }

   public void status(HttpServletRequest request, HttpServletResponse response) throws IOException, MyException {
      final Authentication auth = (Authentication) request.getAttribute(
         RequestAttributes.AUTH_ATTR
      );
      final long id = Long.parseLong(request.getParameter("id"));

      final SearchTaskResultsResponseDTO responseData = searchRFC.status(auth, id);
      response.setContentType(ContentTypes.APPLICATION_JSON);
      response.getWriter().write(gson.toJson(responseData));
   }
}

