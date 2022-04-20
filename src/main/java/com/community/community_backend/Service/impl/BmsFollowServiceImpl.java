package com.community.community_backend.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.community_backend.Mapper.BmsFollowMapper;
import com.community.community_backend.Model.Entity.BmsFollow;
import com.community.community_backend.Service.BmsFollowService;
import org.springframework.stereotype.Service;


@Service
public class BmsFollowServiceImpl extends ServiceImpl<BmsFollowMapper, BmsFollow> implements BmsFollowService {
}
