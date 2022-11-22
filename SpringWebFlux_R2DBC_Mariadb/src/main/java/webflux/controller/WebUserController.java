package webflux.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import webflux.common.WebConst;
import webflux.request.CreateUserRequest;
import webflux.response.ListUserResponse;
import webflux.response.common.ResultBooleanResponse;
import webflux.response.common.RootResponse;
import webflux.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(WebConst.API_WEB_ADMIN_TENANT)
public class WebUserController extends BaseController {

    private final UserService userService;

    public WebUserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * ユーザー一覧.
     *
     * @return the mono
     */
    @GetMapping("/users")
    public Mono<ResponseEntity<RootResponse>> findAllUser() {
        return userService.findAllUser().flatMap(x -> {
            return Mono.just(success(ListUserResponse.builder().listUserResponse(x).build()));
        });
    }

    /**
     * createUser
     *
     * @param user the user
     * @return the mono
     */
    @PostMapping("/users")
    public Mono<ResponseEntity<RootResponse>> createUser(@Valid @RequestBody CreateUserRequest user) {
        return userService.saveUser(user).map(x -> {
            return success(new ResultBooleanResponse(x));
        }).onErrorResume(super::handleExceptionLocal);
    }

    /**
     * updateUser
     *
     * @param userId the user id
     * @param user   the user
     * @return the mono
     */
    @PutMapping("/users/{userId}")
    public Mono<ResponseEntity<RootResponse>> updateUser(@PathVariable("userId") Long userId,@Valid @RequestBody CreateUserRequest user) {
        return userService.updateUser(userId, user).map(x -> {
            return success(new ResultBooleanResponse(x));
        }).onErrorResume(super::handleExceptionLocal);
    }

    /**
     * deleteUser
     *
     * @param userId the user id
     * @return the mono
     */
    @DeleteMapping("/users/{userId}")
    public Mono<ResponseEntity<RootResponse>> deleteUser(@PathVariable("userId") Long userId) {
        return userService.deleteUser(userId).map(x -> {
            return success(new ResultBooleanResponse(x));
        }).onErrorResume(super::handleExceptionLocal);
    }
}
