package com.wugui.datax.admin.tool.datax.reader;

import com.google.common.collect.Maps;
import com.wugui.datax.admin.tool.pojo.DataxElasticSearch7Pojo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mr.Xu
 * @create 2020-09-22 16:27
 */
public class ElasticSearch7xReader extends BaseReaderPlugin implements DataxReaderInterface {
    @Override
    public String getName() {
        return "elasticsearch7xreader";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }

    @Override
    public Map<String, Object> buildElasticSearch(DataxElasticSearch7Pojo plugin) {

        LinkedHashMap<String, Object> readerObj = Maps.newLinkedHashMap();
        readerObj.put("name", getName());
        LinkedHashMap<String, Object> parameterObj = Maps.newLinkedHashMap();
        parameterObj.put("esClusterHosts", plugin.getJdbcDatasource().getJdbcUrl());
        parameterObj.put("batchSize", plugin.getBatchSize());
        parameterObj.put("esIndex", plugin.getEsIndex());

        readerObj.put("parameter", parameterObj);
        return readerObj;
    }
}
