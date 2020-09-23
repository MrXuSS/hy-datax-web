package com.wugui.datax.admin.tool.pojo;

import lombok.Data;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Xu
 * @create 2020-09-22 16:33
 */
@Data
public class DataxElasticSearchPojo {

    private String name;

    private String esClusterHosts;

    private String batchSize;

    private String esIndex;

    // ========================================

    private String hosts;

    private Boolean cleanup;

    private String index;

    private HashMap<String, Map<String, Integer>> settings;

    private String splitter;

    private List<Map<String, String>> columns;

}
