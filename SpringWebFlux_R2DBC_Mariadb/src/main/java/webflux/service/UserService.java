package webflux.service;

import reactor.core.publisher.Mono;
import webflux.dto.UserDTO;
import webflux.request.CreateUserRequest;

import java.util.List;

public interface UserService {

    public Mono<Boolean> saveUser(CreateUserRequest user);

    public Mono<Boolean> updateUser(Long userId, CreateUserRequest user);

    public Mono<Boolean> deleteUser(Long userId);

    public Mono<UserDTO> findUserDetail(Long userId);

    public Mono<List<UserDTO>> findAllUser();

}
