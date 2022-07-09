package com.dailyhub.service.Impl;

import com.dailyhub.dto.SearchDto;
import com.dailyhub.entity.UserCollect;
import com.dailyhub.search.UserCollectDoc;
import com.dailyhub.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    public ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public List<UserCollect> searchByKeyword(SearchDto searchDto) {
        Criteria criteria = new Criteria();
        Criteria way = criteria.and(new Criteria("title").is(searchDto.getQ())).or(new Criteria("info").is(searchDto.getQ())).and(new Criteria("personal").is(0));
        CriteriaQuery criteriaQuery = new CriteriaQuery(way);
        SearchHits<UserCollectDoc> hits = elasticsearchRestTemplate.search(criteriaQuery, UserCollectDoc.class);
        List<UserCollectDoc> userCollectDocs = hits.map(e -> e.getContent()).toList();
        List<UserCollect> searchResult = userCollectDocs.stream().map(e -> {
            UserCollect userCollect = new UserCollect();
            BeanUtils.copyProperties(e, userCollect);
            return userCollect;
        }).collect(Collectors.toList());
        log.info("com.dailyhub.service.Impl.SearchServiceImpl.searchByKeyword.result;{}",searchResult);
        return searchResult;
    }
}
