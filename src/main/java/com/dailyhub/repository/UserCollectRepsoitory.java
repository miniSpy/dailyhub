package com.dailyhub.repository;

import com.dailyhub.entity.UserCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;
import java.util.List;

public interface UserCollectRepsoitory extends JpaRepository<UserCollect,Long> {


    // select distinct collected from m_collect where openId = #{openId}

    @Query(value ="select distinct collected from m_collect where open_id = ? order by collected desc" ,nativeQuery = true)
    List<Date> getCollectByOpenId(String openId);

    @Query(value ="select * from m_collect where open_id = ?1 and collected >= ?2 and collected <= ?3 order by collected desc limit ?4,?5  ",nativeQuery = true)
    List<UserCollect> getUserCollectByCollected(String openId, String startTime, String endTime, Integer begin,Integer size);

    @Query(value ="select * from m_collect where open_id = ?1  order by collected desc limit ?2,?3 ",nativeQuery = true)
    List<UserCollect> getUserAllCollect(String openId, Integer begin,Integer size);

    @Query(value = "select * from m_collect where id = ?",nativeQuery = true)
    UserCollect getById(Long id);

    @Query(value = "select * from m_collect where 1=1",nativeQuery = true)
    List<UserCollect> findAllCollects(Integer begin,Integer size);
}
