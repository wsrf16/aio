package com.aio.portable.swiss.module.elasticsearch;

import com.aio.portable.swiss.sugar.CollectionSugar;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.io.Serializable;
import java.util.List;

public abstract class ESRepository { // <T, ID extends Serializable> implements ElasticsearchRepository<T, ID> {

    public static <T, ID extends Serializable> Page<T> page(ElasticsearchRepository<T, ID> repository,
                                                            int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .build();
        Page<T> pages = repository.search(searchQuery);
        return pages;
    }

    public static <T, ID extends Serializable> Page<T> page(ElasticsearchRepository<T, ID> repository,
                                                      QueryBuilder queryBuilder, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(queryBuilder)
                .build();
        Page<T> pages = repository.search(searchQuery);
        return pages;
    }

    /**
     * search
     * @param repository
     * @param builder : NativeSearchQueryBuilder, QueryStringQueryBuilder
     * @param <T>
     * @param <ID>
     * @return
     */
    public static <T, ID extends Serializable> List<T> search(ElasticsearchRepository<T, ID> repository, QueryBuilder builder) {
        List<T> list = CollectionSugar.toList(repository.search(builder).iterator());
        return list;
    }

    /**
     * search
     * @param repository
     * @param builder : NativeSearchQueryBuilder, QueryStringQueryBuilder
     * @param pageable
     * @param <T>
     * @param <ID>
     * @return
     */
    public static <T, ID extends Serializable> Page<T> search(ElasticsearchRepository<T, ID> repository, QueryBuilder builder, Pageable pageable) {
        Page<T> pages = repository.search(builder, pageable);
        return pages;
    }

    public static <T, ID extends Serializable> Page<T> search(ElasticsearchRepository<T, ID> repository, SearchQuery searchQuery) {
        Page<T> pages = repository.search(searchQuery);
        return pages;
    }



}
