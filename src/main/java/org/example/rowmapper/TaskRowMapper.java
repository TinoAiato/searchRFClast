package org.example.rowmapper;

import org.example.entity.SearchTaskEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TaskRowMapper implements RowMapper<SearchTaskEntity> {
   @Override
   public SearchTaskEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new SearchTaskEntity(
         rs.getLong("id"),
         rs.getString("user_login"),
         rs.getString("q"),
         rs.getBoolean("status")
      );
   }
}
