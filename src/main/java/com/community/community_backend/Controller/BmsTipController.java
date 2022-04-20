package com.community.community_backend.Controller;
import com.community.community_backend.Common.Api.ApiResult;
import com.community.community_backend.Model.Entity.BmsTip;
import com.community.community_backend.Service.BmsTipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "主页")
@RestController
@RequestMapping("/tip")
public class BmsTipController extends BaseController{
    private final BmsTipService bmsTipService;

    public BmsTipController(BmsTipService bmsTipService) {
        this.bmsTipService = bmsTipService;
    }


    @ApiOperation(value="每日一句")
    @GetMapping("/today")
    public ApiResult<BmsTip>  getToday(){
        //查询所有数据
        BmsTip tip = bmsTipService.getToday();
        //查询最后一条数据 = 所有数据-1
        return ApiResult.success(tip);
    }

}
