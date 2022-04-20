package com.community.community_backend.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.community.community_backend.Model.Entity.BmsTip;


/**
* @author admin
* @description 针对表【bms_tip(每日赠言)】的数据库操作Service
* @createDate 2022-02-18 12:53:21
*/
public interface BmsTipService extends IService<BmsTip> {
    BmsTip getToday();
}
