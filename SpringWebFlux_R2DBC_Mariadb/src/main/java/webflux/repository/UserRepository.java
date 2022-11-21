package webflux.repository;

import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.dto.UserDTO;
import webflux.entity.UserEntity;
import webflux.repository.base.BaseSelectReposiroty;
import webflux.repository.mapper.UserListMapper;

@Repository
public class UserRepository extends BaseSelectReposiroty<UserEntity, Long> {
    @Override
    protected Class<UserEntity> getJavaType() {
        return UserEntity.class;
    }

    @Override
    protected String getPrimaryKey() {
        return super.getPrimaryKeyName();
    }

    /*---------- Methods for Inserting and Updating Entities ( Base CRUD using) ------------*/

    /**
     * findByEmailExits
     *
     * @return the flux
     */
    public Flux<UserEntity> findByEmailExits(String email) {
        return super.operation.select(Query.query(Criteria.where("email").is(email).and(deleteFlagName()).is(0)),
                this.getJavaType());
    }

    /**
     * save Or update
     *
     * @return the flux
     */
    public Mono<UserEntity> saveOrUpdate(UserEntity user) {
        if (user.getUserId() == null) {
            return super.operation.insert(user);
        } else {
            return super.operation.update(user);
        }
    }

    /**
     * delete physical
     *
     * @return the flux
     */
    public Mono<UserEntity> delete(UserEntity user) {
        return super.operation.delete(user);
    }

    /**
     * delete logic
     *
     * @return the flux
     */
    public Mono<UserEntity> delete(UserEntity user, String type) {
        if (type.equals("logic")) {
            user.setDeleteFlag(true);
            return super.operation.update(user);
        }
        return Mono.empty();
    }
    /*---------- Methods for Inserting and Updating Entities ( Base CRUD using) ------------*/

    /**
     * Tìm kiếm tất cả user.
     *
     * @return the flux
     */
    public Flux<UserDTO> findAllUser() {
        return Mono.defer(() -> {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT u.* FROM user u ");
            sql.append("WHERE u.delete_flag = 0 ");
            sql.append(" ORDER BY ").append("create_date").append(" DESC ");
            return Mono.just(sql.toString());
        }).flatMapMany(sql -> {
            UserListMapper mapper = new UserListMapper();
            return this.operation.getDatabaseClient().sql(sql).map(mapper).all();
        });
    }

}
