package org.example.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.attribute.ContextAttributes;
import org.example.handler.Handler;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class DemoServlet extends HttpServlet {
   private Map<String, Handler> routes;

   @Override
   public void init() {
      final ApplicationContext context = (ApplicationContext) getServletContext()
         .getAttribute(ContextAttributes.CONTEXT_ATTR);
      routes = (Map<String, Handler>) context.getBean("routes");
   }

   // TODO: http://127.0.0.1:8080/users.getAll -> /users.getAll
   @Override
   protected void service(HttpServletRequest req, HttpServletResponse resp) {
      final String uri = req.getRequestURI();
      final Handler handler = routes.get(uri);
      if (handler != null) {
         try {
            handler.handle(req, resp);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }
}
