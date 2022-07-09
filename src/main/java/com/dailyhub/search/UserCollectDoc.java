package com.dailyhub.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Document(indexName = "dailyhub_collect",createIndex = true)
public class UserCollectDoc {

    @Id
    private Long id;

    @Field(type = FieldType.Text,searchAnalyzer = "ik_smart",analyzer = "ik_max_word")
    private String  title;

    @Field(type = FieldType.Text,searchAnalyzer = "ik_smart",analyzer = "ik_max_word")
    private String info;

    //是否公开 默认不公开 0-公开 1-不公开
    @Field(type = FieldType.Integer)
    private Integer personal = 0;

    /**
     * 用户openId 关联用户
     */
    @Field(type = FieldType.Text)
    private String openId;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Date)
    private Date collected;

    @Field(type = FieldType.Text)
    private String url;
}
