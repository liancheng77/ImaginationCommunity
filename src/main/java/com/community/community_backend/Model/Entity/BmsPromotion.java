package com.community.community_backend.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 广告推广表
 * @TableName bms_promotion
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BmsPromotion implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 广告标题
     */
    private String title;

    /**
     * 广告链接
     */
    private String link;

    /**
     * 说明
     */
    private String description;

    private static final long serialVersionUID = 1L;
}