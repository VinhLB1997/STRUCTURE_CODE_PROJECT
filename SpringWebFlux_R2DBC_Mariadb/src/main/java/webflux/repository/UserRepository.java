package webflux.repository;

import org.springframework.stereotype.Repository;
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
}
