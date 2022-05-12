package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchTaskEntity {
   private long id;
   private String userLogin;
   private String q;
   private boolean status;
}