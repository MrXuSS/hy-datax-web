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
public class ElasticSearch7xWriterDto implements Serializable {

  private String[] hosts;

  private Boolean cleanup;

  private String index;

  private Map<String, Map<String, Integer>> settings;

  private String splitter;

  private List<Map<String, Object>> columns;
}
