package com.community.community_backend.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName ums_user
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UmsUser implements Serializable {
    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String alias;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 积分
     */
    private Integer score;

    /**
     * token
     */
    private String token;

    /**
     * 个人简介
     */
    private String bio;

    /**
     * 是否激活，1：是，0：否
     */
    private Boolean active;

    /**
     * 状态，1：使用，0：停用
     */
    private Boolean status;

    /**
     * 用户角色
     */
    private Integer roleId;

    /**
     * 加入时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    private static final long serialVersionUID = 1L;
}