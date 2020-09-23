package com.wugui.datax.admin.tool.datax.reader;

import com.google.common.collect.Maps;
import com.wugui.datax.admin.tool.pojo.DataxElasticSearchPojo;
import com.wugui.datax.admin.tool.pojo.DataxMongoDBPojo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Mr.Xu
 * @create 2020-09-22 16:27
 */
public class ElasticSearchReader extends BaseReaderPlugin implements DataxReaderInterface {
    @Override
    public String getName() {
        return "elasticsearchreader";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }

    @Override
    public Map<String, Object> buildElasticSearch(DataxElasticSearchPojo plugin) {

        LinkedHashMap<String, Object> readerObj = Maps.newLinkedHashMap();
        readerObj.put("name", getName());
        LinkedHashMap<String, Object> parameterObj = Maps.newLinkedHashMap();
        parameterObj.put("esClusterHosts", plugin.getEsClusterHosts());
        parameterObj.put("batchSize", plugin.getBatchSize());
        parameterObj.put("esIndex", plugin.getIndex());

        readerObj.put("parameter", parameterObj);
        return readerObj;
    }
}
