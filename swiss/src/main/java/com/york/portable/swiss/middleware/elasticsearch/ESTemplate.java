package com.york.portable.swiss.middleware.elasticsearch;

import org.elasticsearch.action.admin.cluster.repositories.delete.DeleteRepositoryRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.List;

public abstract class ESTemplate {
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

    public final static void delete(ElasticsearchTemplate template, String index, String type) {
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setIndex(index);
        deleteQuery.setType(type);
        template.delete(deleteQuery);
    }

    public final static void update(ElasticsearchTemplate template, String index, String type, String id) {
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withIndexName(index)
                .withType(type)
                .withId(id)
                .build();
        template.update(updateQuery);
    }

    public final static <T> List<T> queryForList(ElasticsearchTemplate template, Class<T> clazz) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name","gaolujie")).build();
        return template.queryForList(searchQuery, clazz);
    }

//    public final static <T> Page<T> queryForPage(ElasticsearchTemplate template, Class<T> clazz) {
//        StringQuery stringQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchQuery("name","gaolujie")).build();
//        return template.queryForPage(stringQuery, clazz);
//    }

}
