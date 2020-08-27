package com.wugui.datax.admin.tool.query;

import com.wugui.datax.admin.core.util.LocalCacheUtil;
import com.wugui.datax.admin.entity.JobDatasource;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;

import java.io.IOException;
import java.net.ConnectException;

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
                connectionValidate = connection.indices().exists(new GetIndexRequest(jobDatasource.getDatabaseName()), RequestOptions.DEFAULT);;
            } catch (ConnectException e) {
                log.warn("ES Cluster connect failed");
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


    public boolean dataSourceTest() {
        return connectionValidate;
    }
}
