package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.entity.SearchTaskEntity;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskRepository {
   private final Jdbi jdbi;

   public SearchTaskEntity save(SearchTaskEntity entity) {
      return jdbi.withHandle(handle -> handle.createQuery(
               //language=PostgreSQL
               """
                      INSERT INTO requests(user_login, q, status) VALUES (:user_login, :q, :status)
                      RETURNING id, user_login, q, status
                  """
            )
            .bind("id", entity.getId())
            .bind("user_login", entity.getUserLogin())
            .bind("q", entity.getQ())
            .bind("status", entity.isStatus())
            .mapToBean(SearchTaskEntity.class)
            .one()
      );
//         template.queryForObject(
//         //language=PostgreSQL
//         """
//                INSERT INTO requests(user_login, q, status) VALUES (:user_login, :q, :status)
//                RETURNING id, user_login, q, status
//            """,
//         Map.of(
//            "user_login", entity.getUserLogin(),
//            "q", entity.getQ(),
//            "status", false
//         ),
//         rowMapper
//      );
   }

   public SearchTaskEntity markDone(SearchTaskEntity saved) {
      return jdbi.withHandle(handle -> handle.createQuery(
               //language=PostgreSQL
               """
                      UPDATE requests SET status=:status
                      WHERE id = :id AND user_login=:user_login
                      RETURNING id, user_login, status
                  """
            )
            .bind("id", saved.getId())
            .bind("user_login", saved.getUserLogin())
            .bind("status", true)
            .mapToBean(SearchTaskEntity.class)
            .one()
      );
//         template.queryForObject(
//         //language=PostgreSQL
//         """
//                UPDATE requests SET status=:status
//                WHERE id = :id AND user_login=:user_login
//                RETURNING id, user_login, status
//            """,
//         Map.of(
//            "id", saved.getId(),
//            "user_login", saved.getUserLogin(),
//            "status", true
//         ),
//         rowMapper
//      );
   }

   public SearchTaskEntity getStatus(long taskId) {
      return jdbi.withHandle(handle -> handle.createQuery(
               //language=PostgreSQL
               """
                              SELECT id, user_login, q, status FROM requests
                              WHERE id = :id
                  """
            )
            .bind("id", taskId)
            .mapToBean(SearchTaskEntity.class)
            .one()
      );
   }
}
