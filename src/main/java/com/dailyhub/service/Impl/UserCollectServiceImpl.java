package com.dailyhub.service.Impl;

import cn.hutool.core.date.DateUtil;
import com.dailyhub.dto.TimeTitleDto;
import com.dailyhub.dto.UserDto;
import com.dailyhub.entity.UserCollect;
import com.dailyhub.repository.UserCollectRepsoitory;
import com.dailyhub.service.UserCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserCollectServiceImpl implements UserCollectService {

    @Autowired
    private UserCollectRepsoitory userCollectRepsoitory;

    @Override
    public List<TimeTitleDto> getCollectByOpenId(String openId) {
        List<Date> userCollectList = userCollectRepsoitory.getCollectByOpenId(openId);
        if(userCollectList.size() == 0)
            return new ArrayList<>();
        List<TimeTitleDto> timeTitleDtos = buildTimeCatalogue(userCollectList);
        return timeTitleDtos;
    }

    @Override
    public List<UserCollect> getUsrCollect(String openId, String timeTitle, Pageable pageable) {
        Integer begin = pageable.getPageSize() * pageable.getPageNumber();
        if("all".equals(timeTitle)){
            return userCollectRepsoitory.getUserAllCollect(openId,begin,pageable.getPageSize());
        }

        List<String> dateList = getDateList(timeTitle);
        List<UserCollect> userCollectByCollected = userCollectRepsoitory.getUserCollectByCollected(openId, dateList.get(0), dateList.get(1), begin,pageable.getPageSize());
        return userCollectByCollected;
    }

    @Override
    public UserCollect getCollectById(Long id) {
        log.info("com.dailyhub.service.Impl.UserCollectServiceImpl.getCollectById.id:{}",id);
        UserCollect collect = userCollectRepsoitory.getById(id);
        return collect;
    }

    @Override
    public void deleteById(Long id) {
        userCollectRepsoitory.deleteById(id);
    }

    @Override
    public void editOrSave(UserCollect userCollect, UserDto userInfo) {
        if(userCollect.getId()==null){
            Date now = new Date();
            userCollect.setCollected(now);
            userCollect.setCreateTime(now);
            userCollect.setOpenId(userInfo.getOpenId());
        }
        else {
            UserCollect collectById = userCollectRepsoitory.getById(userCollect.getId());
            if(!collectById.getOpenId().equals(userInfo.getOpenId())){
                log.info("com.dailyhub.service.Impl.UserCollectServiceImpl.editOrSave.没有权限修改此条数据");
                return;
            }
//            collectById.setInfo(userCollect.getInfo());
//            collectById.setPersonal(userCollect.getPersonal());
//            collectById.setTitle(userCollect.getTitle());
//            collectById.setUrl(userCollect.getUrl());
//            collectById.setCollected(new Date());
            userCollect.setCollected(new Date());
            userCollect.setOpenId(collectById.getOpenId());
            userCollect.setCreateTime(collectById.getCreateTime());
        }
        log.info("com.dailyhub.service.Impl.UserCollectServiceImpl.editOrSave.Data:{}",userCollect);
        userCollectRepsoitory.save(userCollect);
    }

    @Override
    public List<UserCollect> getAllCollects(Pageable pageable) {
        List<UserCollect> allCollects = userCollectRepsoitory.findAllCollects(pageable.getPageNumber()*pageable.getPageSize(),pageable.getPageSize());
        for (int i = 0; i < allCollects.size(); i++) {
            if(allCollects.get(i).getPersonal()==1)
                allCollects.remove(i);
        }
        return allCollects;
    }

    /**
     * 将时间处理成两级目录格式
     */
    private List<TimeTitleDto> buildTimeCatalogue(List<Date> timeList){
        ArrayList<TimeTitleDto> timeCatalogue = new ArrayList<>();
        ArrayList<String> firstTitle = new ArrayList<>();
        ArrayList<String> secondTitle = new ArrayList<>();

        for (Date date : timeList) {
            String yearAndMonth = DateUtil.format(date, "yyyy年MM月");
            if(!firstTitle.contains(yearAndMonth))
            firstTitle.add(yearAndMonth);
            String yearMonthDay = DateUtil.format(date,"yyyy年MM月dd日");
            if(!secondTitle.contains(yearMonthDay))
            secondTitle.add(yearMonthDay);
        }
        for (String str :
                firstTitle) {
            TimeTitleDto timeTitleDto = new TimeTitleDto();
            timeTitleDto.setFirstTitle(str);
            timeCatalogue.add(timeTitleDto);
        }
        timeCatalogue.parallelStream().forEach(p->{
            List<TimeTitleDto> secondTitleList = secondTitle.parallelStream().filter(x -> x.contains(p.getFirstTitle())).map(t -> {
                TimeTitleDto second = new TimeTitleDto();
                second.setFirstTitle(t);
                return second;
            }).collect(Collectors.toList());
            p.setSecondTitle(secondTitleList);
        });
        return timeCatalogue;
//        for (String str : firstTitle) {
//            TimeTitleDto timeTitleDto = new TimeTitleDto();
//            timeTitleDto.setFirstTitle(str);
//            timeTitleDto.setSecondTitle(new ArrayList<>());
//            for (String str2 : secondTitle) {
//                if(str2.contains(str)){
//                    TimeTitleDto second = new TimeTitleDto();
//                    second.setFirstTitle(str2);
//                    timeTitleDto.getSecondTitle().add(second);
//                }
//            }
//            timeTitleDtoList.add(timeTitleDto);
//        }
//       return timeTitleDtoList;
    }

    List<String> getDateList(String timeTitle){
        //转换timeTitle 获取startTime和endTime
        Date date = DateUtil.parse(timeTitle, "yyyy年MM月dd日");
        String startTime = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");

        long currentSecond = date.getTime();

        int dayMis = 1000 * 60 * 60 *24;
        long nextDaySecond = currentSecond + dayMis - 1;
        Date nextDay = new Date(nextDaySecond);
        String endTime = DateUtil.format(nextDay, "yyyy-MM-dd HH:mm:ss");

        ArrayList<String> timeList = new ArrayList<>();
        timeList.add(startTime);
        timeList.add(endTime);
        return timeList;
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        format.format(date).va

    }


}
