package com.project.properity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.properity.mapper.CommunityMapper;
import com.project.properity.pojo.Community;
import com.project.properity.service.CommunityService;
import org.springframework.stereotype.Service;

//加上rollbackFor=Exception.class,可以让事物在遇到非运行异常时也回滚。
//@Transactional(rollbackFor=Exception.class)
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper,Community> implements CommunityService {
    public boolean saveCommunity(Community community) {
        return saveOrUpdate(community); //plus中的方法
    }

}

