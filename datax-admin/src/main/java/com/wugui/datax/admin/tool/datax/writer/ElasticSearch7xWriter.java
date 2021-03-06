package com.wugui.datax.admin.tool.datax.writer;

import com.google.common.collect.Maps;
import com.wugui.datax.admin.tool.pojo.DataxElasticSearch7Pojo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mr.Xu
 * @create 2020-09-23 8:37
 */
public class ElasticSearch7xWriter extends BaseWriterPlugin implements DataxWriterInterface {

    @Override
    public String getName() {
        return "elasticsearch7xwriter";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }

    @Override
    public Map<String, Object> buildElasticSearch(DataxElasticSearch7Pojo plugin) {
        LinkedHashMap<String, Object> writerObj = Maps.newLinkedHashMap();
        writerObj.put("name", getName());

        LinkedHashMap<Object, Object> parameterObj = Maps.newLinkedHashMap();
        parameterObj.put("splitter", plugin.getSplitter());
//        parameterObj.put("hosts", plugin.getHosts());
        parameterObj.put("hosts", plugin.getJdbcDatasource().getJdbcUrl());
        parameterObj.put("cleanup", plugin.getCleanup());
        parameterObj.put("index", plugin.getIndex());
        parameterObj.put("settings", plugin.getSettings());
        parameterObj.put("column", plugin.getColumns());

        writerObj.put("parameter", parameterObj);
        return writerObj;
    }
}
