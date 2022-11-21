package webflux.repository;

import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.entity.UserEntity;
import webflux.repository.base.BaseSelectReposiroty;

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

}
