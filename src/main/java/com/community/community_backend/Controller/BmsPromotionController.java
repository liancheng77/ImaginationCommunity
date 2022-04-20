package com.community.community_backend.Controller;

import com.community.community_backend.Common.Api.ApiResult;
import com.community.community_backend.Model.Entity.BmsPromotion;
import com.community.community_backend.Service.BmsPromotionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@Api(tags = "主页")
@RestController
@RequestMapping("/promotion")
public class BmsPromotionController extends BaseController {
    @Autowired
    private BmsPromotionService bmsPromotionService;


    @ApiOperation(value = "推广")
    @GetMapping("/all")
    public ApiResult<List<BmsPromotion>> getToday() {
        //查询所有数据
        List<BmsPromotion> list = bmsPromotionService.list();
        return ApiResult.success(list);
    }

}
