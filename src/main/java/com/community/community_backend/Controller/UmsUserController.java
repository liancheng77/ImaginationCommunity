package com.community.community_backend.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.community.community_backend.Common.Api.ApiResult;
import com.community.community_backend.Model.Dto.LoginDto;
import com.community.community_backend.Model.Dto.RegisterDTO;
import com.community.community_backend.Model.Entity.BmsPost;
import com.community.community_backend.Model.Entity.UmsUser;
import com.community.community_backend.Service.BmsPostService;
import com.community.community_backend.Service.UmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.community.community_backend.jwt.JwtUtil.USER_NAME;

@Api(tags = "主页")
@RestController
@RequestMapping("/ums/user")
public class UmsUserController extends BaseController {
    @Autowired
    private UmsUserService userService;
    @Autowired
    private BmsPostService bmsPostService;

    //用户注册控制层
    //valid:校验入参参数,将自定义的实体类的消息返回
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "注册账号")
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        UmsUser umsUser = userService.executeRegister(dto);
        //返回空
        if (ObjectUtils.isEmpty(umsUser)) {
            return ApiResult.failed("账号注册失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", umsUser);
        return ApiResult.success(map);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登陆")
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDto dto) {
        String token = userService.executeLogin(dto);
        if (ObjectUtils.isEmpty(token)) {
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return ApiResult.success(map, "登陆成功");
    }

    @ApiOperation(value = "获取信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiResult<UmsUser> getUser(@RequestHeader(value = USER_NAME) String username) {
        UmsUser user = userService.getUserByUsername(username);
        return ApiResult.success(user);
    }

    @ApiOperation(value = "退出登陆")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ApiResult<Object> logout() {
        return ApiResult.success(null, "注销成功");
    }

    @ApiOperation(value = "个人资料")
    @GetMapping("/{username}")
    public ApiResult<Map<String, Object>> getUserByUsername(@PathVariable("username") String username,
                                                            @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                                                            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map<String, Object> map = new HashMap<>(16);
        UmsUser user = userService.getUserByUsername(username);
        Assert.notNull(user, "用户不存在");
        Page<BmsPost> page = bmsPostService.page(new Page<>(pageNo, size),
                new LambdaQueryWrapper<BmsPost>().eq(BmsPost::getUserId, user.getId()));
        map.put("user", user);
        map.put("topics", page);
        return ApiResult.success(map);
    }
    @ApiOperation(value = "更新账号")
    @PostMapping("/update")
    public ApiResult<UmsUser> updateUser(@RequestBody UmsUser user) {
        userService.updateById(user);
        return ApiResult.success(user);
    }
}
