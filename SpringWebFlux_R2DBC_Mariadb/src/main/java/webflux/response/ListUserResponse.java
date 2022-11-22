package webflux.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import webflux.dto.UserDTO;
import webflux.response.common.ResponseBody;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ListUserResponse extends ResponseBody {
    private List<UserDTO> listUserResponse;
}
