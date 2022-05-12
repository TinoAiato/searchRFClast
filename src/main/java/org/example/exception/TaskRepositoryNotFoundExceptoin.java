package org.example.exception;

import org.springframework.dao.EmptyResultDataAccessException;

public class TaskRepositoryNotFoundExceptoin extends RuntimeException {
   public TaskRepositoryNotFoundExceptoin() {
   }

   public TaskRepositoryNotFoundExceptoin(String message) {
      super(message);
   }

   public TaskRepositoryNotFoundExceptoin(String message, Throwable cause) {
      super(message, cause);
   }

   public TaskRepositoryNotFoundExceptoin(Throwable cause) {
      super(cause);
   }

   public TaskRepositoryNotFoundExceptoin(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }
}
