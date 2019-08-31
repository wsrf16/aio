package com.aio.portable.swiss.middleware.elasticsearch;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.List;

public abstract class ESTemplate {
    /**
     * indexExists
     * @param template
     * @param indexName
     * @return
     */
    public final static boolean indexExists(ElasticsearchTemplate template, String indexName) {
        return template.indexExists(indexName);
    }

    /**
     * create
     * @param template
     * @param t
     * @param index
     * @param type
     * @param id
     * @param <T>
     * @return
     */
    public final static <T> String create(ElasticsearchTemplate template, T t, String index, String type, String id) {
        IndexQueryBuilder indexQueryBuilder = new IndexQueryBuilder();
        indexQueryBuilder
                .withIndexName(index)
                .withType(type)
                .withId(id)
                .withObject(t)
                .build();
        IndexQuery indexQuery = indexQueryBuilder.build();
        String documentId = template.index(indexQuery);
        return documentId;
    }

    /**
     * delete
     * @param template
     * @param index
     * @param type
     */
    public final static void delete(ElasticsearchTemplate template, String index, String type) {
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setIndex(index);
        deleteQuery.setType(type);
        template.delete(deleteQuery);
    }

    /**
     * update
     * @param template
     * @param index
     * @param type
     * @param id
     */
    public final static void update(ElasticsearchTemplate template, String index, String type, String id) {
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withIndexName(index)
                .withType(type)
                .withId(id)
                .build();
        template.update(updateQuery);
    }

    /**
     * queryForList
     * @param template
     * @param queryBuilder : QueryBuilders.matchQuery("name","tom")
     * @param clazz
     * @param <T>
     * @return
     */
    public final static <T> List<T> queryForList(ElasticsearchTemplate template,
                                                 QueryBuilder queryBuilder,
                                                 Class<T> clazz) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder).build();
        return template.queryForList(searchQuery, clazz);
    }

    /**
     * queryForPage
     * @param template
     * @param pageable : PageRequest.of(0, 30)
     * @param queryBuilder : QueryBuilders.matchQuery("name", "jimmy")
     * @param clazz
     * @param <T>
     * @return
     */
    public final static <T> Page<T> queryForPage(ElasticsearchTemplate template,
                                                 QueryBuilder queryBuilder,
                                                 Pageable pageable,
                                                 Class<T> clazz) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withPageable(pageable)
                .build();
        return template.queryForPage(query, clazz);
    }

}
