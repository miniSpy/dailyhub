package com.dailyhub.service;

import com.dailyhub.dto.TimeTitleDto;
import com.dailyhub.dto.UserDto;
import com.dailyhub.entity.UserCollect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserCollectService {

    /**
     * 获取侧边栏时间
     * @param openId
     * @return
     */
    List<TimeTitleDto> getCollectByOpenId(String openId);

    List<UserCollect> getUsrCollect(String openId, String timeTitle, Pageable pageable);

    UserCollect getCollectById(Long id);

    void deleteById(Long id);

    void editOrSave(UserCollect userCollect, UserDto userInfo);

    List<UserCollect> getAllCollects(Pageable pageable);
}
