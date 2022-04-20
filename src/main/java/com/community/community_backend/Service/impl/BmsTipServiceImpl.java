package com.community.community_backend.Service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.community_backend.Model.Entity.BmsTip;
import com.community.community_backend.Service.BmsTipService;
import com.community.community_backend.Mapper.BmsTipMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【bms_tip(每日赠言)】的数据库操作Service实现
* @createDate 2022-02-18 12:53:21
*/
@Service
@Slf4j
public class BmsTipServiceImpl extends ServiceImpl<BmsTipMapper, BmsTip>
implements BmsTipService{

    @Override
    public BmsTip getToday() {
        BmsTip bmsTip = null;
        try {
            bmsTip = this.baseMapper.getToday();
        }catch (Exception e){
            log.info("tip转化失败");
        }
        return bmsTip;
    }
}
