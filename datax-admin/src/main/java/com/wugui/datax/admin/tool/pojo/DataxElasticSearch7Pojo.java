package com.wugui.datax.admin.tool.pojo;

import com.wugui.datax.admin.entity.JobDatasource;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Xu
 * @create 2020-09-22 16:33
 */
@Data
public class DataxElasticSearch7Pojo {

    /**
     * 数据源信息
     */
    private JobDatasource jdbcDatasource;

    private String name;

    private String esClusterHosts;

    private Integer batchSize;

    private String esIndex;

    // ========================================

    private String hosts;

    private Boolean cleanup;

    private String index;

    private Map<String, Map<String, Integer>> settings;

    private String splitter;

    private List<Map<String, Object>> columns;

}
