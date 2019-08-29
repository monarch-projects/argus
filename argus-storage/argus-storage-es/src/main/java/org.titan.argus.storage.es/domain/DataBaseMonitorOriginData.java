package org.titan.argus.storage.es.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Map;

/**
 * @Title: DataBaseMonitorOriginData
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/29
 */
@Document(indexName = "db_monitor_origin")
@Data
@Accessors(chain = true)
public class DataBaseMonitorOriginData {

    @Id
    @Field(type = FieldType.Long)
    private Long id;
    @Field(type = FieldType.Keyword, fielddata = true)
    private String ip;
    @Field(type = FieldType.Integer)
    private Integer port;
    @Field(type = FieldType.Keyword, fielddata = true)
    private String dbName;
    @Field(type = FieldType.Long)
    private Long time;
    private Map<String, Object> map;
}
