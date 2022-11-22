package webflux.repository.base;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class BaseActiveRepository<T, ID> {
    @Autowired
    @Qualifier("entityTemplate")
    protected R2dbcEntityOperations operation;

    @Transactional
    public <S extends T> Mono<S> insert(S entity) {
        return operation.insert(entity);
    }

    @Transactional
    public <S extends T> Flux<S> insertAll(Iterable<S> objectsToSave) {
        return Flux.fromIterable(objectsToSave).concatMap(this::insert);
    }

    @Transactional
    public <S extends T> Flux<S> insertAll(Publisher<S> objectsToSave) {
        return Flux.from(objectsToSave).concatMap(this::insert);
    }

    @Transactional
    public <S extends T> Mono<S> update(S entity) {
        return operation.update(entity);
    }

    @Transactional
    public <S extends T> Flux<S> updateAll(Iterable<S> objectsToSave) {
        return Flux.fromIterable(objectsToSave).concatMap(this::update);
    }

    @Transactional
    public <S extends T> Flux<S> updateAll(Publisher<S> objectsToSave) {
        return Flux.from(objectsToSave).concatMap(this::update);
    }
}
