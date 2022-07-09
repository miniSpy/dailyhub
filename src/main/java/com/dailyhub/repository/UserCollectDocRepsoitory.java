package com.dailyhub.repository;

import com.dailyhub.search.UserCollectDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * es生成索引
 */
public interface UserCollectDocRepsoitory extends ElasticsearchRepository<UserCollectDoc,Long> {

}
