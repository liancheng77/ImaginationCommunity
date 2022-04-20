package com.community.community_backend.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.community_backend.Model.Entity.BmsTip;
import org.springframework.stereotype.Repository;

/**
* @author admin
* @description 针对表【bms_tip(每日赠言)】的数据库操作Mapper
* @createDate 2022-02-18 12:53:21
* @Entity generator.BmsTip
*/
@Repository
public interface BmsTipMapper extends BaseMapper<BmsTip> {
    BmsTip getToday();

}
