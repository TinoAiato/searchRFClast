package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchTaskResultsResponseDTO {
   private long id;
   private String userLogin;
   private String q;
   private boolean status;
   private List<SearchResult> results;

   @NoArgsConstructor
   @AllArgsConstructor
   @Data
   public static class SearchResult {
      private long taskId;
      private String file;
      private String line;
      private int lineNumber;
   }
}
