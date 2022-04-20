package com.community.community_backend.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.community.community_backend.Model.Entity.BmsTag;
import com.community.community_backend.Model.Entity.BmsTopicTag;

import java.util.List;
import java.util.Set;

public interface BmsTopicTagService extends IService<BmsTopicTag> {

    /**
     * 获取Topic Tag 关联记录
     *
     * @param topicId TopicId
     * @return
     */
    List<BmsTopicTag> selectByTopicId(String topicId);
    /**
     * 创建中间关系
     *
     * @param id
     * @param tags
     * @return
     */
    void createTopicTag(String id, List<BmsTag> tags);
    /**
     * 获取标签换脸话题ID集合
     *
     * @param id
     * @return
     */
    Set<String> selectTopicIdsByTagId(String id);

}
