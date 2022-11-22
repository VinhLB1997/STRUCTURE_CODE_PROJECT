package webflux.repository.base;

import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class BaseSelectReposiroty<T, ID> extends BaseActiveRepository<T, ID> {

    protected static final String DEFAULT_DELETE_FLAG_NAME = "delete_flag";
    protected static final String DEFAULT_SORT_BY_CREATE_DATE = "create_date";
    protected static final String DEFAULT_SORT_BY_UPDATE_DATE = "update_date";
    private static final String DEFAULT_PRIMARY_KEY_NAME = "id";
    private static boolean findByDelete = true;

    protected abstract Class<T> getJavaType();

    protected abstract String getPrimaryKey();

    protected String deleteFlagName() {
        return DEFAULT_DELETE_FLAG_NAME;
    }

    public String getPrimaryKeyName() {
        return DEFAULT_PRIMARY_KEY_NAME;
    }

    /**
     * findAll
     *
     * @return the flux
     */
    public Flux<T> findAll() {
        return this.operation.select(getAllQuery(), getJavaType());
    }

    private Query getAllQuery() {
        if (findByDelete) {
            return Query.query(Criteria.where(deleteFlagName()).is(0))
                    .sort(Sort.by(this.DEFAULT_SORT_BY_CREATE_DATE).descending().and(Sort.by(this.DEFAULT_SORT_BY_UPDATE_DATE).descending()));
        }
        return Query.empty();
    }

    /**
     * findById
     *
     * @return the Mono
     */
    public Mono<T> findById(ID id) {
        Assert.notNull(id, "Id must not be null!");
        return this.operation.selectOne(getIdQuery(id), getJavaType());
    }

    private Query getIdQuery(Object id) {
        if (findByDelete) {
            return Query.query(Criteria.where(getPrimaryKey()).is(id).and(deleteFlagName()).is(0));
        }
        return Query.query(Criteria.where(getPrimaryKey()).is(id));
    }
}
