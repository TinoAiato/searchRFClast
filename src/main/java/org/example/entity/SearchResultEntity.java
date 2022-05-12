package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchResultEntity {
   long resultId;
   long taskId;
   String file;
   String line;
   int lineNumber;
}
