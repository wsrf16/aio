package com.york.portable.swiss.autoconfigure;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.TransportClientFactoryBean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Properties;

//@Configuration
@ConditionalOnClass({Client.class, TransportClientFactoryBean.class})
@ConditionalOnProperty(
        prefix = "spring.data.elasticsearch",
        name = {"cluster-nodes"}
)
@EnableConfigurationProperties(ElasticsearchProperties.class)
// org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration
public class SwissElasticsearchAutoConfiguration {
    private static SwissElasticsearchAutoConfiguration instance;

    public synchronized static SwissElasticsearchAutoConfiguration singletonInstance() {
        return instance;
    }

    public SwissElasticsearchAutoConfiguration() {
        instance = this;
    }

    @Autowired
    ElasticsearchProperties properties;

    private final static String NETTY3 = "NETTY3";
    private final static String NETTY4 = "NETTY4";
    private final static String DEFAULT_MODE = NETTY4;


    @Bean
    @ConditionalOnMissingBean
    public TransportClient transportClient() throws Exception {
//        logger.info("TransportClient initial", MessageFormat.format("cluster-name : {0}, cluster-nodes : {1}", elasticsearchConfig.clusterName, elasticsearchConfig.clusterNodes));
        TransportClient transportClient;
        switch (DEFAULT_MODE) {
            case NETTY3:
                transportClient = clientByNetty3();
                break;
            case NETTY4:
                transportClient = clientByNetty4();
                break;
            default:
                transportClient = clientByNetty4();
                break;
        }
        return transportClient;
    }

    private TransportClient clientByNetty3() {
        Settings settings = Settings.builder()
                .put("cluster.name", properties.getClusterName())       // 集群名称
                .put("client.transport.sniff", false)    // 目的是为了可以找到集群，嗅探机制开启
                .build();
        TransportClient transportClient = new PreBuiltTransportClient(settings);
        addTransportAddress(transportClient, properties.getClusterNodes());

        return transportClient;
    }

    private static void addTransportAddress(TransportClient transportClient, String clusterNodes) {
        Arrays.stream(clusterNodes.split(",")).forEach(c ->
        {
            String[] parts = c.split(":");
            String ip = parts[0].trim();
            Integer port = Integer.parseInt(parts[1].trim());
            try {
                TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(ip), port);
                transportClient.addTransportAddress(transportAddress);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        });
    }

    private TransportClient clientByNetty4() throws Exception {
        TransportClientFactoryBean factory = new TransportClientFactoryBean();
//        factory.setClusterName(properties.getClusterName());
//        factory.setClientTransportSniff(false);
        factory.setClusterNodes(properties.getClusterNodes());
        factory.setProperties(createProperties());
        factory.afterPropertiesSet();
        TransportClient transportClient = factory.getObject();
//        addTransportAddress(transportClient, properties.getClusterNodes());

        return transportClient;
    }


    private Properties createProperties() {
        Properties properties = new Properties();
        properties.put("cluster.name", this.properties.getClusterName());
        properties.putAll(this.properties.getProperties());
        return properties;
    }


}
