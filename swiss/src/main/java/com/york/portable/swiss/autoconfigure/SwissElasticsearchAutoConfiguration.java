package com.york.portable.swiss.autoconfigure;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.TransportClientFactoryBean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@Configuration
@ConditionalOnProperty(prefix = "spring.data.elasticsearch", name = {"cluster-nodes"})
@ConditionalOnClass({com.sun.jna.Native.class, TransportClient.class})
// org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration
public class SwissElasticsearchAutoConfiguration {
    private static SwissElasticsearchAutoConfiguration instance;
    public synchronized static SwissElasticsearchAutoConfiguration newInstance() {
        return instance;
    }

    public SwissElasticsearchAutoConfiguration() {
        instance = this;
    }

    @Autowired
    ElasticsearchProperties elasticsearchProperties;

    @Bean
    @ConditionalOnMissingBean
    public TransportClient transportClient() throws Exception {
//        logger.info("TransportClient initial", MessageFormat.format("cluster-name : {0}, cluster-nodes : {1}", elasticsearchConfig.clusterName, elasticsearchConfig.clusterNodes));
        TransportClient transportClient = clientByNetty4();
        return transportClient;
    }


    private TransportClient clientByNetty3() {
        Settings settings = Settings.builder()
                .put("cluster.name", elasticsearchProperties.getClusterName())       //集群名称
                .put("client.transport.sniff", false)    //目的是为了可以找到集群，嗅探机制开启
                .build();
        TransportClient transportClient = new PreBuiltTransportClient(settings);
        addTransportAddress(transportClient, elasticsearchProperties.getClusterNodes());

        return transportClient;
    }

    private TransportClient clientByNetty4() throws Exception {
        TransportClientFactoryBean bean = new TransportClientFactoryBean();
        bean.setClusterName(elasticsearchProperties.getClusterName());
        bean.setClientTransportSniff(false);
        bean.setClusterNodes(elasticsearchProperties.getClusterNodes());
        bean.afterPropertiesSet();

        TransportClient transportClient = bean.getObject();
        addTransportAddress(transportClient, elasticsearchProperties.getClusterNodes());

        return transportClient;
    }

    private static void addTransportAddress(TransportClient transportClient, String clusterNodes) {
        Arrays.stream(clusterNodes.split(",")).forEach(c ->
        {
            String[] parts = c.split(":");
            String ip = parts[0].trim();
            Integer port = Integer.parseInt(parts[1].trim());
            try {
                TransportAddress transportAddress = new InetSocketTransportAddress(InetAddress.getByName(ip), port);
                transportClient.addTransportAddress(transportAddress);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        });
    }
}
