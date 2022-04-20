package com.community.community_backend.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.community.community_backend.Common.Api.ApiResult;
import com.community.community_backend.Model.Entity.BmsBillboard;
import com.community.community_backend.Service.BmsBillboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "主页")
@RestController
@RequestMapping("/bmsbillboard")
public class BmsBillboardController extends BaseController{
    @Autowired
    private BmsBillboardService billingService;


    @ApiOperation(value="获取最新公告")
    @GetMapping("/show")
    public ApiResult<BmsBillboard>  getNotices(){
        //查询所有数据
        List<BmsBillboard> list = billingService
                .list(new LambdaQueryWrapper<BmsBillboard>()
                        .eq(BmsBillboard::isShow,true) );
        //查询最后一条数据 = 所有数据-1
        return ApiResult.success(list.get(list.size()-1));
    }

}
