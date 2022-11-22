package webflux.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
    private String fullName;
    private String email;
    private String role;
    private Long branchId;
}
