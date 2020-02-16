package com.aio.portable.swiss.module.elasticsearch;

import com.aio.portable.swiss.suite.bean.BeanSugar;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class ElasticsearchSugar {
    private final static String TYPE = "type";

    public static XContentBuilder buildMappingXContentBuilder(Map<String, String> keyValueMap, Class clazz) throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();

        xContentBuilder.startObject();
        Set<Map.Entry<String, String>> keyValueSet = keyValueMap.entrySet();
        for (Map.Entry<String, String> entry : keyValueSet) {
            xContentBuilder.field(entry.getKey(), entry.getValue());
//            xContentBuilder.field("dynamic", "strict");
        }

        xContentBuilder.startObject("properties");
        Set<Map.Entry<String, Class>> propertySet = BeanSugar.PropertyDescriptors.getNameClass(clazz).entrySet();
        startObject(xContentBuilder, propertySet);

        xContentBuilder.endObject().endObject();
        return xContentBuilder;
    }


    /**
     * indicesExists
     * @param transportClient
     * @param index
     * @return
     */
    public static IndicesExistsResponse indicesExists(TransportClient transportClient, String index) {
        IndicesExistsRequest indicesExistsRequest = Requests.indicesExistsRequest(index);
        IndicesExistsResponse indicesExistsResponse = transportClient.admin().indices().exists(indicesExistsRequest).actionGet();
        return indicesExistsResponse;
    }

    /**
     * indicesExists
     * @param transportClient
     * @param indicesExistsRequest
     * @return
     */
    public static IndicesExistsResponse indicesExists(TransportClient transportClient, IndicesExistsRequest indicesExistsRequest) {
        IndicesExistsResponse indicesExistsResponse = transportClient.admin().indices().exists(indicesExistsRequest).actionGet();
        return indicesExistsResponse;
    }

    /**
     * createIndex
     * @param transportClient
     * @param index
     * @param settings
     * @param type
     * @param mapping
     * @return
     */
    public static CreateIndexResponse createIndex(TransportClient transportClient, String index, Settings settings, String type, XContentBuilder mapping) {
        CreateIndexRequest createIndexRequest = Requests.createIndexRequest(index).settings(settings).mapping(type, mapping);
        CreateIndexResponse createIndexResponse = transportClient.admin().indices().create(createIndexRequest).actionGet();
        return createIndexResponse;
    }

    /**
     * createIndex
     * @param transportClient
     * @param index
     * @param settings
     * @return
     */
    public static CreateIndexResponse createIndex(TransportClient transportClient, String index, Settings settings) {
        CreateIndexRequest createIndexRequest = Requests.createIndexRequest(index).settings(settings);
        CreateIndexResponse createIndexResponse = transportClient.admin().indices().create(createIndexRequest).actionGet();
        return createIndexResponse;
    }

    /**
     * createIndex
     * @param transportClient
     * @param index
     * @return
     */
    public static CreateIndexResponse createIndex(TransportClient transportClient, String index) {
        CreateIndexRequest createIndexRequest = Requests.createIndexRequest(index);
        CreateIndexResponse createIndexResponse = transportClient.admin().indices().create(createIndexRequest).actionGet();
        return createIndexResponse;
    }

    /**
     * createIndex
     * @param transportClient
     * @param createIndexRequest
     * @return
     */
    public static CreateIndexResponse createIndex(TransportClient transportClient, CreateIndexRequest createIndexRequest) {
        CreateIndexResponse createIndexResponse = transportClient.admin().indices().create(createIndexRequest).actionGet();
        return createIndexResponse;
    }

    /**
     * putMapping
     * @param transportClient
     * @param index
     * @param type
     * @param xContentBuilder
     * @return
     */
    public static PutMappingResponse putMapping(TransportClient transportClient, String index, String type, XContentBuilder xContentBuilder) {
        PutMappingRequest putMappingRequest = Requests.putMappingRequest(index).type(type).source(xContentBuilder);
        PutMappingResponse putMappingResponse = transportClient.admin().indices().putMapping(putMappingRequest).actionGet();
        return putMappingResponse;
    }

    /**
     * putMapping
     * @param transportClient
     * @param putMappingRequest
     * @return
     */
    public static PutMappingResponse putMapping(TransportClient transportClient, PutMappingRequest putMappingRequest) {
        PutMappingResponse putMappingResponse = transportClient.admin().indices().putMapping(putMappingRequest).actionGet();
        return putMappingResponse;
    }

    /**
     * deleteIndex
     * @param transportClient
     * @param index
     * @return
     */
    public static DeleteIndexResponse deleteIndex(TransportClient transportClient, String index) {
        DeleteIndexRequest deleteIndexRequest = Requests.deleteIndexRequest(index);
        DeleteIndexResponse deleteIndexResponse = transportClient.admin().indices().delete(deleteIndexRequest).actionGet();
        return deleteIndexResponse;
    }

    /**
     * deleteIndex
     * @param transportClient
     * @param deleteIndexRequest
     * @return
     */
    public static DeleteIndexResponse deleteIndex(TransportClient transportClient, DeleteIndexRequest deleteIndexRequest) {
        DeleteIndexResponse deleteIndexResponse = transportClient.admin().indices().delete(deleteIndexRequest).actionGet();
        return deleteIndexResponse;
    }

    private static void startObject(XContentBuilder xContentBuilder, Set<Map.Entry<String, Class>> propertySet) throws IOException {
        for (Map.Entry<String, Class> entry : propertySet) {
            if (entry.getValue().equals(String.class) || entry.getValue().equals(Character.class))
                xContentBuilder.startObject(entry.getKey()).field(TYPE, "text").endObject();
            if (entry.getValue().equals(Integer.class))
                xContentBuilder.startObject(entry.getKey()).field(TYPE, "integer").endObject();
            if (entry.getValue().equals(Long.class))
                xContentBuilder.startObject(entry.getKey()).field(TYPE, "long").endObject();

            if (entry.getValue().equals(Short.class))
                xContentBuilder.startObject(entry.getKey()).field(TYPE, "short").endObject();
            if (entry.getValue().equals(Float.class))
                xContentBuilder.startObject(entry.getKey()).field(TYPE, "float").endObject();
            if (entry.getValue().equals(Double.class))
                xContentBuilder.startObject(entry.getKey()).field(TYPE, "double").endObject();
            if (entry.getValue().equals(Byte.class))
                xContentBuilder.startObject(entry.getKey()).field(TYPE, "byte").endObject();

            if (entry.getValue().equals(Date.class))
                xContentBuilder.startObject(entry.getKey()).field(TYPE, "date").endObject();

            if (entry.getValue().equals(Boolean.class))
                xContentBuilder.startObject(entry.getKey()).field(TYPE, "boolean").endObject();
        }
    }




//    public static class Bulk {
//        public static BulkRequestBuilder bulkAdd(BulkRequestBuilder bulkRequestBuilder, IndexRequest request) {
//            return bulkRequestBuilder.add(request);
//        }
//
//        public static BulkRequestBuilder bulkAdd(BulkRequestBuilder bulkRequestBuilder, IndexRequestBuilder requestBuilder) {
//            return bulkRequestBuilder.add(requestBuilder);
//        }
//
//        public static BulkRequestBuilder bulkAdd(BulkRequestBuilder bulkRequestBuilder, DeleteRequest request) {
//            return bulkRequestBuilder.add(request);
//        }
//
//        public static BulkRequestBuilder bulkAdd(BulkRequestBuilder bulkRequestBuilder, DeleteRequestBuilder requestBuilder) {
//            return bulkRequestBuilder.add(requestBuilder);
//        }
//
//        public static BulkRequestBuilder bulkAdd(BulkRequestBuilder bulkRequestBuilder, UpdateRequest request) {
//            return bulkRequestBuilder.add(request);
//        }
//
//        public static BulkRequestBuilder bulkAdd(BulkRequestBuilder bulkRequestBuilder, UpdateRequestBuilder requestBuilder) {
//            return bulkRequestBuilder.add(requestBuilder);
//        }
//    }

}
