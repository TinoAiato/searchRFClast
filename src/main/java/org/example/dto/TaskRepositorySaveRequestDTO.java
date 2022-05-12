package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskRepositorySaveRequestDTO {
   private long id;
   private String userLogin;
   private String q;
   private boolean status;
}
