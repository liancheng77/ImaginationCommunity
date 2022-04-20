package com.community.community_backend.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.community.community_backend.Model.Dto.LoginDto;
import com.community.community_backend.Model.Dto.RegisterDTO;
import com.community.community_backend.Model.Entity.UmsUser;
import com.community.community_backend.Model.Vo.ProfileVO;

/**
 * @author admin
 * @description 针对表【ums_user(用户表)】的数据库操作Service
 * @createDate 2022-02-18 19:12:10
 */

public interface UmsUserService extends IService<UmsUser> {

    /**
     * 注册功能
     *
     * @param dto
     * @return 注册对象
     */
    UmsUser executeRegister(RegisterDTO dto);
    /**
     * 获取用户信息
     *
     * @param username
     * @return dbUser
     */
    UmsUser getUserByUsername(String username);
    /**
     * 用户登录
     *
     * @param dto
     * @return 生成的JWT的token
     */
    String executeLogin(LoginDto dto);

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return
     */
    ProfileVO getUserProfile(String id);

}
