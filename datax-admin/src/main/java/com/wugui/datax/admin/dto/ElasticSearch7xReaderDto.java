package com.wugui.datax.admin.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Xu
 * @create 2020-09-22 16:00
 */
@Data
public class ElasticSearch7xReaderDto implements Serializable {

    private String[] esClusterHosts;

    private String batchSize;

    private String esIndex;
}