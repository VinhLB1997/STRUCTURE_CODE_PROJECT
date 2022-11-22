package webflux.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long userId;
    private String fullName;
    private String email;
    private Integer role;
    private Long branchId;
    private String tel;
    @JsonIgnore
    private String password;
    private LocalDateTime createDate;

}
