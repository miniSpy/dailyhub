package com.dailyhub.service;

import com.dailyhub.dto.SearchDto;
import com.dailyhub.entity.UserCollect;

import java.util.List;

public interface SearchService {

    List<UserCollect> searchByKeyword(SearchDto searchDto);
}
