package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchTaskResponseDTO {
   private long id;
   private String userLogin;
   private String q;
   private boolean status;
}
