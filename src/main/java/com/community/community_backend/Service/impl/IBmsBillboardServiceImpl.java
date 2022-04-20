package com.community.community_backend.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.community_backend.Mapper.BmsBillboardMapper;
import com.community.community_backend.Model.Entity.BmsBillboard;
import com.community.community_backend.Service.BmsBillboardService;
import org.springframework.stereotype.Service;

@Service
public class IBmsBillboardServiceImpl extends ServiceImpl<BmsBillboardMapper, BmsBillboard> implements BmsBillboardService {
}
