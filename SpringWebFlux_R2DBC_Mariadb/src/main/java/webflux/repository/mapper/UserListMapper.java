package webflux.repository.mapper;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import webflux.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

public class UserListMapper implements BiFunction<Row, RowMetadata, UserDTO> {
    @Override
    public UserDTO apply(Row row, RowMetadata u) {
        UserDTO dto = new UserDTO();
        dto.setUserId(Long.parseLong(row.get("id").toString()));
        dto.setEmail((String) row.get("email"));
        dto.setRole((Integer) row.get("user_role"));
        dto.setFullName((String) row.get("full_name"));
        dto.setCreateDate((LocalDateTime) row.get("create_date"));
        return dto;
    }
}
