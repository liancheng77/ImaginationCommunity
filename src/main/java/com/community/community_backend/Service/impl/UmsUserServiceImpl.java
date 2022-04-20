package com.community.community_backend.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.community_backend.Common.Exception.ApiAsserts;
import com.community.community_backend.Mapper.BmsFollowMapper;
import com.community.community_backend.Mapper.BmsTopicMapper;
import com.community.community_backend.Mapper.UmsUserMapper;
import com.community.community_backend.Model.Dto.LoginDto;
import com.community.community_backend.Model.Dto.RegisterDTO;
import com.community.community_backend.Model.Entity.BmsFollow;
import com.community.community_backend.Model.Entity.BmsPost;
import com.community.community_backend.Model.Entity.UmsUser;
import com.community.community_backend.Model.Vo.ProfileVO;
import com.community.community_backend.Service.UmsUserService;
import com.community.community_backend.Utils.Md5Utils;
import com.community.community_backend.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * @author admin
 * @description 针对表【ums_user(用户表)】的数据库操作Service实现
 * @createDate 2022-02-18 19:12:10
 */
@Service
@Slf4j
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser>
        implements UmsUserService {
    @Autowired
    private BmsTopicMapper bmsTopicMapper;
    @Autowired
    private BmsFollowMapper bmsFollowMapper;
    @Override
    public UmsUser executeRegister(RegisterDTO dto) {
        //查询是否有相同用户名的用户    ///new一个mybatisplus的对象
        LambdaQueryWrapper<UmsUser> wrapper = new LambdaQueryWrapper<>();
        //判断是否相同
                   //数据库的数据        从客户端发来的数据
        wrapper.eq(UmsUser::getUsername,dto.getName()).or().eq(UmsUser::getEmail,dto.getEmail());
        UmsUser umsUser = baseMapper.selectOne(wrapper);//从数据看查询符合该条件的数据
        //判断对象是否为空,如果不等于空,代表数据库已有相同的数据
        if (!ObjectUtils.isEmpty(umsUser)){
            ApiAsserts.fail("账号或邮箱已存在");
        }
        //在model层架上@Builder注解,可以用链式编程,以及直接赋值
        UmsUser addUser = UmsUser.builder()
                .username(dto.getName())
                .alias(dto.getName())
                .password(Md5Utils.getPwd(dto.getPass()))
                .email(dto.getEmail())
                .createTime(new Date())
                .status(true)
                .build();
        baseMapper.insert(addUser);
        return addUser;
    }

    @Override
    public UmsUser getUserByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<UmsUser>().eq(UmsUser::getUsername,username));
    }

    @Override
    public String executeLogin(LoginDto dto) {
        String token = null;
        try {
            UmsUser userByUsername = getUserByUsername(dto.getUsername());
            String encodePwd = Md5Utils.getPwd(dto.getPassword());
            if (!encodePwd.equals(userByUsername.getPassword())) {
                throw new Exception("密码错误");
            }
            token = JwtUtil.generateToken(String.valueOf(userByUsername.getUsername()));
        } catch (Exception e) {
            log.warn("用户不存在or密码验证失败=======>{}", dto.getUsername());
        }
        return token;
    }

    @Override
    public ProfileVO getUserProfile(String id) {
        ProfileVO profile = new ProfileVO();
        UmsUser user = baseMapper.selectById(id);
        BeanUtils.copyProperties(user, profile);
        // 用户文章数
        int count = bmsTopicMapper.selectCount(new LambdaQueryWrapper<BmsPost>().eq(BmsPost::getUserId, id));
        profile.setTopicCount(count);
        // 粉丝数
        int followers = bmsFollowMapper.selectCount((new LambdaQueryWrapper<BmsFollow>().eq(BmsFollow::getParentId, id)));
        profile.setFollowerCount(followers);
        return profile;
    }


}
