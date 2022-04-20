package com.community.community_backend.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.community.community_backend.Common.Api.ApiResult;
import com.community.community_backend.Model.Dto.CreateTopicDTO;
import com.community.community_backend.Model.Entity.BmsPost;
import com.community.community_backend.Model.Entity.UmsUser;
import com.community.community_backend.Model.Vo.PostVO;
import com.community.community_backend.Service.BmsPostService;
import com.community.community_backend.Service.UmsUserService;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static com.community.community_backend.jwt.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/post")
@Api(tags = "帖子相关")
public class BmsPostController extends BaseController {
    @Autowired
    private BmsPostService bmsPostService;
    @Autowired
    private UmsUserService usService;

    @ApiOperation(value = "获取所有帖子")
    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(
            @RequestParam(value = "tab", defaultValue = "latest") String tab,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = bmsPostService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(list);
    }

    @ApiOperation(value = "创建帖子")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<BmsPost> create(@RequestHeader(value = USER_NAME) String userName
            , @RequestBody CreateTopicDTO dto) {
        UmsUser user = usService.getUserByUsername(userName);
        BmsPost topic = bmsPostService.create(dto, user);
        return ApiResult.success(topic);
    }

    @ApiOperation(value = "帖子详情")
    @GetMapping()
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id) {
        Map<String, Object> map = bmsPostService.viewTopic(id);
        return ApiResult.success(map);
    }
    @ApiOperation(value = "帖子推荐")
    @GetMapping(value = "/recommend")
    public ApiResult<List<BmsPost>> getRecommend(@RequestParam("topicId") String id) {
        List<BmsPost> recommendList = bmsPostService.getRecommend(id);
        return ApiResult.success(recommendList);
    }

    //编辑帖子
    @ApiOperation(value = "编辑帖子")
    @PostMapping("/update")
    public ApiResult<BmsPost> update(@RequestHeader(value = USER_NAME) String userName, @Valid @RequestBody BmsPost post) {
        UmsUser umsUser = usService.getUserByUsername(userName);
        Assert.isTrue(umsUser.getId().equals(post.getUserId()), "非本人无权修改");
        post.setModifyTime(new Date());
        post.setContent(EmojiParser.parseToAliases(post.getContent()));
        bmsPostService.updateById(post);
        return ApiResult.success(post);
    }
    @ApiOperation(value = "删除帖子")
    @DeleteMapping("/delete/{id}")
    public ApiResult<String> delete(@RequestHeader(value = USER_NAME) String userName, @PathVariable("id") String id) {
        UmsUser umsUser = usService.getUserByUsername(userName);
        BmsPost byId = bmsPostService.getById(id);
        Assert.notNull(byId, "来晚一步，话题已不存在");
        Assert.isTrue(byId.getUserId().equals(umsUser.getId()), "你为什么可以删除别人的话题？？？");
        bmsPostService.removeById(id);
        return ApiResult.success(null,"删除成功");
    }
}
