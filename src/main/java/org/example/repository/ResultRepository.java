package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.entity.SearchResultEntity;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ResultRepository {
   private final Jdbi jdbi;

   public SearchResultEntity saveResult(SearchResultEntity entity) {
      return jdbi.withHandle(handle -> handle.createQuery(
               // language=PostgreSQL
               """
                  INSERT INTO results(task_id, file, line, lineNumber) VALUES (:task_id, :file, :line, :lineNumber)
                  RETURNING result_id,task_id, file
                  """
            )
            .bind("task_id", entity.getTaskId())
            .bind("file", entity.getFile())
            .bind("line", entity.getLine())
            .bind("lineNumber", entity.getLineNumber())
            .mapToBean(SearchResultEntity.class)
            .one()
      );
   }

   public List<SearchResultEntity> getByTaskId(long taskId) {
      return jdbi.withHandle(handle -> handle.createQuery(
               // language=PostgreSQL
               """
                              SELECT task_id, file, line, linenumber FROM results
                              WHERE task_id = :task_id
                  """
            )
            .bind("task_id", taskId)
            .mapToBean(SearchResultEntity.class)
            .list()
      );
   }
}
