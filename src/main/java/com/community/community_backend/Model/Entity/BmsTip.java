package com.community.community_backend.Model.Entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 每日赠言
 * @TableName bms_tip
 */
@Data
public class BmsTip implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 作者
     */
    private String author;

    /**
     * 1：使用，0：过期
     */
    private Integer type;

    private static final long serialVersionUID = 1L;
}