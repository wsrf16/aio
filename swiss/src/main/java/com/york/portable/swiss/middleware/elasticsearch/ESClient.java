package com.york.portable.swiss.middleware.elasticsearch;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public abstract class ESClient {
    TransportClient transportClient;

    private Settings settings() {
        Settings.Builder builder = Settings.builder();
        builder.put("number_of_shards", 1);
        builder.put("number_of_replicas", 1);
        Settings settings = builder.build();
        return settings;
    }

    public final static CreateIndexResponse createStrictIndex(TransportClient transportClient, String index, String type, Class clazz) throws IOException, ExecutionException, InterruptedException, IllegalAccessException, InstantiationException {
        AdminClient adminClient = transportClient.admin();
        IndicesAdminClient indicesAdminClient = adminClient.indices();

        Map<String, String> keyValueMap = new HashMap<>();
        keyValueMap.put("dynamic", "strict");
        XContentBuilder contentBuilder = ElasticsearchUtils.buildMappingXContentBuilderByClass(keyValueMap, clazz);

        CreateIndexRequest createIndexRequest = Requests.createIndexRequest(index)
//                .settings(settings)
                .mapping(type, contentBuilder);
        CreateIndexResponse createIndexResponse = indicesAdminClient.create(createIndexRequest).get();

//        indicesAdminClient.prepareCreate(index).execute().actionGet();
//        PutMappingRequest mapping = Requests.putMappingRequest(index).type(type).source(contentBuilder);
//        indicesAdminClient.putMapping(mapping).actionGet();
//
//
//        DeleteIndexRequest deleteIndexRequest = Requests.deleteIndexRequest(index);
//        DeleteIndexResponse response_delete = indicesAdminClient.delete(deleteIndexRequest).get();
        return createIndexResponse;
    }

    /**
     * create
     * @param transportClient
     * @param source : XContentFactory.jsonBuilder().startObject().field("name", "value").endObject();
     * @param index
     * @param type
     * @param id
     * @return
     */
    public final static IndexResponse create(TransportClient transportClient, XContentBuilder source, String index, String type, String id) {
        IndexRequestBuilder requestBuilder = transportClient.prepareIndex(index, type, id)
                .setSource(source);
        IndexResponse response = requestBuilder.get();
        return response;
    }

    /**
     * create
     * @param transportClient
     * @param fields
     * @param index
     * @param type
     * @param id
     * @return
     * @throws IOException
     */
    public final static IndexResponse create(TransportClient transportClient, Map<String, Object> fields, String index, String type, String id) throws IOException {
        XContentBuilder source = XContentFactory.jsonBuilder()
                .startObject();
        fields.entrySet().forEach(c -> {
            try {
                source.field(c.getKey(), c.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        source.endObject();

        IndexRequestBuilder requestBuilder = transportClient.prepareIndex(index, type, id)
                .setSource(source);
        IndexResponse response = requestBuilder.get();
        return response;
    }

    /**
     * delete
     * @param transportClient
     * @param index
     * @param type
     * @param id
     * @return
     */
    public final static DeleteResponse delete(TransportClient transportClient, String index, String type, String id) {
        DeleteResponse response = transportClient.prepareDelete(index, type, id).get();
        return response;

//        DeleteResponse deleteResponse;
//        deleteResponse = transportClient.prepareDelete("index", "type", "2").execute().actionGet();
//        deleteResponse = transportClient.prepareDelete("index", "type", "2").get();
//        try {
//            deleteResponse = transportClient.delete(new DeleteRequest()).get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * update
     * @param transportClient
     * @param source : XContentFactory.jsonBuilder().startObject().field("name", "value").endObject();
     * @param index
     * @param type
     * @param id
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public UpdateResponse update(TransportClient transportClient, XContentBuilder source, String index, String type, String id) throws ExecutionException, InterruptedException {
        UpdateRequest update = new UpdateRequest(index, type, id);
        update.doc(source);
        UpdateResponse response = transportClient.update(update).get();
        return response;
    }

    /**
     * get
     * @param transportClient
     * @param index
     * @param type
     * @param id
     * @return
     */
    public final static GetResponse get(TransportClient transportClient, String index, String type, String id) {
        GetResponse response = transportClient.prepareGet(index, type, id).get();
        return response;
    }

    /**
     * search
     * @param queryBuilder
     * @param index
     * @param type
     * @param from
     * @param size
     * @return
     */
    public SearchResponse search(QueryBuilder queryBuilder, String index, String type, int from, int size) {
//        // 组装查询条件
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        if (title != null) {
//            boolQuery.must(QueryBuilders.matchQuery("title", title));
//        }
//        if (author != null) {
//            boolQuery.must(QueryBuilders.matchQuery("author", author));
//        }
//        if (wordCount != null) {
//            boolQuery.must(QueryBuilders.matchQuery("word_count", wordCount));
//        }
//        if (publishDate != null) {
//            boolQuery.must(QueryBuilders.matchQuery("publish_date", publishDate));
//        }
//        // 以word_count作为条件范围
//        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("word_count").from(gtWordCount);
//        if (ltWordCount != null && ltWordCount > 0) {
//            rangeQuery.to(ltWordCount);
//        }
//        boolQuery.filter(rangeQuery);
//        queryBuilder = boolQuery;


        // 组装查询请求
        SearchRequestBuilder requestBuilder = transportClient.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .setFrom(from)
                .setSize(size);
        SearchResponse response = requestBuilder.get();
        return response;
    }
//    public final static BulkResponse bulk(TransportClient transportClient, Map<String, Object> fields, String index, String type, String id) throws IOException {
//        XContentBuilder source = XContentFactory.jsonBuilder()
//                .startObject();
//        fields.entrySet().forEach(c -> {
//            try {
//                source.field(c.getKey(), c.getValue());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        source.endObject();
//
//        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
//
//        bulkRequest.add(transportClient.prepareIndex(index, type, id).setSource(source));
//        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
//        return bulkResponse;

//    }
}
