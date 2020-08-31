package com.wugui.datax.admin.tool.query;

import com.wugui.datax.admin.core.util.LocalCacheUtil;
import com.wugui.datax.admin.entity.JobDatasource;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author zhangwei
 * @version V1.0
 * @Date 2020/8/25 5:34 下午
 * @Description: ES 查询
 */
@Slf4j
public class ElasticsearchQueryTool {

    private RestHighLevelClient connection;
    private boolean connectionValidate = false;

    public ElasticsearchQueryTool(JobDatasource jobDatasource) {
        getDataSource(jobDatasource);
        if (LocalCacheUtil.get(jobDatasource.getDatasourceName()) == null) {
            getDataSource(jobDatasource);
        } else {
            connection = (RestHighLevelClient) LocalCacheUtil.get(jobDatasource.getDatasourceName());
            if (connection == null) {
                LocalCacheUtil.remove(jobDatasource.getDatasourceName());
                getDataSource(jobDatasource);
            }
        }
        LocalCacheUtil.set(jobDatasource.getDatasourceName(), connection, 4 * 60 * 60 * 1000);
    }

    /**
     * 获得Elasticsearch 的客户端
     *
     * @param jobDatasource 数据源信息
     */
    private void getDataSource(JobDatasource jobDatasource) {
        if (jobDatasource.getJdbcUrl() != null && jobDatasource.getDatabaseName() != null) {

            connection = new RestHighLevelClient(RestClient.builder(
                    new HttpHost(HttpHost.create(jobDatasource.getJdbcUrl()))));
            try {
                connectionValidate = connection.indices().exists(new GetIndexRequest(jobDatasource.getDatabaseName()), RequestOptions.DEFAULT);
                log.info("ES Cluster connect succeed, you are trying to connect " + jobDatasource.getJdbcUrl() +
                        ". And the index " + jobDatasource.getDatabaseName() +
                        " you are trying to connection's existence is " +
                        connectionValidate);
            } catch (ConnectException e) {
                log.warn("ES Cluster connect failed, you are trying to connect " + jobDatasource.getJdbcUrl());
                closeClient();
            } catch (IOException e) {
                log.warn("ES indies get failed");
                closeClient();
                e.printStackTrace();
            }
            log.info("Elasticsearch init succeed");
        }
    }


    private void closeClient() {
        try {
            connection.close();
            log.info("Elasticsearch closed succeed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有索引的名称
     *
     * @return 所有索引的名称
     */
    public List<String> getIndexNames() {
        List<String> indices = new ArrayList<>();
        GetIndexRequest request = new GetIndexRequest("*");
        GetIndexResponse response = null;
        try {
            response = connection.indices().get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException e) {
            log.warn("There is no index in ES now, the message is: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null) {
            indices = Arrays.asList(response.getIndices().clone());
        }
        return indices;
    }

    @SuppressWarnings("unchecked")
    public List<String> getFields(String indexName) {
        List<String> fields = new ArrayList<>();
        GetIndexRequest request = new GetIndexRequest(indexName);
        GetIndexResponse response = null;

        try {
            response = connection.indices().get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException e) {
            log.warn("There is no index in ES now, the message is: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null) {
            Map<String, Object> sourceMap = response.getMappings().get(indexName).getSourceAsMap();
            Map<String, Object> esMapping = (Map<String, Object>) sourceMap.get("properties");
            for (Map.Entry<String, Object> entry : esMapping.entrySet()) {
                Map<String, Object> value = (Map<String, Object>) entry.getValue();
                if (value.containsKey("properties")) {
                    log.info(entry.getKey() + " object");
                } else {
                    log.info("Got Mapping, whose {} is {}", entry.getKey(), value.get("type"));
                    fields.add((String) value.get("type"));
                }
            }

        }

        return fields;
    }

    public boolean dataSourceTest() {
        return connectionValidate;
    }
}
