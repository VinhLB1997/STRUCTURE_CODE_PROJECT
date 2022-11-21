package webflux.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    @Column("delete_flag")
    private Boolean deleteFlag;

    @Column("create_user_id")
    private Long createUser;

    @Column("create_date")
    private LocalDateTime createDate;

    @Column("update_user_id")
    private Long updateUser;

    @Column("update_date")
    private LocalDateTime updateDate;
}
