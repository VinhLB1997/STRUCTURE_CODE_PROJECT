package webflux.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("user")
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends BaseEntity{

    @Id
    @Column("id")
    private Long userId;

    @Column("password")
    private String password;

    @Column("full_name")
    private String fullName;

    @Column("email")
    private String email;

    @Column("user_role")
    private Integer role;

    @Column("branch_id")
    private Long branchId;

}
