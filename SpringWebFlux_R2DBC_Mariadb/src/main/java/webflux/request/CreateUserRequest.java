package webflux.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CreateUserRequest {

    @NotBlank
    private String fullName;
    @NotBlank(message = "Email is not null !!!")
    @Email(message = "Email invalid !!!")
    private String email;
    private String role;
    private Long branchId;
}
