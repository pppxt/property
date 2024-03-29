package com.project.properity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.properity.pojo.Community;

public interface CommunityService extends IService<Community> {

    boolean saveCommunity(Community community);
}
