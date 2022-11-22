package webflux.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import webflux.dto.UserDTO;
import webflux.entity.UserEntity;
import webflux.repository.UserRepository;
import webflux.request.CreateUserRequest;
import webflux.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> saveUser(CreateUserRequest user) {
        return Mono.just(user)
                .map(request -> mapper.map(request, UserEntity.class))
                .map(entity -> {
                    entity.setPassword("default");
                    return entity;
                })
                .flatMap(userRepository::saveOrUpdate)
                .thenReturn(true);
    }

    @Override
    public Mono<Boolean> updateUser(Long userId, CreateUserRequest user) {
        return userRepository.findById(userId).switchIfEmpty(Mono.empty())
                .map(entity -> {
                    mapper.map(user, entity);
                    return entity;
                })
                .flatMap(userUpdate -> userRepository.saveOrUpdate(userUpdate))
                .thenReturn(true);
    }

    @Override
    public Mono<Boolean> deleteUser(Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.empty())
                .flatMap(entity -> userRepository.delete(entity, "logic"))
                .thenReturn(true);
    }

    @Override
    public Mono<UserDTO> findUserDetail(Long userId) {
        return userRepository.findById(userId).map(entity -> mapper.map(entity, UserDTO.class));
    }

    @Override
    public Mono<List<UserDTO>> findAllUser() {
        return userRepository.findAllUser().collectList();
    }
}
