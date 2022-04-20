package com.community.community_backend.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.community.community_backend.Common.Api.ApiResult;
import com.community.community_backend.Model.Vo.PostVO;
import com.community.community_backend.Service.BmsPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "搜索")
@RestController
public class BmsSearchController {
    @Autowired
    private BmsPostService postService;

    @ApiOperation(value = "搜索")
    @GetMapping("/search")
    public ApiResult<Page<PostVO>> searchList(@RequestParam("keyword") String keyword,
                                              @RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize) {
        return ApiResult.success(postService.searchByKey(keyword, new Page<>(pageNum, pageSize)));
    }
}
